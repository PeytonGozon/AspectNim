import nim.Nim
import foam._

object Main {

 def main(args: Array[String]): Unit = {
  val nimNFA: NFA = Nim.apply(Vector(3, 4), Seq(1, 2))

  Emitter.emitGV(nimNFA, Nim.namer)
 }

}
