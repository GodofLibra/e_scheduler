package com.example.e_scheduler.entity

data class Material(
    val uid: String = "",
    val sem: Int = 0,
    val branch: String = "",
    val uploadTime: Long = 0L,
    val topic: String = "",
    val description: String = "",
    val documentUrl: String = ""
)
