package lzw

import scala.collection.mutable

object Encoder {

  def apply(in: IndexedSeq[Byte])(params: Params = Params()): Array[Byte] = {

    val outWriter = new BitWriter()

    var dict: mutable.Map[Seq[Byte], Int] = mutable.Map()
    var currentCodeNumber: Int = -1
    def reset(): Unit = {
      dict = Helpers.initialEncoderDict
      currentCodeNumber = params.startCode
    }

    reset()

    def currentLength: Int = Helpers.codeLength(currentCodeNumber)

    var notInOutput: Seq[Byte] = Seq(in.head) //always in dictionary
    for (currentChar <- in.tail) {
      val toTestInDict = notInOutput :+ currentChar
      dict.get(toTestInDict) match {
        case Some(_) =>
          notInOutput = toTestInDict
        case None =>
          if (params.showDebug && params.showDebugWrittenCodes)
            println(s"emitting ${dict(notInOutput)} - ${notInOutput.map(_.toChar).mkString("")} with length $currentLength ${Helpers.toBinaryStingLittleEndian(dict(notInOutput), currentLength)}")
          outWriter.writeBits(dict(notInOutput), currentLength)

          if (params.showDebug && params.showDebugNewCodes) {
            if (params.bytesAsChars)
              println(f"new code for ${dict(notInOutput) + s" + \'${currentChar.toChar}\'"}%12s  ${s"\"${toTestInDict.map(_.toChar).mkString}\""}%-8s is $currentCodeNumber%5d - ${currentCodeNumber.toBinaryString}%-20s")
            else
              println(f"new code for ${dict(notInOutput) + s" + \'${currentChar}\'"}%12s  ${s"\"${toTestInDict.mkString}\""}%-8s is $currentCodeNumber%5d - ${currentCodeNumber.toBinaryString}%-20s")
          }

          dict += toTestInDict -> currentCodeNumber
          currentCodeNumber += 1
          if (currentCodeNumber == params.maxCode) {
            outWriter.writeBits(params.resetCode, currentLength)
            if (params.showDebug && params.showDebugWrittenCodes)
              println(s"emitting reset code ${params.resetCode} with length $currentLength ${Helpers.toBinaryStingLittleEndian(params.resetCode, currentLength)}")
            reset()
          }
          notInOutput = Seq(currentChar)

      }
    }
    if (params.showDebug && params.showDebugWrittenCodes)
      println(s"emitting ${dict(notInOutput)} with length $currentLength ${Helpers.toBinaryStingLittleEndian(dict(notInOutput), currentLength)}")
    outWriter.writeBits(dict(notInOutput), currentLength)
    currentCodeNumber += 1 //for correct EOF reading in decoder
    if (params.showDebug && params.showDebugWrittenCodes)
      println(s"emitting EOF code ${params.eofCode} with length $currentLength ${Helpers.toBinaryStingLittleEndian(params.eofCode, currentLength)}")
    outWriter.writeBits(params.eofCode, currentLength)
    val res = outWriter.toArray
    println(s"Dictionary size ${dict.size}")
    println(s"input length ${in.length * 8} bits")
    println(s"encoded length ${res.length * 8} bits")
    println(f"compression ratio ${res.length.toDouble / in.length.toDouble}%.6f")
    if (params.showDebug && params.showDebugBytes) {
      Helpers.printByGroups(res, params.debugBytesCap, 9, 10)
      //     Helpers.printByGroups(res, params.debugBytesCap, 8, 10)
    }
    res
  }

}
