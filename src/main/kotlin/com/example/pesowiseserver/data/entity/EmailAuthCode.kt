package com.example.pesowiseserver.data.entity

import com.example.pesowiseserver.util.enum.AuthTypeEnum
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.Date

@Entity
@Table(name = "authenticationCode")
data class EmailAuthCode(
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    val authId: String = "",

    @Column(nullable = false)
    var userId: String = "",

    @Column(nullable = false, unique = true)
    var referenceId: String = "",

    @Column(nullable = false)
    var code: String = "",

    @Enumerated(EnumType.STRING)  // Store the enum as a string value
    @Column(nullable = false)
    val type: AuthTypeEnum = AuthTypeEnum.REGISTER,

    @Column(nullable = false)
    var isUsed: Boolean = false,

    @Temporal(TemporalType.TIMESTAMP)  // Use TemporalType.TIMESTAMP to store the Date as a timestamp
    @Column(nullable = false)
    var dateIssued: Date = Date(),

    @Temporal(TemporalType.TIMESTAMP)  // Store expiry date as a timestamp
    @Column(nullable = false)
    var dateExpiry: Date = Date(),

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null
)
