package lzw

import scala.collection.immutable.{AbstractSeq, LinearSeq}
import scala.collection.mutable

object Decoder {
  def apply(in: Array[Byte])(params: Params = Params()): Array[Byte] = {
    val dict: mutable.Map[Int, Seq[Byte]] = Helpers.initialDecoderDict

    val reader = new BitReader(in)
    val res = mutable.Buffer[Byte]()


    var currentCodeNumber = params.startCode
    var codeLength = params.initialCodeLength
    var prevCode: Int = reader.readBits(codeLength) //init prevCode with first code
    var prevSeq: Seq[Byte] = if (prevCode == params.eofCode) Seq() else dict(prevCode) //process empty input seq case
    res ++= prevSeq

    var EOF: Boolean = prevCode == params.eofCode
    while (!EOF) {
      var currentCode = reader.readBits(codeLength)
      if (currentCode == params.eofCode) EOF = true
      else if (currentCode == params.resetCode) {} //todo
      else {
        dict.get(currentCode) match {
          case Some(codedSeq) =>
            //add to dictionary previous pattern + first char of current pattern
            val toDict = prevSeq ++ codedSeq

            if (params.showDebug && params.showDebugNewCodes) {
              if (params.bytesAsChars)
                println(f"new code for ${prevCode + s" + \'${codedSeq.head.toChar}\'"}%12s  ${s"\"${toDict.map(_.toChar).mkString}\""}%-8s is $currentCodeNumber%5d - ${currentCodeNumber.toBinaryString}%-20s")
              else
                println(f"new code for ${prevCode + s" + \'${codedSeq.head}\'"}%12s  ${s"\"${toDict.mkString}\""}%-8s is $currentCodeNumber%5d - ${currentCodeNumber.toBinaryString}%-20s")
            }

            dict += currentCodeNumber -> toDict
            currentCodeNumber += 1

            //output decoded data
            res ++= codedSeq
            prevSeq = codedSeq
            prevCode = currentCode
          case None =>
            //duplicate previous pattern
            val toDict = prevSeq :+ prevSeq.head

            if (params.showDebug && params.showDebugNewCodes) {
              if (params.bytesAsChars)
                print(f"new code for ${prevCode + s" + \'${prevSeq.head.toChar}\'"}%12s  ${s"\"${toDict.map(_.toChar).mkString}\""}%-8s is $currentCodeNumber%5d - ${currentCodeNumber.toBinaryString}%-20s")
              else
                print(f"new code for ${prevCode + s" + \'${prevSeq.head}\'"}%12s  ${s"\"${toDict.mkString}\""}%-8s is $currentCodeNumber%5d - ${currentCodeNumber.toBinaryString}%-20s")
              println(s" duplicating previous code")
            }
            dict += currentCodeNumber -> toDict
            currentCodeNumber += 1

            // output decoded data
            res ++= prevSeq ++ prevSeq //todoCheck
            prevSeq = prevSeq
            prevCode = currentCode
        }
      }
    }


    res.toArray
  }
}
