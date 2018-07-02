package com.example.hyeonjung.google2018sample

interface UserInterface {
    val nickname: String
}

class PrivateUser(override val nickname: String) :UserInterface


class SubscribingUser(val email: String) :UserInterface{

    override val nickname : String
    get() = email.substringBefore("@")

}


//class FacebookUser(val accountId:Int):UserInterface{
//    override val nickname = getFacebookName()
//}