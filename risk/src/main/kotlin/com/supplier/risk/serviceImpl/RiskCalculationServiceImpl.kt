package com.supplier.risk.serviceImpl

import com.supplier.risk.common.RiskLevel
import com.supplier.risk.dao.SupplierDto
import com.supplier.risk.model.RiskEvent
import com.supplier.risk.model.Supplier
import com.supplier.risk.repository.SupplierRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class RiskCalculationServiceImpl (private val supplierRepository: SupplierRepository) {

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
  fun updateSupplierRisk(supplierId: Long) {
        val supplier = supplierRepository.findById(supplierId).orElseThrow {
            EntityNotFoundException("Supplier not found with id $supplierId")
        }

        val newScore = calculateRiskScore(supplier.riskEvents.toList())
        supplier.riskScore = newScore
        supplier.riskLevel = determineRiskLevel(newScore)
        supplier.updatedAt = Instant.now()

        supplierRepository.save(supplier)

    }


    fun findAllSuppliers(): List<Supplier> {
        val suppliers = supplierRepository.findAll()
        return suppliers;
    }



}