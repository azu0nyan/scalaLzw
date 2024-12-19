package simplelzw

import scala.collection.mutable

object Encode {
  def encode(input: String): Seq[Int] = {
    var maxCode = 0
    val dict = mutable.Map[String, Int]()
    val result = mutable.Buffer[Int]()
    for(char <- input.distinct.sorted)
      dict += (char.toString -> maxCode)
      maxCode += 1

    var current: String = input(0).toString //x
    var currentId = 1
    while(currentId < input.length) {
      val toCheckIfInDict = current + input(currentId) //Считать очередной символ Y из сообщения.
      if(dict.contains(toCheckIfInDict)) //Если фраза XY уже имеется в словаре
        current = toCheckIfInDict // то присвоить входной фразе значение XY и перейти к Шагу 2
      else {
        result += dict(current) // Иначе выдать код для входной фразы  X
        dict += toCheckIfInDict -> maxCode //добавить XY в словарь
        maxCode += 1
        current = input(currentId).toString //присвоить входной фразе значение Y
      }
      currentId += 1
    }
    result += dict(current) //Если Y — это символ конца сообщения, то выдать код для X

    result.toSeq
  }


  def main(args: Array[String]): Unit = {
    print(encode("abacabadabacabae"))
  }
}
