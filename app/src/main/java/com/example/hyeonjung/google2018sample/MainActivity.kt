package com.example.hyeonjung.google2018sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.hyeonjung.google2018sample.kotlin.*
import java.io.File

//import com.example.hyeonjung.google2018sample.strings.lastChar as last

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val testJava = Test()

        testJava.testKotlin()

        val list = listOf(1, 2, 3)

        Log.d(TAG, list.joinToString(" "))
        Log.d(TAG, "Kotlin".last().toString())
        Log.d(TAG, listOf("one", "two", "three").join(" ", "(", ")"))

        Log.d(TAG, "Kotlin".lastChar.toString())

        val sb = StringBuilder("Kotlin?")
        sb.lastChar = '!'
        Log.d(TAG, sb.toString())

        testJava.lastChar("Java")

        val arrayList = Array(3) { i -> (i * i).toString() }
        Log.d(TAG,arrayList.contentToString())

        val spreadList = listOf("list: ", *arrayList)
        Log.d(TAG, spreadList.joinToString())

        Log.d(TAG, "12.456-6.A".split("[.\\-]".toRegex()).toString())

        Log.d(TAG, "12.456-6.A".split("[.|-]".toRegex()).toString())

        Log.d(TAG, "12.456-6.A".split(".", "-").toString())

        parsePath("/Users/yole/kotlin-book/chapter1.adoc")

        parsePathWithRegex("/Users/yole/kotlin-book/chapter2.adoc")

        // 3중 따옴표 문자열에는 줄 바꿈을 표현하는 아무 문자열이나 (이스케이프 없이) 그대로 들어간다.
        val kotlinLogo = """
           .|   //
           .|  //
           .| / \
           """.trimMargin(".")

        Log.d(TAG, kotlinLogo)


        val customBt = CustomButton()
        customBt.click()
        customBt.showOff()
        customBt.setFocus(true)


        val view: View = Button(applicationContext)
        view.showOff()

        val button = Button(applicationContext)
        button.showOff()

        Log.d(TAG, "1 + 2 = " + eval(Expr.Sum(Expr.Num(1), Expr.Num(2))))

        val user = User("Alice")
        saveUser(user)
        saveUser2(user)

        user.address = "서울시 강남구 테헤란로 82길 15"
        Log.d(TAG, "user address is " + user.address)


        val lengthCounter = LengthCounter()
        lengthCounter.addWord("Hi")
        Log.d(TAG, lengthCounter.counter.toString())

        val client1 = Client("a", 1)
        Log.d(TAG, client1.toString())

        val client2 = Client("b", 1)
        Log.d(TAG, client2.toString())

        // 자바에서는 == 를 원시 타입과 참조 타입을 비교할 때 사용한다. 원시 타입의 경우 == 는 두 피연산자의 값이 같은지 비교한다(동등성(equality)).
        // 반면 참조 타입의 경우 == 는 두 피산자의 주소가 같은지를 비교한다.(참조 비교(reference comparision)).
        // 따라서 자바에서는 두 객체의 동등성을 알려면 equals 를 호출해야 한다.
        // 코틀린에서는 == 연산자가 두 객체를 비교하는 기본적인 방법이다. == 는 내부적으로 equals 를 호출해서 객체를 비교한다.
        // 따라서 클래스가 equals 를 오버라이드하면 == 를 통해 안전하게 그 클래스의 인스턴스를 비교할 수 있다.
        // 참조 비교를 위해서는 === 연산자를 사용할 수 있다. === 연산자는 자바에서 객체의 참조를 비교할 때 사용하는 == 연산자와 같다.
        Log.d(TAG, (client1 == client2).toString())

        val processed = hashSetOf(Client("영미", 4111))
        // client 클래스에 hashcode 가 정의되어 있지 않을 경우 false 반환.
        // JVM 언어에서는 hashCode 가 지켜야 하는 "equals() 가 true 를 반환하는 두 객체는 반드시 같은 hashCode() 를 반환해야 한다" 라는
        // 제약이 있기 때문이다.
        // HashSet 은 원소를 비교할 때 비용을 줄이기 위해 먼저 객체의 해시 코드를 비교하고 해시 코드가 같은 경우에만 실제 값을 비교한다.
        // HashCode 가 정의되어있지 않으면 두 Client 인스턴스는 해시 코드가 다르기 때문에 두 번째 인스턴스가 집합 안에 들어 있지 않다고 판단한다.
        // 해시 코드가 다를 때 equals 가 반환하는 값은 판단 결과에 영향을 끼치지 못한다.
        // 즉, 원소 객체들이 해시 코드에 대한 규칙을 지키지 않는 경우 HashSet 은 제대로 작동할 수 없다.
        Log.d(TAG, "processed : " + processed.contains(Client("영미", 4111)))

        val client3 = Client2("a", 1)
        Log.d(TAG, client3.toString())

        val client4 = Client2("a", 1)
        Log.d(TAG, client4.toString())

        Log.d(TAG, (client4 == client3).toString())

        val processed2 = hashSetOf(Client2("영미", 4111))
        Log.d(TAG, "processed2 : " + processed2.contains(Client2("영미", 4111)))


        Log.d(TAG, client3.copy(postalCode = 1000).toString())

        val cset = CountingSet<Int>()
        cset.addAll(listOf(1, 1, 2))
        Log.d(TAG, "${cset.objectAdded} objects added, ${cset.size} remain")

        Payroll.allEmployees.add(Person("Kim"))
        Payroll.calculateSalary()

        Log.d(TAG, "CaseInsensitiveComparator : " + CaseInsensitiveFileComparator.compare(
                File("/User"),
                File("/user")))

        // 클래스 인스턴스를  사용할 수 있는 곳에서는 항상 싱글턴 객체를 사용할 수 있다.
        val files = listOf(File("/Z"), File("/a"))
        Log.d(TAG, ("files.sortedWith " + files.sortedWith(CaseInsensitiveFileComparator)))

    }
}
