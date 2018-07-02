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

class User(val id: Int, val name: String) {
    constructor(name: String) : this(0, name)

    var address: String = ""
        set(value) {
            Log.d(TAG, """
                Address was changed for $name : "$field" -> "$value".
                """.trimIndent())
            field = value
        }

}


/// 로컬 함수에서 바깥 함수의 파라미터 접근하기. 로컬 함수는 자신이 속한 바깥 함수의 모든 파라미터와 변수를 사용할 수 있다.
fun saveUser(user: User) {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
                    //user 접근 가능
                    "Can't save user ${user.id}: empty $fieldName"
            )
        }
    }

    try {
        // 로컬 함수를 호출해서 각 필드를 검증
        validate(user.name, "Name")
        validate(user.address, "Address")
    } catch (e: Exception) {
        Log.d(TAG, e.message)
    }
}

// 검증 로직을 확장 함수로 추출하기
fun User.validateBeforeSave() {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
                    "Can't save user $id: empty $fieldName"
            )
        }
    }
    validate(name, "Name")
    validate(address, "Address")
}

fun saveUser2(user: User) {
    try {
        user.validateBeforeSave()
    } catch (e: Exception) {
        Log.d(TAG, e.message)
    }
}

class LengthCounter {
    //counter value private 으로 설정.
    var counter: Int = 0
        private set

    fun addWord(word: String) {
        counter += word.length
    }
}

class Client(val name: String, private val postalCode: Int) {
    override fun equals(other: Any?): Boolean {
        if (other === null || other !is Client)
            return false
        return name === other.name && postalCode == other.postalCode
    }

    override fun toString() = "Client(name = $name, postalCode = $postalCode)"

    //equals 를 오버라이드 할 때는 반드시 hashcode 도 함께 오버라이드 해야 한다.
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + postalCode
        return result
    }
}

// data 클래스는 equals/toString/hashCode 함수를 컴파일러가 자동으로 만들어준다.
data class Client2(val name: String, private val postalCode: Int)

// by 키워드를 통해 인터페이스에 대한 구현을 다른 객체에 위임 중이라는 사실을 명시
class Delegatingcollection<T>(
        innerList: Collection<T> = ArrayList()
) : Collection<T> by innerList

// mutableCollection 과 Collection 차이 : http://aroundck.tistory.com/4861
// primitive type / wrapper type : http://jusungpark.tistory.com/17
class CountingSet<T>(
        private val innerSet: MutableCollection<T> = HashSet()
) : MutableCollection<T> by innerSet {
    var objectAdded = 0

    override fun add(element: T): Boolean {
        objectAdded++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectAdded += elements.size
        return innerSet.addAll(elements)
    }
}

// object 키워드를 통해 싱글톤 생성.
object Payroll {
    val allEmployees = arrayListOf<Person>()
    fun calculateSalary() {
        for (person in allEmployees) {

        }
    }
}

object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(file1: File, file2: File): Int {
        return file1.path.compareTo(file2.path, ignoreCase = true)
    }
}

data class Person(val name: String) {

}






