package nim

import foam.aspects.Weaver
import foam.examples.SimpleStateFactory
import foam.{Lambda, NFA, State}
import nim.aspects.TakeTokens
import nim.states.GameState
import nim.tokens.Take

object Nim {

  val start: State = SimpleStateFactory(false)

  val namer: Any => String = {
    case s: State if (s == start) => "Start"
    case other => other.toString
  }

  def apply(initialHeaps: Vector[Int], validTakes: Seq[Int]): NFA = {
    implicit var fsm: NFA = (new NFA(start)).addTransition((start, Lambda), GameState(initialHeaps, isAccept = false))

    val takeFeatures = (for(t <- validTakes; i <- initialHeaps.indices) yield new TakeTokens(Take(t, i))).toList

    val finalFSM = Weaver[NFA](takeFeatures, fsm, (before: NFA, after: NFA) => before.isEqual(after))

    finalFSM
  }

}
