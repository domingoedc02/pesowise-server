package com.example.pesowiseserver.data.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "bank_accounts")
data class BankAccount (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    @Column(nullable = false)
    val userId: String = "",

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = true)
    val accountNumber: String? = "",

    @Column(nullable = false, precision = 19, scale = 4)
    val balance: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    val cardType: String? = "",

    @Column(nullable = false)
    val color: String = "",

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null
)