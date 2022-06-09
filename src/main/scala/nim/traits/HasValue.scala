package nim.traits

import foam.Component

trait HasValue {
  this: Component =>
  val value: Int

  override def toString: String = super.toString + " " + value
}
