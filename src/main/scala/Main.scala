import nim.Nim
import nim.aspects.{LegalMove, PlayType}
import nim.utils.Move
import foam._
import foam.aspects.Aspect
import nim.move_generators.ClassicNimMoveGenerator

object Main {

 def main(args: Array[String]): Unit = {

  val heaps: Vector[Int] = Vector(3, 4, 5)
  val threshold: Int = 0

  var features: List[Aspect[NFA]] = List(
   new PlayType(true, threshold)
  )

  val additionalFeatures = ClassicNimMoveGenerator.apply(heaps, threshold)

  println(additionalFeatures)

  features = features ++ additionalFeatures

  val nimNFA: NFA = Nim.apply(heaps, features)

  Emitter.emitGV(nimNFA, Nim.namer)
 }
}
