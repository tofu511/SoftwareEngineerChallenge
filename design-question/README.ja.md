# Design Question
## 要件
- 下記の要件を満たすGoogle Analyticsのようなサービスを顧客に提供する
- オープンソースのソフトウェアは自由に使って良い
```
1) handle large write volume: Billions write events per day.

2) handle large read/query volume: Millions merchants want to get insight about their business. Read/Query patterns are time-series related metrics. 

3) provide metrics to customers with at most one hour delay.

4) run with minimum downtime.

5) have the ability to reprocess historical data in case of bugs in the processing logic.
```

## 要件分析
1. 1日に10億件の書き込みがあるため、1秒あたり約12000件の書き込みができる必要がある
1. 1日に100万件の読み込みがあるため、1秒あたり約12件の読み込みができる必要がある
1. 最大1時間の遅れでメトリクスを見れるようにする必要があるため、バッチ処理ではなくリアルタイム性が求められる
1. AWS等のパブリッククラウドでシステムを構築することを想定し、パブリッククラウドのSLAを少し下回る99.5%から99.9%の可用性を想定する
1. バグがあった場合、再実行できるようにするため、ログをストレージ等に保持する必要がある

## システム概要
![system_overview](./system_overview.png)

## 選定したソフトウェア・設計
### Kubernetes
1. Websiteから送られてくる情報を受け取り、ログの加工・fluentdに流す役割のアプリケーションを動かす。fluentdはsidecarとして配置する。
1. サイト管理者などのユーザー向けにメトリクスの可視化などの機能を提供するアプリケーションを動かす。Nginxはアクセスログを取得をしたり、レート制限を行うためにアプリケーションの前段に配置する。
- それぞれのサービスは高可用性を維持するために、コンテナをスケールアウトする。またコンテナが落ちても必要なコンテナ数が維持されるようにするためにコンテナオーケストレーションツールでデファクトスタンダードであるKubernetesを選定した。

### fluentd
- ログをオブジェクトストレージとKafakaに転送するために使用する。
- アプリケーションでログをオブジェクトストレージとKafakaに転送しようとすると、やり直しの処理やファイル書き込みによるローカルボリュームへの永続化が複雑になるため、fluentdを使用して、ログ転送の責務をアプリケーションから分離する。
- ログ転送のためfluentdを使用する事例が多く、AWS-S3, GCP-GCSへのプラグインがあることからfluentdを選定した。

### Apache Kafka
- 大量のログデータをリアルタイムにETLサーバーに転送する分散メッセージキューとして使用し、データをFlinkに送る。
- バグ等で再処理が必要になった場合にデータを再度ETLサーバーに転送できるよう、数ヶ月分のデータを貯めておく。
- 高可用かつリアルタイム性を両立するために、分散メッセージキューを使用し、その中でもメッセージを一定期間保存できるKafkaを選定した。

### Apache Flink + Apache Beam
- データベースにログデータを保存するためのストリーミングETL処理を行う。
- バグなどで再処理を実行したいが、現在動いているストリーミング処理では行いたくない場合のバッチ処理も実行する。
- ストリーミング処理とバッチ処理をどちらも実行でき、Alibabaでの大量アクセスをさばいた実績があることからFlinkを選定した。
	- [A Flink Series from the Alibaba Tech Team](https://medium.com/@alitech_2017/a-flink-series-from-the-alibaba-tech-team-b8b5539fdc70)
	- > For the most recent 11.11 of 2018, the Flink engine smoothly supported real-time traffic peaking at 1.7 billion transactions per second.
- ストリーミング処理とバッチ処理の両方をサポートし、複数の実行環境をサポートしているしているため、処理のモデルとしてApache Beamを選定した。

### Apache Cassandra
- ETL処理の結果を保存するために使用する。
- 高可用かつ書き込みの性能に優れているCassandraを選定した。

### Nginx
- アクセスログの取得とレート制限を行うために使用する。
- NginxはWebサーバーの中で大量アクセス処理が得意なため選定した。

### Object Storage
- fluentdから送られるログを保存する。バグ等で再処理をする必要がある場合に使用する。

### Load Balancer
- ユーザーのトラフィックを分散するために使用する。
