package com.example.hyeonjung.google2018sample

import android.util.Log
import java.io.Serializable

interface Clickable {
    fun click()

    fun showOff() {
        Log.d(TAG, "I'm Clickable!")
    }
}

interface Focusable {
    fun setFocus(b: Boolean) = Log.d(TAG, "I ${if (b) "got" else "lost"} focus.")

    fun showOff() {
        Log.d(TAG, "I'm focusable")
    }
}

class CustomButton : Clickable, Focusable {
    override fun click() {
        Log.d(TAG, "I was Clicked")
    }

    // 이름과 시그니처가 같은 멤버 메소드에 대해 둘 이상의 디폴트 구현이 있는 경우 인터페이스를 구현하는 하위 클래스에서 명시적으로 새로운 구현을 제공해야 한다.
    override fun showOff() {

        // 상위 타입의 을을 꺽쇠 괄호 (<>) 사이에 넣어서 "super"를 지정하면 어떤 상위 타입의 멤버 메소드를 호출할지 지정할 수 있다.
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }

    // 상속한 구현 중 단 하나만 호출해도 된다면 다음과 같이 쓸 수 있다.
    // override fun showOff() = super<Clickable>.showOff()
}

// 코틀린의 클래스와 메소드는 기본적으로 final 이다.
// 어떤 클래스의 상속을 허용하려면 클래스 앞에 open 변경자를 붙여야 한다. 그와 더불어 오버라이드를 허용 하고 싶은 메소드나 프로퍼티 앞에도 open 변경자를 붙여야 한다.
// 해당 클래스는 열려 있다. 다른 클래스가 이 클래스를 상속 할 수 있다.
open class RichButton : Clickable {

    // 이 함수는 열려 있는 메소드를 오버라이드한다. 오버라이드 한 함수는 기본적으로 열려 있다.
    override fun click() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // 이 함수는 파이널이다.
    fun disable() {}

    // 이 함수는 열려 있다.
    open fun animatate() {}
}

internal open class TalkativeButton : Focusable {
    private fun yell() = Log.d(TAG, "Hey!")
    protected fun whisper() = Log.d(TAG, "Let's talk!")
}

// 코틀린은 public 함수인 giveSpeech 안에서 그보다 가시성이 더 낮은( 이 경우 internal) 타입인 TalkativeButton 을 참조하지 못하게 한다.
// 여기서 컴파일 오류를 없애려면 giveSpeech 확장 함수의 가시성을 internal 로 바꾸거나, TalkativeButton 클래스의 가시성을 public 으로 바꿔야 한다.

// 오류 : "public" 멤버가 자신의 internal 수신 타입인 "TalkativeButton"을 노출함
//fun TalkativeButton.giveSpeech(){
//
// 오류 : "yell"에 접근할 수 없음. "yell"은 "TalkativeButton"의 "private" 멤버임.
//    yell()
//
// 오류 : "whisper"에 접근할 수 없음. "whisper"은 "TalkativeButton"의 "protected" 멤버임.
//    whisper()
//}

interface State : Serializable

interface View {
    fun getCurrentState(): State
    fun restoreState(state: State) {}
}

// 코틀린의 중첩클래스가 자바와의 차이점은 명시적으로 요청하지 않는 한 바깥쪽 클래스 인터스턴에 대한 접근 권한이 없다는 점이다.

// 선언한 버튼의 상태를 직렬화하면 java.io.NotSerializableException: Button 이라는 오류가 발생한다.
// 이 예제의 ButtonState 클래스는 바깥쪽 Button 클래스에 대한 참조를 묵시적으로 포함한다. 그 참조로 인해 ButtonState 를 직렬화할 수 없다.
// Button 을 직렬화할 수 없으므로 버튼에 대한 참조가 ButtonState 의 직렬화를 방해한다.
// 이 문제를 해결하려면 ButtonState 를 static 클래스로 선언해야 한다.
// 자바에서 중첩 클래스를 static 으로 선언하면 그 클래스를 둘러싼 바깥쪽 클래스에 대한 묵시적인 참조가 사라진다.

/* 자바 */
//public class Button implements View{
//    @Override
//    public state getCurrentState() {
//        return new ButtonState ();
//    }
//
//    @Override
//    public void restoreState(State state) { /*...*/ }
//
//    public class ButtonState(State state) implements State { /*...*/ }
//}

class Button : View {
    override fun getCurrentState(): State = ButtonState()

    override fun restoreState(state: State) {
        super.restoreState(state)
        Log.d(TAG, "restoreState")
    }

    // 코틀린 중첩 클래스에 아무런 변경자가 붙지 않으면 자바 static 중첩 클래스 와 같다.
    class ButtonState : State
}

// 중첩 클래스 안에는 바깥쪽 클래스에 대한 참조가 없지만 내부 클레스에는 없다.
// 코틀린에서 내부 클래스 Inner 안에서 바깥쪽 클래스 Outer의 참조에 접근하려면 this@Outer 라고 써야 한다.
class Outer {
    inner class Inner {
        fun getOuterReference(): Outer = this@Outer
    }
}

