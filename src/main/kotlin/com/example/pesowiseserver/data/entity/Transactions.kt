package com.example.pesowiseserver.data.entity

import com.example.pesowiseserver.util.enum.TransactionType
import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "transactions")
data class Transactions(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment for primary key
    val id: Long? = null,  // Use Long instead of String for PostgreSQL auto-increment primary key

    @Enumerated(EnumType.STRING)  // Store the enum as a string value
    @Column(nullable = false)
    val type: TransactionType,

    @Column(nullable = false)
    val category: String,

    @Column(nullable = true)
    val subcategory: String?,

    @Column(nullable = false)
    val payment: String,

    @Column(nullable = false)
    val receiptUrl: String,

    @Temporal(TemporalType.TIMESTAMP)  // Store transactionDate as a TIMESTAMP
    @Column(nullable = false)
    val transactionDate: Date,

    @Column(nullable = false)
    val amount: String
)
