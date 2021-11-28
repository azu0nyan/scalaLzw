package lzw

import java.nio.file.Path

object Main {
  def main(args: Array[String]): Unit = {
    FileEncoderDecoder.encode(Path.of("src/main/resources/testInput1"), Path.of("encoded"))
    FileEncoderDecoder.decode(Path.of("encoded"), Path.of("decoded"))
  }

}
