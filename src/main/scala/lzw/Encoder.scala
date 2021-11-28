package lzw

import scala.collection.mutable

object Encoder {




  def apply(in: IndexedSeq[Byte])(params: Params = Params()): Array[Byte] = {
    val dict: mutable.Map[Seq[Byte], Int] = Helpers.initialEncoderDict
    var currentCodeNumber = params.startCode
    var currentLength = params.initialCodeLength
    //var out: StringBuilder = new StringBuilder

    val outWriter = new BitWriter()

    var notInOutput: Seq[Byte] = Seq(in.head) //always in dictionary
    for (currentChar <- in.tail) {
      val toTestInDict = notInOutput :+ currentChar
      dict.get(toTestInDict) match {
        case Some(_) =>
          notInOutput = toTestInDict
        case None =>
          if(params.showDebug && params.showDebugWrittenCodes) println(s"emitting ${dict(notInOutput)} with length $currentLength ${Helpers.toBinaryStingLittleEndian(dict(notInOutput), currentLength)}")
          outWriter.writeBits(dict(notInOutput) , currentLength)

          if(params.showDebug && params.showDebugNewCodes) {
            if (params.bytesAsChars)
              println(f"new code for ${dict(notInOutput) + s" + \'${currentChar.toChar}\'"}%12s  ${s"\"${toTestInDict.map(_.toChar).mkString}\""}%-8s is $currentCodeNumber%5d - ${currentCodeNumber.toBinaryString}%-20s")
            else
              println(f"new code for ${dict(notInOutput) + s" + \'${currentChar}\'"}%12s  ${s"\"${toTestInDict.mkString}\""}%-8s is $currentCodeNumber%5d - ${currentCodeNumber.toBinaryString}%-20s")
          }

          dict += toTestInDict -> currentCodeNumber
          currentCodeNumber += 1
          notInOutput = Seq(currentChar)
      }
    }
    if(params.showDebug && params.showDebugWrittenCodes)
      println(s"emitting ${dict(notInOutput)} with length $currentLength ${Helpers.toBinaryStingLittleEndian(dict(notInOutput), currentLength)}")
    outWriter.writeBits(dict(notInOutput), currentLength)
    outWriter.writeBits(params.eofCode, currentLength)
    val res = outWriter.toArray
    println(s"Dictionary size ${dict.size}")
    println(s"encoded length ${res.length * 8d} bits")
    println(f"compression ratio ${in.length.toDouble / res.length.toDouble}%.6f")
    if(params.showDebug && params.showDebugBytes){
     Helpers.printByGroups(res, params.debugBytesCap, 10, 10)
//     Helpers.printByGroups(res, params.debugBytesCap, 8, 10)
    }
    res
  }

}
