package com.supplier.risk.service

import com.supplier.risk.common.RiskLevel
import com.supplier.risk.dao.RiskEventDto
import com.supplier.risk.dao.SupplierDto
import com.supplier.risk.model.RiskEvent
import com.supplier.risk.model.Supplier

interface RiskCalculationService {

    fun updateSupplierRisk(supplierId: Long)
    fun findAllSuppliers(): List<Supplier>
    fun getHighestRiskSuppliers(): List<SupplierDto>
    fun getSupplierEventById(supplierId: Long): List<RiskEventDto>
    fun getTopRiskSuppliers(): List<SupplierDto>

}