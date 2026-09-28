package com.example.a24012011122_mad_pr7

data class Contact(
    var id: Long = 0L,
    var name: String,
    var phoneNo: String,
    var emailId: String,
    var address: String,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)