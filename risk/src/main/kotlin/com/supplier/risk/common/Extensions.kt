package com.supplier.risk.common

import com.supplier.risk.dao.RiskEventDto
import com.supplier.risk.dao.SupplierDto
import com.supplier.risk.model.RiskEvent
import com.supplier.risk.model.Supplier

fun Supplier.toDto() = SupplierDto(
    id = id!!,
    name = name,
    country = country,
    industry = industry,
    riskScore = riskScore,
    riskLevel = riskLevel,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun SupplierDto.toSupplierEntity() = Supplier(
    id = id,
    name = name,
    country = country,
    industry = industry,
    riskScore = riskScore,
    riskLevel = riskLevel,
    createdAt = createdAt,
    updatedAt = updatedAt
)


fun List<Supplier>.toDtoList() = this.map { it.toDto() }

fun RiskEvent.toDto() = RiskEventDto(
    id = id!!,
    supplierId = supplier.id!!,
    eventType = eventType,
    severity = severity,
    source = source,
    description = description,
    detectedAt = detectedAt,
    location = location
)


fun List<RiskEvent>.toDtoRiskEvenList() = this.map { it.toDto() }
