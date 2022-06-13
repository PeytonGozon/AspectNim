package nim.tokens

import foam.Token

case class Turn(change: Vector[Int]) extends Token {
  override def toString(): String = change.mkString(",")
}
