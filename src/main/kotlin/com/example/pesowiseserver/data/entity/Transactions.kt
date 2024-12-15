package com.example.pesowiseserver.data.entity

import com.example.pesowiseserver.util.enum.TransactionTypeEnum
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.Date

@Entity
@Table(name = "transactions")
data class Transactions(
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    val transactionId: String? = "",

    @Enumerated(EnumType.STRING)  // Store the enum as a string value
    @Column(nullable = false)
    val type: TransactionTypeEnum = TransactionTypeEnum.INCOME,

    @Column(nullable = false)
    val category: String = "",

    @Column(nullable = true)
    val subcategory: String? = "",

    @Column(nullable = false)
    val payment: String = "",

    @Column(nullable = false)
    val receiptUrl: String = "",

    @Temporal(TemporalType.TIMESTAMP)  // Store transactionDate as a TIMESTAMP
    @Column(nullable = false)
    val transactionDate: Date = Date(),

    @Column(nullable = false)
    val amount: String = "",

    @Column(nullable = true)
    val notes: String? = "",

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null
)
