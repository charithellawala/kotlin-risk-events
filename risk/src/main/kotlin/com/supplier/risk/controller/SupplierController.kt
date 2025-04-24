package com.supplier.risk.controller

import com.supplier.risk.common.toDtoList
import com.supplier.risk.dao.RiskEventDto
import com.supplier.risk.dao.SupplierDto
import com.supplier.risk.serviceImpl.RiskCalculationServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/api/suppliers")
class SupplierController(
    private val riskCalculationService: RiskCalculationServiceImpl,
) {

    @GetMapping
    fun getSuppliers(): ResponseEntity<List<SupplierDto>> {
        val supplierList = riskCalculationService.findAllSuppliers().toDtoList()
        return ResponseEntity(supplierList, HttpStatus.CREATED);
    }

    @GetMapping("/high-risk")
    fun getHighRiskSuppliers(): ResponseEntity<List<SupplierDto>> {
        val highestSupplierList = riskCalculationService.getHighestRiskSuppliers()

        return ResponseEntity.ok(highestSupplierList)
    }

    @GetMapping("/{id}/events")
    fun getSupplierEvents(@PathVariable id: Long): ResponseEntity<List<RiskEventDto>> {
        val events = riskCalculationService.getSupplierEventById(id)
        return ResponseEntity.ok(events)
    }

    @GetMapping("/top-risks")
    fun getTopRiskSuppliers(): ResponseEntity<List<SupplierDto>> {
        val suppliers = riskCalculationService.getTopRiskSuppliers()
        return ResponseEntity.ok(suppliers)
    }


}