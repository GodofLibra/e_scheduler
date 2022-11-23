package com.example.e_scheduler.entity

data class Material(
    val uid: String = "",
    val sem: String = "",
    val branch: String = "",
    val uploadTime: Long = 0L,
    val topic: String = "",
    val description: String = "",
    val documentUrl: String = ""
)
