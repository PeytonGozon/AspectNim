package nim.move_generators

import nim.aspects.LegalMove
import nim.utils.Move

import scala.collection.mutable.ListBuffer

object ClassicNimMoveGenerator {

  /**
   * In the classical game of nim, the game has multiple heaps, and players are counting down.
   * Each turn, the current player takes anywhere between one token and the entire stack.
   *
   * @param heaps The quantity of tokens in each heap
   * @return A list of valid moves that can be played from the initial state of the game.
   */
  def apply(heaps: Seq[Int], threshold: Int = 0): Seq[LegalMove] = {

    (for ((heap, index) <- heaps.zipWithIndex)
      yield (1 to heap) map (t => new LegalMove(Seq(Move(index, -t)), threshold))).flatten

  }

}
