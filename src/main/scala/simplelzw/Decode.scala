package simplelzw

import scala.collection.mutable

object Decode {
  def decode(input: Seq[Int], alphabet: Seq[Char]): String = {
    var maxCode = 0
    val dict = mutable.Map[Int, String]()
    for (char <- alphabet)
      dict += (maxCode -> char.toString)
      maxCode += 1

    var prevString = dict(input(0))
    var result = prevString
    for(currentCode <- input.tail) {
      if(dict.contains(currentCode)) {
        val inDict = dict(currentCode)
        val toDict = prevString + inDict.head
        dict += (maxCode -> toDict)
        maxCode += 1

        result += inDict
        prevString = inDict
      } else {
        val toDict = prevString :+ prevString.head
        dict += (maxCode -> toDict)
        maxCode += 1
        result += toDict
        prevString = toDict
      }
    }

    result

  }


  def main(args: Array[String]): Unit = {
    print(decode(List(0, 1, 0, 2, 5, 0, 3, 9, 8, 6, 4), Seq('a', 'b', 'c', 'd', 'e')))
  }
}
