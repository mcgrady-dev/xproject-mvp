package com.mcgrady.test

/**
 *
 * Created by mcgrady on 2019-10-11.
 */

var age = 18
val name = "mcgrady"
var name2: String? = null

fun main(args: Array<String>) {

    name2 = name
//    printMessageWithPrefix(message = "${name2?.toUpperCase() ?: "mcgrady"}")
//    printMessageWithPrefix("Log", "Hello World")
//    printMessageWithPrefix(message = "Hello Kotlin", prefix = "Log")
//
//    infix fun Int.times(str: String) = str.repeat(this)
//    println(2 times "mcgrady ")
//    printAllWithPrefix("Nicole", "mcgrady", "JennyWang", "MoGui", frefix = "Log")

//    getStringLength(name2 ?: "mcgrady")


    // 区间
//    val a = 10

//    if (a in 1..10) {
//        println(a)
//    }

//    for (i in 1..10) {
//        println(i)
//    }

//    for (i in 10 downTo 1 step 7) {
//        println(i)
//    }

//    for (i in 1 until 10) {
//        println(i)
//    }


    // char to int
//    println(decimalDigitValue('9'))

    //array
//    val array: IntArray = intArrayOf(1, 2, 3)
//    println(array[0])

    // string
//    val text = """
//        |多行字符串|菜鸟教程|多行字符串|Runoob"""
//    val text2 = "|多行字符串|菜鸟教程|多行字符串|Runoob"
//    println(text)
//    println(text2)
//
//    val price ="${'$'}9.99"
//
//    println(price)


    // if else
//    printIfElse()
//    var condtion: Boolean = true
//    val text = if (condtion) "a" else "b"
//    println("text = $text")

    // when
//    printWhen(12)
//    println(hasPrefix("prefix mcgrady"))

//    // array
//    var cakes = listOf("mcgrady", "Nicole", "JennyWang", "MOGUI")
//    var cakes2 = arrayListOf("mcgrady", "Nicole", "JennyWang", "MOGUI")
//    var cakes3 = arrayOf("mcgrady", "Nicole", "JennyWang", "MOGUI")
//    var cakes4 = setOf("mcgrady", "Nicole", "JennyWang", "MOGUI")
//
//    var sum = listOf(cakes, cakes2, cakes3, cakes4)
//
//    // for
//    for (any in sum) {
//        when (any) {
//            is List<*> -> println("is List $any")
//            is ArrayList<*> -> println("is ArrayList $any")
//            is Array<*> -> println("is Array $any")
//            is Set<*> -> println("is Set toList $any.toList()")
//            else -> println("null")
//        }
//    }

//    var names = listOf("mcgrady", "Nicole", "JennyWang", "MOGUI")
//
//    for (name in names) {
//        println(name)
//    }

    val firstName = "Mo"
    val lastName : String? = null

    println("my name is ${getName(firstName, lastName)}")
}

/**
 * 使用关键字vararg来表示Java中的String...
 */
fun hasEmpty(vararg strArray: String?): Boolean {
    for (s in strArray) {
        //相当于Java的 s == null
        s ?: return true
    }
    return false
}

fun getName(firstName: String?, lastName: String? = "unkonw"): String {
//    if (hasEmpty(firstName, lastName)) {
//        lastName?.let {
//            return@getName "${checkName(firstName)} $lastName"
//        }
//        firstName?.let {
//            return@getName "$firstName ${checkName(lastName)}"
//        }
//    }

    return "$firstName $lastName"
}

fun checkName(name: String?) : String = name ?: "Unkonw"

fun printMessageWithPrefix(prefix: String = "Info", message: String) {
    println("[$prefix] $message")
}

fun printAllWithPrefix(vararg messages: String, frefix: String) {

    for (msg in messages) {
        println("[$frefix] $msg")
    }
}

fun getStringLength(obj: Any): Int? {
    if (obj is String) {
        println("$obj is String")
        return obj.length
    }

    return null
}

//    fun decimalDigitValue(c: Char): Int {
//        if (c !in '0'..'9')
//            throw IllegalArgumentException("Out of range")
//        return c.toInt() - '0'.toInt() // 显式转换为数字
//    }

fun printStr(str: String) {
    println("before $str")
    println("after ${str.trim(' ')}")
    for (c in str.trim(' ')) {
        print("$c,")
    }
    println()
}

fun printIfElse() {
    var a = 10
    var b = 12

    println("a = $a b = $b max = ${if (a > b) a else b}")
}

fun printWhen(int: Int) {
//    when (int) {
//        1 -> print("int = 1")
//        2 -> print("int = 2")
//        else -> print("int = $int")
//    }

    val x = 1
    val s = "hello world"
    val res = when {
        x in 1..10 -> "cheap"
        s.contains("hello") -> "it's a welcome!"
        else -> ""
    }

    print(res)    //cheap
}

fun hasPrefix(obj: Any) = when (obj) {
    is String -> {
        println("obj = $obj")
        obj.startsWith("prefix")
    }
    else -> false
}


fun foo() {
    listOf(1, 2, 3, 4, 5).forEach list@{
        if (it == 3) return@list

        print(it)
    }

//    list@ for (i in 1..5) {
//        if (i == 3) break
//
//        print(i)
//    }

    print(" done with explicit label")
}