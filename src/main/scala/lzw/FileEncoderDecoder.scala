package lzw

import java.nio.file.{Files, Path}

object FileEncoderDecoder {
  def encode(inFile: Path, outFile: Path):Unit = {
    val in = Files.readAllBytes(inFile)
    val params = Params(initialCodeLength = 16, showDebug = false)
    val encoded = Encoder(in)(params)
    Files.write(outFile, encoded)
  }

  def decode(inFile: Path, outFile: Path):Unit = {
    val in = Files.readAllBytes(inFile)
    val params = Params(initialCodeLength = 16, showDebug = false)
    val decoded = Decoder(in)(params)
    Files.write(outFile, decoded)
  }


}
