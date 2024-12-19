package simplelzw

import scala.util.Random

object TestGen {
  def main(args: Array[String]): Unit = {
    println("---------ENCODE------")
    for (i <- 0 until 15) yield {

      val randomChars: Seq[Char] =
        new Random(i).shuffle(('a'.toInt to 'z'.toInt).map(_.toChar).toSeq).take(4).sorted

      val str = new Random(i).nextBytes(16).toSeq.map(x => ((x % 4) + 4) % 4).map(randomChars).mkString("")
      val encoded = Encode.encode(str)
      val decoded = Decode.decode(encoded, randomChars)
      println(i + " " + str + "  " + randomChars.mkString(""))
    }

    println("---------DECODE------")
    for (i <- 0 until 15) yield {

      val randomChars: Seq[Char] =
        new Random(15 + i).shuffle(('a'.toInt to 'z'.toInt).map(_.toChar).toSeq).take(4).sorted

      val str = new Random(15 + i).nextBytes(16).toSeq.map(x => ((x % 4) + 4) % 4).map(randomChars).mkString("")
      val encoded = Encode.encode(str)
      val decoded = Decode.decode(encoded, randomChars)
      println(i + "  " + randomChars.mkString("") + " " + encoded)
    }

    println("---------ENCODE- ANSWERS-----")
    for (i <- 0 until 15) yield {

      val randomChars: Seq[Char] =
        new Random(i).shuffle(('a'.toInt to 'z'.toInt).map(_.toChar).toSeq).take(4).sorted

      val str = new Random(i).nextBytes(16).toSeq.map(x => ((x % 4) + 4) % 4).map(randomChars).mkString("")
      val encoded = Encode.encode(str)
      val decoded = Decode.decode(encoded, randomChars)
      println(i + "  " + str + " " + randomChars.mkString("") + " " + encoded + " " + decoded)
    }

    println("---------DECODE---ANSERS---")
    for (i <- 0 until 15) yield {

      val randomChars: Seq[Char] =
        new Random(15 + i).shuffle(('a'.toInt to 'z'.toInt).map(_.toChar).toSeq).take(4).sorted

      val str = new Random(15 + i).nextBytes(16).toSeq.map(x => ((x % 4) + 4) % 4).map(randomChars).mkString("")
      val encoded = Encode.encode(str)
      val decoded = Decode.decode(encoded, randomChars)
      println(i + "  " + str + " " + randomChars.mkString("") + " " + encoded + " " + decoded)
    }


  }

}
