package nim.tokens

import foam.Token
import nim.traits.HasValue

case class Take(value: Int, index: Int) extends Token with HasValue {
  override def toString: String = s"P $index, T" + super.toString
}