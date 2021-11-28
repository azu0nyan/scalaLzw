package lzw

object ReaderWriterTest extends App {
  val w = new BitWriter()
  w.writeBits(1, 8)
  w.writeBits(127, 8)
  w.writeBits(128, 8)
  w.writeBits(255, 8)
  w.writeBits(48, 8)
  w.writeBits(1, 10)
  w.writeBits(2, 32)
  w.writeBits(243234, 32)
  w.writeBits(3, 14)
  val arr = w.toArray
  println(arr.map(Helpers.byteToStringReversedFromLittleEndian).mkString(","))
  println(arr.map(Helpers.byteToStringLittleEndian).mkString(","))
  val r = new BitReader(arr)
  println(r.readBits(8))
  println(r.readBits(8))
  println(r.readBits(8))
  println(r.readBits(8))
  println(r.readBits(8))
  println(r.readBits(10))
  println(r.readBits(32))
  println(r.readBits(32))
  println(r.readBits(14))
}
