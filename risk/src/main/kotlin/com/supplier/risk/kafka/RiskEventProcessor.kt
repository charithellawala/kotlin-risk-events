package com.supplier.risk.kafka

import com.supplier.risk.common.RiskLevel
import com.supplier.risk.dao.RiskEventDto
import com.supplier.risk.model.RiskAlert
import com.supplier.risk.model.RiskEvent
import com.supplier.risk.repository.RiskEventRepository
import com.supplier.risk.repository.SupplierRepository
import com.supplier.risk.serviceImpl.RiskCalculationServiceImpl
import jakarta.persistence.EntityNotFoundException
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class RiskEventProcessor (
    private val supplierRepository: SupplierRepository,
    private val riskEventRepository: RiskEventRepository,
    private val riskCalculationService: RiskCalculationServiceImpl,
    private val kafkaTemplate: KafkaTemplate<String, RiskAlert>
) {

    fun processNewEvent(eventDto: RiskEventDto): RiskEvent {

        val supplier = supplierRepository.findById(eventDto.supplierId).orElseThrow {
            EntityNotFoundException("Supplier not found with id $eventDto.supplierId")
        }

        val event = RiskEvent(
            supplier = supplier,
            eventType = eventDto.eventType,
            severity = eventDto.severity,
            source = eventDto.source,
            description = eventDto.description,
            location = eventDto.location
        )
        val savedEvent = riskEventRepository.save(event)
        riskCalculationService.updateSupplierRisk(supplier.id!!)

        if(supplier.riskLevel >= RiskLevel.HIGH) {
            val alert = RiskAlert(
                supplierId = supplier.id!!,
                supplierName = supplier.name,
                eventDescription = event.description,
                riskScore = supplier.riskScore,
                riskLevel = supplier.riskLevel
            )
            kafkaTemplate.send("risk-alerts", alert)
        }
        return savedEvent
    }





}