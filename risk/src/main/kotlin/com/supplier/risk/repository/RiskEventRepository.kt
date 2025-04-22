package com.supplier.risk.repository

import com.supplier.risk.model.RiskEvent
import com.supplier.risk.model.Supplier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface RiskEventRepository: JpaRepository<RiskEvent, Long> {
    fun findBySupplierId(supplierId: Long): List<RiskEvent>

    @Query("SELECT r FROM RiskEvent r WHERE r.detectedAt >= :since ORDER BY r.detectedAt DESC")
    fun findRecentEvents(since: Instant): List<RiskEvent>
}