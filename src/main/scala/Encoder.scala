import scala.collection.mutable

object Encoder {
  def apply(in: IndexedSeq[Byte]): Array[Byte] = {
    val dict: mutable.Map[Seq[Byte], String] = Helpers.initialDict
    var current = 256
    var out: StringBuilder = new StringBuilder

    var inPhrase: Seq[Byte] = Seq(in.head)
    for (b <- in.tail) {
      dict.get(inPhrase :+ b) match {
        case Some(_) =>
          inPhrase = inPhrase :+ b
        case None =>
          out.append(dict(inPhrase))
          println(s"new code for ${inPhrase :+ b} ${current.toBinaryString}")
          dict += (inPhrase :+ b) -> current.toBinaryString
          current += 1
          inPhrase = Seq(b)
      }
      out.append(dict(inPhrase))
    }
    println(s"encoded length ${out.length}:")
    println(f"compression ratio ${in.length * 8d / out.length }")
    println(if(out.length > 2000) out.take(2000) + "......" else out)
    in.toArray
  }

}
