package com.example.dripdropapp.data

data class user(
    val firstName: String,
    val lastName: String,
    val email: String,
    var imagePath: String = ""
)
{
    constructor(): this("","","","")
}
