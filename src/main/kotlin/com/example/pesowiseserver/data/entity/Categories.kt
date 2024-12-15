package com.example.pesowiseserver.data.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "categories")
data class Categories(
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    val categoryId: String = "",

    @Column(nullable = false)
    val userId: String? = "",

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = false, precision = 19, scale = 4)
    val budgetAmount: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    val isDefault: Boolean? = false,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null
)
