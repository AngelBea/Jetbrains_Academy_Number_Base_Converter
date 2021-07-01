package converter

class BaseNumber(number: Int) {
    val octalValue by lazy { number.toString(8) }
    val hexValue by lazy { number.toString(16).toUpperCase() }
    val binaryValue by lazy { number.toString(2) }
    val decimalValue by lazy { number.toString(10) }
}