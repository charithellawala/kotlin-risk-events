package com.supplier.risk.controller

import com.supplier.risk.common.toEventDto
import com.supplier.risk.dao.RiskEventDto
import com.supplier.risk.kafka.RiskEventProcessor
import com.supplier.risk.serviceImpl.RiskCalculationServiceImpl
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.temporal.ChronoUnit

@RestController
@RequestMapping("/api/risk-events")
class RiskEventController(
    private val riskEventProcessor: RiskEventProcessor,
    private val riskCalculationService: RiskCalculationServiceImpl
) {

    @PostMapping
    fun createRiskEvent(
        @RequestBody @Valid request: RiskEventDto
    ): ResponseEntity<RiskEventDto> {
        val event = riskEventProcessor.processNewEvent(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(event.toEventDto())
    }

    @GetMapping("/recent")
    fun getRecentEvents(
        @RequestParam(defaultValue = "24") hours: Int
    ): ResponseEntity<List<RiskEventDto>> {
        val since = Instant.now().minus(hours.toLong(), ChronoUnit.HOURS)
        val events = riskCalculationService.findRecentEvents(hours)
        return ResponseEntity.ok(events)
    }
}