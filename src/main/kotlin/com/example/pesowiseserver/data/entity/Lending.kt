package com.example.pesowiseserver.data.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "lending")
data class Lending(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    @Column(nullable = false)
    val userId: String = "",

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = true)
    val email: String? = null,

    @Column(nullable = false)
    val amount: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    val autoPaymentEmail: Boolean = false,

    @Column(nullable = false)
    val paymentDate: Date? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null


)
