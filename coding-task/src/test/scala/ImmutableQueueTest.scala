import org.scalatest.FunSuite

class ImmutableQueueTest extends FunSuite {

  test("element should be empty when one element queue is dequeued") {
    val intQueue = ImmutableQueue[Int](1)
    assert(intQueue.deQueue().isEmpty)
  }

  test("enQueue method should add an element to the end of the queue") {
    val intQueue = ImmutableQueue[Int](1, 2)
    assert(intQueue.enQueue(3) == ImmutableQueue[Int](1, 2, 3))
  }

  test("enQueue method should return new instance") {
    val intQueue1 = ImmutableQueue[Int](1, 2)
    val intQueue2 = ImmutableQueue[Int](1, 2, 3)
    assert(intQueue1.enQueue(3) ne intQueue2)
  }

  test("deQueue method should return queue excluding the first element") {
    val intQueue1 = ImmutableQueue[Int](1, 2, 3)
    assert(intQueue1.deQueue() == ImmutableQueue[Int](2, 3))
  }

  test("deQueue method should return new instance") {
    val intQueue1 = ImmutableQueue[Int](1, 2, 3)
    val intQueue2 = ImmutableQueue[Int](2, 3)
    assert(intQueue1.deQueue() ne intQueue2)
  }

  test("head method should return first element") {
    val intQueue = ImmutableQueue[Int](1, 2)
    assert(intQueue.head === Some(1))

    val stringQueue = ImmutableQueue[String]("foo", "bar")
    assert(stringQueue.head === Some("foo"))
  }
}
