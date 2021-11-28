package lzw

object Main {
  def main(args: Array[String]): Unit = {
    //    Encoder(Array(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))
    //        val arr = (1 to 100000).map(x => (x % 256).toByte)
    //    val arr = (1 to 100).map(x => (x % 5).toByte)
    //    val arr = Encoder(Array(1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1).map(_.toByte))
    //    val arr = "abababsbsbsbdjdjjdjdskskksksks".toArray.map(_.toByte)
    //    val arr = "aba".toArray.map(_.toByte)
//    val arr = "aaaaaa".toArray.map(_.toByte) //todo
//    val arr = "ababababab".toArray.map(_.toByte) //todo
    val arr = "abababab".toArray.map(_.toByte)

    println("input: " + new String(arr))
    val params = Params()
    val encoded = Encoder(arr)(params)
    val decoded = Decoder(encoded)(params)

    println("output: " + new String(decoded))
  }

}
