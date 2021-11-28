package lzw

import org.scalatest.funsuite.AnyFunSuite

class EncodeDecodeTest extends AnyFunSuite{
  def stringTest(str: String): Unit = {
    val arr = str.toArray.map(_.toByte)
    println("input: " + new String(arr))
    val params = Params()
    val encoded = Encoder(arr)(params)
    val decoded = Decoder(encoded)(params)
    println("output: " + new String(decoded))
    assert(arr.sameElements(decoded))
  }

  test("some text"){
    stringTest("some text")
  }

  test("abababab"){
    stringTest("abababab")
  }

  test("aaaaaa"){
    stringTest("aaaaaa")
  }

  test("multiple a"){
    for(i <- 1 to 100)
    stringTest("a".repeat(i))
  }
  test("multiple ab"){
    for(i <- 1 to 100)
      stringTest("ab".repeat(i))
  }
}
