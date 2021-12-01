package lzw

import java.nio.file.{Files, Path}

object FileEncoderDecoder {
  def encode(inFile: Path, outFile: Path, params: Params = Params(showDebug = false)):Unit = {
    val in = Files.readAllBytes(inFile)
    val encoded = Encoder(in)(params)
    Files.write(outFile, encoded)
  }

  def decode(inFile: Path, outFile: Path, params: Params = Params(showDebug = false)):Unit = {
    val in = Files.readAllBytes(inFile)
    val decoded = Decoder(in)(params)
    Files.write(outFile, decoded)
  }


}
