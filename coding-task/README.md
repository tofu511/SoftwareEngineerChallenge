# Implement Immutable Queue
- implement an immutable queue with the following api
    - Scala Version:
    ```scala
	trait Queue[T] {
	  def isEmpty: Boolean
	  def enQueue(t: T): Queue[T]
	  // Removes the element at the beginning of the immutable queue, and returns the new queue.
	  def deQueue(): Queue[T]
	  def head: Option[T]
	}
	object Queue {
	  def empty[T]: Queue[T] = ???
	}
    ```
   - Java Version:
    ```java
	public interface Queue<T> {
	    public Queue<T> enQueue(T t);
	    // Removes the element at the beginning of the immutable queue, and returns the new queue.
	    public Queue<T> deQueue();
	    public T head();
	    public boolean isEmpty();
	}
    ```
