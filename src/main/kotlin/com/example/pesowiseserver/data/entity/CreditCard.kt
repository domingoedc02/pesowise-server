package com.example.pesowiseserver.data.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "credit_cards")
data class CreditCard(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = "",

    @Column(nullable = false)
    val userId: String = "",

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = true)
    val lastFourDigits: Int? = null,

    @Column(nullable = false)
    val creditLimit: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    val usedAmount: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    val statementDay: String = "",

    @Column(nullable = false)
    val dueDay: String = "",

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null
)
