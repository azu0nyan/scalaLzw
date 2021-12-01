package lzw

import org.scalatest.funsuite.AnyFunSuite

import scala.util.Random

class EncodeDecodeTest extends AnyFunSuite {
  def stringTest(str: String, params:Params = Params(showDebug = true)): Unit = {
    val arr = str.toArray.map(_.toByte)
    if (params.showDebug) println("input: " + new String(arr))
    val encoded = Encoder(arr)(params)
    val decoded = Decoder(encoded)(params)
    if (params.showDebug) println("output: " + new String(decoded))
    assert(arr.sameElements(decoded))
  }

  test("510 test"){//todo
    val p = Params(startCode = 500, maxCode = 512, showDebug = false)
    for (i <- 1 to 16000) {
      val r = new Random(i)
      val str = new String(r.nextBytes(100).map(b => ('a' + ((b + 256) % 10)).toChar))
      println(str)
      stringTest(str, p)
//      stringTest(Helpers.toBinaryStingLittleEndian(i, j), p)
//      stringTest(Helpers.toBinaryStingLittleEndian(~i, j), p)
    }
  }

  test("some text") {
    stringTest("some text")
  }

  test("abababab") {
    stringTest("abababab")
  }

  test("aaaaaa") {
    stringTest("aaaaaa")
  }
  test("aaaaaaaaaaaaaaaaaaa") {
    stringTest("aaaaaaaaaaaaaaaaaaa")
  }

  test("multiple a") {
    for (i <- 1 to 100)
      stringTest("a".repeat(i))
  }
  test("multiple ab") {
    for (i <- 1 to 100)
      stringTest("ab".repeat(i))
  }

  test("long text") {
    val r = new Random(1)
    val sb = new StringBuilder
    val chars = for (i <- 0 until 100000)
      sb.append(('a' + r.nextInt(26)).toChar)
    stringTest(sb.toString(), Params(showDebug = false))
  }
}
