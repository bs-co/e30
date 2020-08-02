package com.bs.activity.contract.web.service

import com.bs.activity.contract.ContractDetails
import com.bs.activity.contract.ContractId
import com.bs.activity.contract.message.CreateContractCommand
import com.bs.activity.contract.message.OpenContractCommand
import com.bs.activity.contract.document.Contract
import com.bs.activity.contract.message.CloseContactCommand
import com.bs.activity.contract.query.FindAllContractQuery
import com.bs.activity.contract.query.FindContractQuery
import com.bs.activity.contract.web.data.*
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class ContractService(private val commandGateway: CommandGateway, private val queryGateway: QueryGateway) {

    fun createContract(createContractRequest: CreateContractRequest):Mono<ContractResponse> {
        val contractDetails = ContractDetails(createContractRequest.startDate, createContractRequest.endDate)
        val uuid =UUID.randomUUID();
        val contractId = ContractId(uuid)
        commandGateway.send<Void>(CreateContractCommand(contractId,contractDetails))
        return Mono.just(ContractResponse(uuid))
    }

    fun openContract(openContractRequest: OpenContractRequest):Mono<ContractResponse>  {
        val contractId = ContractId(openContractRequest.id)
        commandGateway.send<Void>(OpenContractCommand(contractId))
        return Mono.just(ContractResponse(contractId.id))
    }
    fun closeContract(closeContractRequest: CloseContractRequest):Mono<ContractResponse> {
        val contractId = ContractId(closeContractRequest.id)
        commandGateway.send<Void>(CloseContactCommand(contractId))
        return Mono.just(ContractResponse(contractId.id))

    }

    fun getContract(contractId: UUID):Mono<GetContractResponse>  {

        val res =queryGateway.query(FindContractQuery(contractId),Contract::class.java)
        return Mono.fromFuture(res).map { c -> GetContractResponse(c.id,c.startDateTime,c.endDateTime,c.statut.name) }
    }
}