package com.example.pesowiseserver.data.entity

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "accounts", uniqueConstraints = [
    UniqueConstraint(columnNames = ["user_name"]),
    UniqueConstraint(columnNames = ["email"])
])
data class Accounts(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,  // Changed type to Long for PostgreSQL auto-increment behavior

    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val lastName: String,

    @Column(name = "user_name", nullable = false, unique = true)
    val userName: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = true)
    var password: String?,

    @Column(name = "is_verified", nullable = false)
    var isVerified: Boolean = false,

    @Column(nullable = false)
    val isAdmin: Boolean = false
) {
    // Add a custom toString method to avoid potential issues with lazy loading
    override fun toString(): String {
        return "Accounts(id=$id, firstName='$firstName', lastName='$lastName', userName='$userName', email='$email', isVerified=$isVerified, isAdmin=$isAdmin)"
    }
}
