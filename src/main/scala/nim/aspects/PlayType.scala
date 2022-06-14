package nim.aspects

import foam.{Lambda, NFA, State}
import foam.aspects.{AroundState, Aspect, Joinpoint, Pointcutter}
import nim.states.{GameOverState, GameState}
import nim.tokens.PlayTypeToken

class PlayType(isNormalPlay: Boolean, threshold: Int = 0) extends Aspect[NFA] {
  override def apply(base: NFA): NFA = {
    val statePointCut = Pointcutter[State, GameState](base.states, {
      case s: GameState if s.heaps.forall(_ == threshold) => true
      case _ => false
    })

    AroundState[GameState](statePointCut, base)((thisJointPoint: Joinpoint[GameState], thisNFA: NFA) => {
      val newNFA = thisNFA.addTransition((thisJointPoint.point, Lambda/*PlayTypeToken(isNormalPlay)*/), GameOverState(isNormalPlay))
      (thisJointPoint.point, newNFA)
    })
  }
}
