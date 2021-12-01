package lzw

import scala.collection.immutable.{AbstractSeq, LinearSeq}
import scala.collection.mutable

object Decoder {
  def apply(in: Array[Byte])(params: Params = Params()): Array[Byte] = {
    val reader = new BitReader(in)

    val res = mutable.Buffer[Byte]()

    var dict: mutable.Map[Int, Seq[Byte]] = mutable.Map()
    var currentCodeNumber = -1
    var prevCode: Int = -1 //init prevCode with first code
    var prevSeq: Seq[Byte] = Seq()
    def reset(): Unit = {
      dict = Helpers.initialDecoderDict
      currentCodeNumber = params.startCode
      if (params.showDebug) println(s"Set current code to $currentCodeNumber")
      prevCode = reader.readBits(Helpers.codeLength(currentCodeNumber))
      if (params.showDebug && params.showDebugReading)  println(s"Read bits ${Helpers.codeLength(currentCodeNumber)} result:$prevCode")
      prevSeq = if (prevCode == params.eofCode) Seq() else dict(prevCode) //process empty input seq case
      res ++= prevSeq
      if (params.showDebug && params.showDebugWrittenCodes)
        println(s"emitting ${prevSeq.map(_.toChar).mkString("")}")

    }
    reset()

    def codeLength: Int = Helpers.codeLength(currentCodeNumber + 1)


    var EOF: Boolean = prevCode == params.eofCode
    while (!EOF) {
      var currentCode = reader.readBits(codeLength)
      if (params.showDebug && params.showDebugReading) println(s"Read bits ${codeLength} result:$currentCode")
      if (currentCode == params.eofCode) EOF = true
      else if (currentCode == params.resetCode) {
        reset()
      } else {
        dict.get(currentCode) match {
          case Some(codedSeq) =>
            //add to dictionary previous pattern + first char of current pattern
            val toDict = prevSeq :+ codedSeq.head

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
            if (params.showDebug && params.showDebugWrittenCodes) println(s"emitting ${codedSeq.map(_.toChar).mkString("")}")
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
            res ++= toDict //todoCheck
            if (params.showDebug && params.showDebugWrittenCodes) println(s"emitting ${toDict.map(_.toChar).mkString("")}")
            prevSeq = toDict
            prevCode = currentCode
        }
      }
    }


    res.toArray
  }
}
