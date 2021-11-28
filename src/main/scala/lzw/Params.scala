package lzw

case class Params(
                   startCode:Int = 258,
                   resetCode:Int = 256,
                   eofCode:Int = 257,
                   maxCode:Int = 4096, //todo make working
                   initialCodeLength:Int  = 10,
                   showDebug:Boolean = true,
                   showDebugNewCodes:Boolean = true,
                   showDebugWrittenCodes:Boolean = true,
                   showDebugBytes:Boolean = true,
                   debugBytesCap: Int = 2048,
                   bytesAsChars:Boolean = true
                 )
