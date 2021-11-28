import scala.collection.mutable

object Helpers {

  def byteTo8String(byte: Byte): String = {
    var res = ""
    for (i <- 0 until 8) {
      res = ((byte >> i) & 1).toString + res
    }
    res
  }

  def initialDict: mutable.Map[Seq[Byte], String] = {
    val dict: mutable.Map[Seq[Byte], String] = mutable.Map()
    for (i <- Byte.MinValue to Byte.MaxValue)
      dict += Seq(i.toByte) -> byteTo8String(i.toByte)
    dict
  }
}
