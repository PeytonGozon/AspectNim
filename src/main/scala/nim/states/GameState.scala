package nim.states

import foam.State
import nim.traits.HasHeaps

// Represents the number of tokens remaining in each heap.
case class GameState (heaps: Vector[Int], isAccept: Boolean) extends State with HasHeaps
