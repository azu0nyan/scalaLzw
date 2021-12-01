package lzw

import java.nio.file.Path

object Main {
  def main(args: Array[String]): Unit = {

    for(i <- 9 to 24){
      val cl = (1 << i) - 1
      val p = Params(startCode = 510, maxCode = cl, showDebug = false)
      println()
      println(s"Encoding decoding with $cl")
      FileEncoderDecoder.encode(Path.of("src/main/resources/testInput1"), Path.of("encoded"), p)
      FileEncoderDecoder.decode(Path.of("encoded"), Path.of("decoded"), p)
    }

  }

}
