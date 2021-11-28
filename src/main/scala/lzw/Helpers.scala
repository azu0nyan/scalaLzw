package lzw

import scala.collection.mutable

object Helpers {

  def initialEncoderDict: mutable.Map[Seq[Byte], Int] = {
    val dict: mutable.Map[Seq[Byte], Int] = mutable.Map()
    for (i <- Byte.MinValue to Byte.MaxValue)
      dict += Seq(i.toByte) -> i
    dict
  }

  def initialDecoderDict: mutable.Map[Int, Seq[Byte]] = {
    val dict: mutable.Map[Int, Seq[Byte]] = mutable.Map()
    for (i <- Byte.MinValue to Byte.MaxValue)
      dict += i -> Seq(i.toByte)
    dict
  }

  def toBinaryStingLittleEndian(number: Int, length: Int): String = {
    var res = ""
    for (bit <- 0 until length)
      res = res + ((number >> bit) & 1).toString
    res
  }

  def byteToStringReversedFromLittleEndian(b: Byte): String = {
    var res = ""
    for (i <- 0 until 8) {
      res = ((b >> i) & 1).toString + res
    }
    res
  }

  def byteToStringLittleEndian(b: Byte): String = {
    var res = ""
    for (i <- 0 until 8) {
      res = res + ((b >> i) & 1).toString
    }
    res
  }

  def printByGroups(arr: Array[Byte], cap: Int, groupLength: Int = 8, groupInLine: Int = 10): Unit = {
    val toStr = arr.toSeq.take(cap)
    val strGroups:Seq[String] = toStr.map(Helpers.byteToStringLittleEndian).reduce(_ + _).grouped(groupLength).map(str => str + " ").toSeq
    val lines:Seq[Seq[String]] = strGroups.grouped(groupInLine).toSeq
    val str = lines.map(l => l.reduce(_ + _)).mkString("\n")
    val finalStr = if(arr.length > cap) str + "....." else str
    println(finalStr)
  }

}
