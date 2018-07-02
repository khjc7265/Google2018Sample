package com.example.hyeonjung.google2018sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
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

        Log.d(TAG, (client1 == client2).toString())

        val processed = hashSetOf(Client("영미", 4111))
        // client 클래스에 hashcode 가 정의되어 있지 않을 경우 false 반환.
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
