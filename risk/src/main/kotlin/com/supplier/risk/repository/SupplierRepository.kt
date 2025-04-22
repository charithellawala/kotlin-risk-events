package com.supplier.risk.repository

import com.supplier.risk.common.RiskLevel
import com.supplier.risk.model.Supplier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SupplierRepository : JpaRepository<Supplier, Long> {

    @Query("SELECT s FROM Supplier s WHERE s.riskLevel = :riskLevel")
    fun findByRiskLevel(riskLevel: RiskLevel): List<Supplier>

    fun findByNameContainingIgnoreCase(name: String): List<Supplier>;

    @Query("SELECT s FROM Supplier s ORDER BY s.riskScore DESC LIMIT 10")
    fun findHighestRiskSuppliers(): List<Supplier>
}