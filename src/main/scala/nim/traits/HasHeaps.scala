package nim.traits

import foam.Component

trait HasHeaps {
  this: Component =>
  val heaps: Vector[Int]

  override def toString: String = super.toString + " " + heaps
}
