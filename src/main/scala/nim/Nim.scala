package nim

import foam.aspects.{Aspect, Weaver}
import foam.examples.SimpleStateFactory
import foam.{Lambda, NFA, State}
import nim.states.GameState

object Nim {

  val start: State = SimpleStateFactory(false)

  val namer: Any => String = {
    case s: State if s == start => "Start"
    case other => other.toString
  }

  def apply(initialHeaps: Vector[Int], features: List[Aspect[NFA]]): NFA = {
    implicit var fsm: NFA = new NFA(start).addTransition((start, Lambda), GameState(initialHeaps/*, isAccept = false*/))

    val finalFSM = Weaver[NFA](features, fsm, (before: NFA, after: NFA) => before.isEqual(after))

    finalFSM
  }

}
