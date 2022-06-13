import nim.Nim
import nim.aspects.LegalMove
import nim.utils.Move
import foam._

object Main {

 def main(args: Array[String]): Unit = {

  val features = List(
    new LegalMove(Seq(Move(0, 1)), 3),
    new LegalMove(Seq(Move(1, 1)), 3),
    new LegalMove(Seq(Move(0, 1), Move(1, 1)), 3)
  )

  val nimNFA: NFA = Nim.apply(Vector(0, 0), features)

  Emitter.emitGV(nimNFA, Nim.namer)
 }
}
