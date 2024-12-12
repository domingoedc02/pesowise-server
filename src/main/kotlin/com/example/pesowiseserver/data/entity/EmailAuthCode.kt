package com.example.pesowiseserver.data.entity

import com.example.pesowiseserver.util.enum.AuthTypeEnum
import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "authenticationCode")
data class EmailAuthCode(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment for primary key
    val id: Long? = null,  // Use Long instead of String for auto-increment primary key

    @Column(nullable = false)
    val accountId: String,

    @Column(nullable = false)
    val referenceId: String,

    @Column(nullable = false)
    val code: String,

    @Enumerated(EnumType.STRING)  // Store the enum as a string value
    @Column(nullable = false)
    val type: AuthTypeEnum,

    @Column(nullable = false)
    var isUsed: Boolean = false,

    @Temporal(TemporalType.TIMESTAMP)  // Use TemporalType.TIMESTAMP to store the Date as a timestamp
    @Column(nullable = false)
    val dateIssued: Date,

    @Temporal(TemporalType.TIMESTAMP)  // Store expiry date as a timestamp
    @Column(nullable = false)
    val dateExpiry: Date
)
