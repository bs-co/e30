package com.bs.activity.contract.web.service

import com.bs.activity.contract.ContractDetails
import com.bs.activity.contract.ContractId
import com.bs.activity.contract.message.CreateContractCommand
import com.bs.activity.contract.message.OpenContractCommand
import com.bs.activity.contract.document.Contract
import com.bs.activity.contract.web.data.CreateContractRequest
import com.bs.activity.contract.web.data.GetContractResponse
import com.bs.activity.contract.query.FindContractQuery
import com.bs.activity.contract.web.data.CreateContractResponse
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class ContractService(private val commandGateway: CommandGateway, private val queryGateway: QueryGateway) {

    fun createContract(createContractRequest: CreateContractRequest):Mono<CreateContractResponse> {
        val contractDetails = ContractDetails(createContractRequest.startDate, createContractRequest.endDate)
        val uuid =UUID.randomUUID();
        val contractId = ContractId(uuid)
        commandGateway.send<Void>(CreateContractCommand(contractId,contractDetails))
        return Mono.just(CreateContractResponse(uuid))
    }

    fun openContract(id: String) {
        val contractId = ContractId(UUID.fromString(id))
        commandGateway.send<Void>(OpenContractCommand(contractId))
    }

    fun getContract(contractId: UUID):Mono<GetContractResponse>  {

        val res =queryGateway.query(FindContractQuery(contractId),Contract::class.java)
        return Mono.fromFuture(res).map { c -> GetContractResponse(c.id,c.startDateTime,c.endDateTime) }
    }

//    fun getAllContracts():Flux<Contract>{
//      val res = queryGateway.query(FindAllContractQuery(),Contract::class.java)
//        return Flux.fromI
//    }
}