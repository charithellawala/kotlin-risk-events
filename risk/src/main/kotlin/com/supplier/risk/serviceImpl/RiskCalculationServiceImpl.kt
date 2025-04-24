package com.supplier.risk.serviceImpl

import com.supplier.risk.common.RiskLevel
import com.supplier.risk.common.toDto
import com.supplier.risk.common.toDtoList
import com.supplier.risk.common.toEventDto
import com.supplier.risk.dao.RiskEventDto
import com.supplier.risk.dao.SupplierDto
import com.supplier.risk.model.RiskEvent
import com.supplier.risk.model.Supplier
import com.supplier.risk.repository.RiskEventRepository
import com.supplier.risk.repository.SupplierRepository
import com.supplier.risk.service.RiskCalculationService
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class RiskCalculationServiceImpl(
    private val supplierRepository: SupplierRepository,
    private val riskEventRepository: RiskEventRepository
) : RiskCalculationService {

    fun calculateRiskScore(events: List<RiskEvent>): Double {
        return events.sumOf { it.severity } * when (events.size) {
            in 0..2 -> 1.0
            in 3..5 -> 1.2
            else -> 1.5
        }
    }

    fun determineRiskLevel(score: Double): RiskLevel {
        return when {
            score >= 80 -> RiskLevel.CRITICAL
            score >= 50 -> RiskLevel.HIGH
            score >= 20 -> RiskLevel.MEDIUM
            else -> RiskLevel.LOW
        }
    }


    @Transactional
    override fun updateSupplierRisk(supplierId: Long) {
        val supplier = supplierRepository.findById(supplierId).orElseThrow {
            EntityNotFoundException("Supplier not found with id $supplierId")
        }

        val newScore = calculateRiskScore(supplier.riskEvents.toList())
        supplier.riskScore = newScore
        supplier.riskLevel = determineRiskLevel(newScore)
        supplier.updatedAt = Instant.now()

        supplierRepository.save(supplier)

    }


    override fun findAllSuppliers(): List<Supplier> {
        val suppliers = supplierRepository.findAll()
        return suppliers;
    }

    override fun getHighestRiskSuppliers(): List<SupplierDto> {
        val supplierList = supplierRepository.findByRiskLevel(RiskLevel.HIGH)
            .plus(supplierRepository.findByRiskLevel(RiskLevel.CRITICAL))
        return supplierList.toDtoList();
    }

    override fun getSupplierEventById(supplierId: Long): List<RiskEventDto> {
        val events = riskEventRepository.findBySupplierId(supplierId).map { it.toEventDto() }
        return events;
    }

    override fun getTopRiskSuppliers(): List<SupplierDto> {
        val suppliers = supplierRepository.findHighestRiskSuppliers().map { it.toDto() }
        return suppliers;
    }

    fun findRecentEvents(hours: Int): List<RiskEventDto> {
        val since = Instant.now().minus(hours.toLong(), ChronoUnit.HOURS)
        val events = riskEventRepository.findRecentEvents(since)
        return events.map { it.toEventDto() }
    }


}