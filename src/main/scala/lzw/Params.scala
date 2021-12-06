package lzw

case class Params(
                   startCode:Int = 258,
                   resetCode:Int = 256,
                   eofCode:Int = 257,
                   maxCode:Int = 16535,
                   showDebug:Boolean = true,
                   showDebugNewCodes:Boolean = true,
                   showDebugWrittenCodes:Boolean = true,
                   showDebugBytes:Boolean = true,
                   debugBytesCap: Int = 16536,
                   bytesAsChars:Boolean = true,
                   showDebugReading:Boolean = true
                 )
