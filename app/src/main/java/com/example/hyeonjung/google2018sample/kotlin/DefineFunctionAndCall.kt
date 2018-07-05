@file:JvmName("DefineFunctionAndCall")

package com.example.hyeonjung.google2018sample.kotlin

import android.util.Log
import android.view.View
import android.widget.Button
import java.io.File

// 자바에서는 일부 클래스에서 오버로딩한 메소드가 너무 많아진다는 문제가 있다.
// 코틀린에서는 함수 선언에서 파라미터의 디폴트 값을 지정할 수 잇으므로 이런 오버로드 중 상당수를 피할 수 있다.

// 자바에서는 모든 코드를 클래스의 메소드로 작성해야 한다.
// 코틀린에서는 Util 과 같은 무의미한 클래스가 필요 없다. 대신 함수를 직접  소스 파일의 최상위 수준, 모든 다른 클래스의 밖에 위치시면 된다.
// 그런 함수들은 여전히 그 파일의 맨 앞에 정의된 패키지의 멤버 함수이므로 다른 패키지에서 그 함수를 사용하고 싶을 때는 그 함수가 정의된 패키지를 임포트 해야 한다.
// 하지만 임포트 시 유틸리티 클래스 이름이 추가로 들어갈 필요는 없다.

fun <T> Collection<T>.joinToString(
        separator: String = ", ",
        prefix: String = "",
        postfix: String = ""
): String {
    val result = StringBuilder(prefix)

    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }

    result.append(postfix)
    return result.toString()
}


fun Collection<String>.join(
        separator: String = ", ",
        prefix: String = "",
        postfix: String = ""
) = joinToString(separator, prefix, postfix)


fun String.lastChar(): Char = this[this.length - 1]

val String.lastChar: Char
    get() = get(length - 1)

var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value) {
        this.setCharAt(length - 1, value)
    }


fun View.showOff() = Log.d(TAG, "i'm a view!")
fun Button.showOff() = Log.d(TAG, "i'm a button!")


fun parsePath(path: String) {
    val directory = path.substringBeforeLast("/")
    val fullName = path.substringAfterLast("/")
    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")

    Log.d(TAG, "Dir: $directory, name: $fileName, ext: $extension")
}

fun parsePathWithRegex(path: String) {

    // (.+) = 디렉터리 // 따로 지정하지 않으면 정규식 엔진은 각 패턴을 가능한 한 가장 긴 부분 문자열과 매치하려고 시도한다.
    // / = 마지막 슬래시
    // (.+) = 파일이름
    // \. = 마지막점
    // (.+) = 확장자
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if (matchResult != null) {
        val (directory, filename, extension) = matchResult.destructured
        Log.d(TAG, "Dir: $directory, name: $filename, ext: $extension")

    }
}




