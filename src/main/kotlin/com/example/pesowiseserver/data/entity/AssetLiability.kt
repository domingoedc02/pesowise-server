package com.example.pesowiseserver.data.entity

import com.example.pesowiseserver.util.enum.AssetLiabilityEnum
import com.example.pesowiseserver.util.enum.PropertyTypeEnum
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.Date

@Entity
@Table(name = "asset_liability")
data class AssetLiability (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = "",

    @Column(nullable = false)
    val userId: String = "",

    @Column(nullable = false)
    val type: AssetLiabilityEnum = AssetLiabilityEnum.ASSETS,

    @Column(nullable = false)
    val propertyTypeEnum: PropertyTypeEnum =  PropertyTypeEnum.PROPERTIES,

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = true)
    val startDate: Date? = null,

    @Column(nullable = true)
    val mortgageEndDate: Date? = null,

    @Column(nullable = false, precision = 19, scale = 4)
    val amount: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = true)
    val balance: BigDecimal? = null,

    @Column(nullable = true)
    val startPayMonth: String? = "",

    @Column(nullable = true)
    val startPayDay: String? = "",

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null
)