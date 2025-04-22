package com.supplier.risk.model

import com.supplier.risk.common.RiskLevel
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "suppliers")
data class Supplier(  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
                      val id: Long? = null,

                      @Column(nullable = false)
                      val name: String,

                      @Column(nullable = false)
                      val country: String,

                      @Column(nullable = false)
                      val industry: String,

                      @Column(nullable = false)
                      var riskScore: Double = 0.0,

                      @Enumerated(EnumType.STRING)
                      var riskLevel: RiskLevel = RiskLevel.LOW,

                      @OneToMany(mappedBy = "supplier", cascade = [CascadeType.ALL], orphanRemoval = true)
                      val riskEvents: MutableSet<RiskEvent> = mutableSetOf(),

                      @Column(nullable = false)
                      val createdAt: Instant = Instant.now(),

                      @Column(nullable = false)
                      var updatedAt: Instant = Instant.now())
