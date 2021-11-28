package lzw

class BitReader(val readFrom: Array[Byte], var bitPosition: Int = 0) {
  def bitAt(pos: Int): Int = {
    val arrayPos = pos >> 3
    val bitPos = pos & 0x00000007
    (readFrom(arrayPos) >> bitPos) & 1
  }

  def readBitString(bits: Int): String = {
    val sb = new StringBuilder
    for (bit <- 0 until bits) {
      sb.append(bitAt(bitPosition + bit))
    }
    bitPosition = bitPosition + bits
    sb.toString()
  }

  /** reads in little-endian */
  def readBits(bits: Int): Int = {
    var res = 0
    for (bit <- 0 until bits) {
      res |= bitAt(bitPosition + bit) << bit
    }
    bitPosition = bitPosition + bits
    res
  }
}
