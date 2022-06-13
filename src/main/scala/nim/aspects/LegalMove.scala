package nim.aspects

import foam.{NFA, State}
import foam.aspects.{AroundState, Aspect, Joinpoint, Pointcutter}
import nim.states.GameState
import nim.tokens.Turn
import nim.utils.Move

class LegalMove(moves: Seq[Move], threshold: Int = 0) extends Aspect[NFA] {
  override def apply(base: NFA): NFA = {
    val statePointCut = Pointcutter[State, GameState](base.states, {
      // If delta >= 0, then we want s.heaps(heapIndex) + delta <= threshold
      // Otherwise, then we want threshold <= s.heaps(heapIndex + delta
      // This may be concisely represented as: (-1)^{1[delta < 0]} (h + delta) <= threshold.
      case s: GameState if moves.forall(move =>
        (if (move.deltaTokens >= 0) 1 else -1) * (s.heaps(move.heapIndex) + move.deltaTokens) <= threshold) => true
      case _ => false
    })

    AroundState[GameState](statePointCut, base)((thisJoinPoint: Joinpoint[GameState], thisNFA: NFA) => {
      var heapUpdateSlice = Vector.fill(thisJoinPoint.point.heaps.length)(0)

      // Compose the set of moves together into a single representation.
      for (move <- moves) {
        heapUpdateSlice = heapUpdateSlice.updated(move.heapIndex, move.deltaTokens)
      }

      // Calculate the number of tokens remaining in each heap as the
      // elementwise-sum of the current heap totals and the heapUpdateSlice.
      val newHeapQuantities = thisJoinPoint.point.heaps zip heapUpdateSlice map(t => t._1 + t._2)

      val newNFA = thisNFA.addTransition(
        (thisJoinPoint.point, Turn(heapUpdateSlice)),
        GameState(newHeapQuantities, newHeapQuantities.forall(_ == threshold))
      )

      (thisJoinPoint.point, newNFA)
    })
  }
}
