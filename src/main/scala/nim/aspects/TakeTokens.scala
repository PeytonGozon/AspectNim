package nim.aspects

import foam.{NFA, State}
import foam.aspects.{AroundState, Aspect, Joinpoint, Pointcutter}
import nim.states.GameState
import nim.tokens.Take

class TakeTokens(take: Take) extends Aspect[NFA] {
  def apply(nfa: NFA): NFA = {

    val statePointCut = Pointcutter[State, GameState](nfa.states, {
      case s: GameState if (s.heaps(take.index) - take.value >= 0) => true
      case _ => false
    })

    AroundState[GameState](statePointCut, nfa)((thisJoinPoint: Joinpoint[GameState], thisNFA: NFA) => {
      val newHeapValue = thisJoinPoint.point.heaps(take.index) - take.value
      val updatedHeaps = thisJoinPoint.point.heaps.updated(take.index, newHeapValue)

      // TODO: Game's over when each pile has a total of 0 sticks in normal play.
      val newGameState = GameState(updatedHeaps, updatedHeaps.forall(h => h == 0))

      val newNFA = thisNFA.addTransition((thisJoinPoint.point, take), newGameState)
      (thisJoinPoint.point, newNFA)
    })
  }
}
