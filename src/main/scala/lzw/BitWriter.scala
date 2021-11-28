package lzw

import scala.collection.mutable

class BitWriter(buffer: mutable.Buffer[Byte] = mutable.Buffer(), var bitsTotal: Int = 0) {
  def putBit(bit: Int): Unit = {
    val bufferPos = bitsTotal >> 3
    val bitPos = bitsTotal & 0x00000007
    if (bufferPos >= buffer.size) buffer += 0.toByte
    buffer(bufferPos) = (buffer(bufferPos) | (bit << bitPos)).toByte
    bitsTotal += 1
  }

  def toArray: Array[Byte] = buffer.toArray

  def writeBitString(string: String): Unit = {
    for (i <- string.indices)
      putBit(if (string(i) == '0') 0 else 1)
  }

  def writeBits(toWrite: Int, bits: Int): Unit = {
    for (bit <- 0 until bits)
      putBit((toWrite >> bit) & 1)
  }
}
