package nim.tokens

import foam.Token

case class TurnToken(change: Vector[Int]) extends Token {
  override def toString(): String = change.mkString(",")
}
