package com.supplier.risk.dao

import com.supplier.risk.model.RiskAlert
import com.supplier.risk.model.RiskEvent
import java.time.Instant

data class RiskEventDto(val id: Long?,
                        val supplierId: Long,
                        val eventType: String,
                        val severity: Double,
                        val source: String,
                        val description: String,
                        val detectedAt: Instant,
                        val location: String)


