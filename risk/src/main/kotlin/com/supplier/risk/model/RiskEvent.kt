package com.supplier.risk.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
//import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "risk_events")
data class RiskEvent( @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
                      val id: Long? = null,

                      @ManyToOne(fetch = FetchType.LAZY)
                      @JoinColumn(name = "supplier_id", nullable = false)
                      val supplier: Supplier,

                      @Column(nullable = false)
                      val eventType: String,

                      @Column(nullable = false)
                      val severity: Double,

                      @Column(nullable = false)
                      val source: String,

                      @Column(nullable = false)
                      val description: String,

                      @Column(nullable = false)
                      val detectedAt: Instant = Instant.now(),

                      @Column(nullable = false)
                      val location: String)

