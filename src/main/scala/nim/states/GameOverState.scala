package nim.states

import foam.State

case class GameOverState(isNormalPlay: Boolean, isAccept: Boolean = true) extends State {
  override def toString(): String = "Game Over\n(" + (if (isNormalPlay) "Normal Play" else "Misère Play") + ")"
}
