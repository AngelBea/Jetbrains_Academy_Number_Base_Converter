package converter

import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.pow
import kotlin.math.sqrt

//1rst phase
fun askForBaseNumberDecimal() {
    STRING_ASK_DECIMAL.let(::print)
    val decimal = readLine()!!.toInt()
    STRING_ASK_BASE.let(::print)
    val base = readLine()!!.toInt()

    if (base in listOf(2, 8, 16)) {
        STRING_ASK_RESULT.plus(
            when (base) {
                2 -> BaseNumber(decimal).binaryValue
                8 -> BaseNumber(decimal).octalValue
                16 -> BaseNumber(decimal).hexValue
                else -> 0
            }
        ).let(::println)
    }
}

//2nd phase
fun askForBaseNumberBase() {
    STRING_SOURCE_NUM.let(::print)
    val baseNumber = readLine()!!
    STRING_SOURCE_BASE.let(::print)
    val base = readLine()!!.toInt()

    STRING_TO_DECIMAL.plus(baseNumber.toInt(base)).let(::println)
}

fun chooseDecimalOrBase() {
    var option = ""

    while (option != OPTION_EXIT) {
        STRING_DECIMAL_OR_NOT.let(::print)
        option = readLine()!!

        when (option) {
            OPTION_FROM -> askForBaseNumberDecimal()
            OPTION_TO -> askForBaseNumberBase()
        }
    }
}

//3rd phase
fun askForNumberAndBase() {

    STRING_NUMBERS_NUM_BASE.let(::print)
    var option1 = readLine()!!

    while (option1 != OPTION_EXIT) {
        val numbers = option1.split(" ").map { it.toInt() }
        "Enter number in base ${numbers[0]} to convert to base ${numbers[1]} (To go back type $OPTION_BACK) > ".let(::print)

        var option2 = readLine()!!

        if (option2 == OPTION_BACK) {
            askForNumberAndBase()
            option1 = OPTION_EXIT
        } else {
            STRING_ASK_RESULT.plus(
                if (option2.matches("[\\d\\w]*".toRegex())) {
                    option2.toBigInteger(numbers[0]).toString(numbers[1])
                } else if (option2.matches("[\\d\\w]*.[\\d\\w]*".toRegex())) {
                    option2.split(".")
                        .let { "${it[0].toBigInteger(numbers[0]).toString(numbers[1])}.${decimalConversor(it[1], numbers[0], numbers[1]) }" }
                } else {
                    ""
                }
            ).let(::println)
        }
    }
}

//4th phase
fun decimalConversor(decimalNumber: String, sourceBase: Int, targetBase: Int): String {
    val letters = ('a'..'z').zip(10..36)
    var decimal = 0.0
    val toDecimalIndividuals = decimalNumber.map { if(it.isDigit()) it.toString().toInt() else letters.filter { equivalences -> equivalences.first == it }.first().second }

    for((power, idx) in (-1 downTo decimalNumber.length.unaryMinus()).zip(toDecimalIndividuals.indices)){
        decimal += toDecimalIndividuals[idx].toDouble() * sourceBase.toDouble().pow(power)
        //println(decimal)
    }

    val listOfConverted = mutableListOf<String>()

    for (idx in 1..5){
        var decimalResult = decimal * targetBase
        //println(decimalResult)
        var notDecimalPart = decimalResult.toInt()
        //println(notDecimalPart)
        listOfConverted.add(
            if (notDecimalPart in 0..9) decimalResult.toInt().toString() else letters.first { it.second == decimalResult.toInt() }.first.toString()
        )
        decimal = decimalResult - notDecimalPart.toDouble()
    }

    return listOfConverted.joinToString("")
}


