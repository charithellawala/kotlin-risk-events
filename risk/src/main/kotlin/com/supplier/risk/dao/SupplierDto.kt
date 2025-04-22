package com.supplier.risk.dao

import com.supplier.risk.common.RiskLevel
import com.supplier.risk.model.Supplier
import java.time.Instant

data class SupplierDto(val id: Long,
                       val name: String,
                       val country: String,
                       val industry: String,
                       val riskScore: Double,
                       val riskLevel: RiskLevel,
                       val createdAt: Instant,
                       val updatedAt: Instant)

