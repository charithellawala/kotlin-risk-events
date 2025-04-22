package com.supplier.risk.controller

import com.supplier.risk.common.RiskLevel
import com.supplier.risk.common.toDto
import com.supplier.risk.common.toDtoList
import com.supplier.risk.dao.RiskEventDto
import com.supplier.risk.dao.SupplierDto
import com.supplier.risk.model.Supplier
import com.supplier.risk.repository.RiskEventRepository
import com.supplier.risk.repository.SupplierRepository
import com.supplier.risk.serviceImpl.RiskCalculationServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/api/suppliers")
class SupplierController (
    private val riskCalculationService: RiskCalculationServiceImpl,
    private val supplierRepository: SupplierRepository,
    private val riskEventRepository: RiskEventRepository
){

    @GetMapping
    fun getSuppliers(): ResponseEntity<List<SupplierDto>> {
        val supplierList = riskCalculationService.findAllSuppliers().toDtoList()
        return ResponseEntity(supplierList, HttpStatus.CREATED);
    }

    @GetMapping("/high-risk")
    fun getHighRiskSuppliers(): ResponseEntity<List<SupplierDto>> {
        val suppliers = supplierRepository.findByRiskLevel(RiskLevel.HIGH)
            .plus(supplierRepository.findByRiskLevel(RiskLevel.CRITICAL))
        return ResponseEntity.ok(suppliers.map { it.toDto() })
    }

    @GetMapping("/{id}/events")
    fun getSupplierEvents(@PathVariable id: Long): ResponseEntity<List<RiskEventDto>> {
        val events = riskEventRepository.findBySupplierId(id)
        return ResponseEntity.ok(events.map { it.toDto() })
    }

    @GetMapping("/top-risks")
    fun getTopRiskSuppliers(): ResponseEntity<List<SupplierDto>> {
        val suppliers = supplierRepository.findHighestRiskSuppliers()
        return ResponseEntity.ok(suppliers.map { it.toDto() })
    }



}