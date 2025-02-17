package com.example.pesowiseserver.data.entity

import com.example.pesowiseserver.util.enum.AccountTypeEnum
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "accounts")
data class Accounts(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val accountId: String? = null,

    @Column(nullable = false)
    var userId: String = "",

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var isActive: Boolean = true,

    @Column(nullable = false)
    var accountType: AccountTypeEnum = AccountTypeEnum.PERSONAL,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null
)
