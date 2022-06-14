package nim.tokens

import foam.Token

case class PlayTypeToken(isNormalPlay: Boolean) extends Token {
  override def toString(): String = if (isNormalPlay) "Normal Play" else "Misère Play"
}