// 코틀린 컴파일러는 when 을 사용해 Expr 타입의 값을 검사할 때 꼭 디폴트 분기인 else 분기를 덧붙이게 강제한다.
// 이 예제의 else 분기에서는 반환할 만한 의미 있는 값이 없으므로 예외를 던진다.
// 항상 디폴트 분기를 추가하는 게 편하지는 않다. 그리고 디폴트 분기가 있으면 이런 클래스 계층에 새로운 하위 클래스를 추가하더라도 컴파일러가 when 이 모튼 경우를 처리하는지\
// 제대로 검사할 수 없다. 혹 실수로 새로운 클래스 처리를 잊어버렸더라도 디폴트 분기가 선택되기 때문에 심각한 버그가 발생할 수 있다.
interface Expr1

class Num(val value: Int) : Expr1
class Sum(val left: Expr1, val right: Expr1) : Expr1

fun eval1(e: Expr1): Int =
        when (e) {
            is Num -> e.value
            is Sum -> eval1(e.left) + eval1(e.right)
            else ->
                throw IllegalAccessException("Unknow espression")
        }


// 코틀린은 이런 문제애 대한 해법을 제공한다. sealed 클래스가 그 답이다.
// 상위 클래스에 sealed 변경자를 붙이면 그 상우이 클래스를 상속한 하위 클래스 정의를 제한 할 수 있다.
// sealed 클래스의 하위 클래스를 정의 할 때는 반드시 상위 클래스 안에 중첩시켜야 한다.

// 기반 클래스를 sealed 로 봉인한다.
// sealed 로 표시된 클래스는 자동으로 open 임을 기억하라. 따라서 별도로 open 변경자를 붙일 필요가 없다.
sealed class Expr {

    // 기반 클래스의 모든 하위 클래스를 중첩 클래스로 나열한다.
    class Num(val value: Int) : Expr()

    class Sum(val left: Expr, val right: Expr) : Expr()

}

// "when" 식이 모든 하위 클래스를 검사하므로 별도의 "else" 분기가 없어도 된다.
// when 식에서 sealed 클래스의 모든 하위 클래스를 처리한다면 디폴트 분기가(else)가 필요없다.
// sealed 클래스에 속한 값에 대해 디폴트 분기를 사용하지 않고 when 식을 사용하면 나중에 sealed 클래스의 상속 게층에 새로운 하위 클래스를 추가해도
// when 식이 컴파일 되지 않는다. 따라서 when 식을 고쳐야 한다는 사실을 쉽게 알 수 있다. 내부적으로 Expr 클래스트 private 생성자를 가진다.
// 그 생성자는 클래스는 내부에서만 호출할 수 있다. sealed 인터페이스를 정의할 수는 없다.
// 왜 그럴까? 봉인된 인터페이스를 만들 수 있다면 그 인터페이스를 자바 쪽에서 구현하지 못하게 막을 수 있는 수단이 코틀린 컴파일러에게 없기 때문이다.
fun eval(e: Expr): Int =
        when (e) {
            is Expr.Num -> e.value
            is Expr.Sum -> eval(e.left) + eval(e.right)
        }


// 아래 3개의 클래스 초기화는 모두 같은 내용
class User2(val nickname: String)

class User3 constructor(val _nickname: String) {
    val nickname: String

    init {
        nickname = _nickname
    }
}

// val 은 이 파라미터에 상응하는 프로퍼티가 생성된다는 뜻이다.
class User4 constructor(val _nickname: String) {
    val nickname: String = _nickname
}

// 생성자 파라미터에 대한 디폴트 값을 제공한다.
class Usere5(val nickname: String, val isSubscribed:Boolean = true)

// isSubscribed 파라미터에는 디폴트 값이 쓰인다.
val hyun = Usere5("현석")
// 모든 인자를 파라미터 선언 순서대로 지정할 수도 있다.
val gye = Usere5("계영",false)
// 생성 인자 중 일부에 대해 이름을 지정할 수도 있다.
val hey = Usere5("헤원",isSubscribed = false)

// 클래스에 기반 클래스가 있다면 주 생성자에서 기반 클래스의 생성자를 호출해야 할 필요가 있다.
// 기반 클래스를 초기화하려면 기반 클래스 이름 뒤에 괄호를 치고 생성자 인자를 넘긴다.
open class User6(val nickname: String)
class TwitterUser(nickname: String) :User6(nickname)

// 인자가 없는 디폴트 생성자가 만들어진다.
open class Button2

// Button 의 생성자는 아무 인자도 받지 않지만, Button 클래스를 상속한 하위 클래스는 반드시 Button 클래스의 생성자를 호출해야 한다.
class RadioButton:Button2()

// 이 규칙으로 인해 기반 클래스의 이름 뒤에는 꼭 빈 괄호가 들어간다( 물론 생성자 인자가 있다면 괄호 안에 인자가 들어간다).
// 반면 인터페이스는 생성자가 없기 때문에 어떤 클래스가 인터페이스를 구현하는 경우 그 클래스의 상위 클래스 목록에 있는 인터페이스 이름 뒤에는 아무 괄호도 없다.
// 클래스 정의에 있는 상위 클래스 및 인터페이스 목록에서 이름 뒤에 괄호가 붙었는지 살펴보면 쉽게 귀반 클래스와 인터페이스를 구별할 수 있다.

// 어떤 클래스를 클래스 외부에서 인스턴화하지 못하게 막고 싶다면 모든 생성자를 private 으로 만들면 된다.
// 다음과 같이 주 생성자에 private 변경자를 붙일 수 있다.
class Secreteive private constructor()


