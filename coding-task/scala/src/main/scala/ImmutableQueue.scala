class ImmutableQueue[T](val elements: T*) extends AnyRef with Queue[T] {
  override def isEmpty: Boolean = elements.isEmpty

  override def enQueue(t: T): Queue[T] = new ImmutableQueue[T](elements :+ t: _*)

  override def deQueue(): Queue[T] = new ImmutableQueue[T](elements.tail: _*)

  override def head: Option[T] = elements.headOption

  override def equals(obj: Any): Boolean = {
    obj.isInstanceOf[ImmutableQueue[T]] &&
      (this.hashCode() == obj.asInstanceOf[ImmutableQueue[T]].hashCode())
  }

  override def hashCode(): Int = elements.hashCode()
}

object ImmutableQueue {
  def apply[T](elem: T*): ImmutableQueue[T] = new ImmutableQueue[T](elem: _*)
}
