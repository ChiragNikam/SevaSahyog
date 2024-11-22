package com.learn.sevasahyog.auth.data.dataclass

data class SignInResponseNgo(
    val token: String = "",
    val ngoAccount: NgoAccount = NgoAccount()
)

data class NgoAccount(
    val userId: String = "",
    val profileImage: String = "",
    val userName: String = "",
    val mobileNo: String = "",
    val email: String = "",
    val password: String = "",
    val ngoImage: String = "",
    val ngoName: String = "",
    val location: String = "",
    val aboutNgo: String = ""
)
