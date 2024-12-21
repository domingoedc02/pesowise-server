package com.example.pesowiseserver.data.entity

import com.example.pesowiseserver.util.enum.RoleEnum
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name="users")
data class Users(
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    var userId: String = "",

    @Column(nullable = false)
    var firstName: String = "",

    @Column(nullable = false)
    var lastName: String = "",

    @Column(name = "user_name", nullable = false, unique = true)
    var userName: String = "",

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var password: String? = null,

    @Column(name = "is_verified", nullable = false)
    var isVerified: Boolean = false,

    @Column(nullable = true)
    val imageUrl: String? = null,

    @Column(nullable = false)
    val role: RoleEnum = RoleEnum.ROLE_USER,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null
)
