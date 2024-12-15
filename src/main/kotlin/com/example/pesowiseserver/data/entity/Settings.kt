package com.example.pesowiseserver.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "settings")
data class Settings(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    @Column(nullable = false)
    val userId: String = "",

    @Column(nullable = false)
    val isEmailNotificationOptOn: Boolean = true
)
