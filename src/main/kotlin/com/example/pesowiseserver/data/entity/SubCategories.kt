package com.example.pesowiseserver.data.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "subcategories")
data class SubCategories(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val categoriesId: String? = "",

    @Column(nullable = false)
    val name: String? = "",

    @Column(nullable = false, precision = 19, scale = 4)
    val budgetAmount: BigDecimal = BigDecimal.ZERO,

    val isDefault: Boolean = true,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null
)
