object Main {
  def main(args: Array[String]): Unit = {
//    Encoder(Array(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))
//    val arr = (1 to 100000).map(x => (x % 256).toByte)
    val arr = (1 to 100).map(x => (x % 5).toByte)
    Encoder(arr)
  }

}
