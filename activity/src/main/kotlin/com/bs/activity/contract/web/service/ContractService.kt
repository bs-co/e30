package com.bs.activity.contract.web.service

import com.bs.activity.contract.ContractDetails
import com.bs.activity.contract.ContractId
import com.bs.activity.contract.message.CreateContractCommand
import com.bs.activity.contract.message.OpenContractCommand
import com.bs.activity.contract.document.Contract
import com.bs.activity.contract.web.data.CreateContractData
import com.bs.activity.contract.web.data.GetContractData
import com.bs.activity.contract.query.FindContractQuery
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class ContractService(private val commandGateway: CommandGateway, private val queryGateway: QueryGateway) {

    fun createContract(createContractData: CreateContractData) {
        val contractDetails = ContractDetails(createContractData.startDate, createContractData.endDate)
        commandGateway.send<Void>(CreateContractCommand(contractDetails))
    }

    fun openContract(id: String) {
        val contractId = ContractId(UUID.fromString(id))
        commandGateway.send<Void>(OpenContractCommand(contractId))
    }

    fun getContract(contractId: UUID):Mono<GetContractData>  {

        val res =queryGateway.query(FindContractQuery(contractId),Contract::class.java)
        return Mono.fromFuture(res).map { c -> GetContractData(c.id,c.startDateTime,c.endDateTime) }
    }
}