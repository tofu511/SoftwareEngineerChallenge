import org.scalatest.FunSuite

class ImmutableQueueTest extends FunSuite {

  private val emptyQueue = Queue.empty[Int]

  test("isEmpty method should return true when the queue is empty") {
    assert(emptyQueue.isEmpty)
  }

  test("isEmpty method should return false when the queue is not empty") {
    val intQueue = ImmutableQueue[Int](1)
    assert(intQueue.isEmpty === false)
  }

  test("enQueue method should add an element to the end of the queue") {
    val intQueue = ImmutableQueue[Int](1, 2)
    assert(intQueue.enQueue(3) === ImmutableQueue[Int](1, 2, 3))
  }

  test("enQueue method should return new instance") {
    val intQueue = ImmutableQueue[Int](1, 2)
    assert(intQueue.enQueue(3) ne intQueue.enQueue(3))
  }

  test("deQueue method should throw exception when the queue is empty") {
    assertThrows[UnsupportedOperationException](emptyQueue.deQueue())
  }

  test("deQueue method should return queue excluding the first element") {
    val intQueue1 = ImmutableQueue[Int](1, 2, 3)
    assert(intQueue1.deQueue() === ImmutableQueue[Int](2, 3))
  }

  test("deQueue method should return new instance") {
    val intQueue = ImmutableQueue[Int](1, 2, 3)
    assert(intQueue.deQueue() ne intQueue.deQueue())
  }

  test("head method should return first element") {
    val intQueue = ImmutableQueue[Int](1, 2)
    assert(intQueue.head === Some(1))
  }

  test("head method should return None when the queue is empty") {
    assert(emptyQueue.head === None)
  }
}
