package com.supplier.risk.model

import com.supplier.risk.common.RiskLevel
import java.time.Instant

data class RiskAlert(val supplierId: Long,
                     val supplierName: String,
                     val eventDescription: String,
                     val riskScore: Double,
                     val riskLevel: RiskLevel,
                     val timestamp: Instant = Instant.now())
