package com.example.e_scheduler.entity

data class User(
    val uid: String = "",
    val userName: String = "",
    val branch: String = "",
    val semester: String = "",
    val className: String = "",
    val batch: String = "",
    val address: String = "",
    val fatherName: String = "",
    val bloodGrp: String = "",
    val fatherNumber: String = "",
    val mobileNumber: String = "",
    val enrNo: String= "",
    val email: String = "",
    val role: String = "Student",
    val validated: Boolean = false,
    val additionalDetailsAdded: Boolean = false
)