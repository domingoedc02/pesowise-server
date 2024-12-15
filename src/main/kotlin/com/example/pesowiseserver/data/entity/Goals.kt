package com.example.pesowiseserver.data.entity

import com.example.pesowiseserver.util.enum.RecurringPaymentEnum
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "goals")
data class Goals(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    val userId: String = "",

    val name: String = "",

    val targetAmount: BigDecimal = BigDecimal.ZERO,

    val currentAmount: BigDecimal = BigDecimal.ZERO,

    val startDate: Date = Date(),

    val endDate: Date? = null,

    val recurringPayment: RecurringPaymentEnum = RecurringPaymentEnum.DAILY,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null
)
