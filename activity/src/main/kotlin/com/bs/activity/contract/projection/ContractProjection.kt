package com.bs.activity.contract.projection

import com.bs.activity.contract.document.Contract
import com.bs.activity.contract.event.ContractCreated
import com.bs.activity.contract.event.ContractOpened
import com.bs.activity.contract.query.FindAllContractQuery
import com.bs.activity.contract.query.FindContractQuery
import com.bs.activity.contract.repository.ContractRepository
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ContractProjection(val repository: ContractRepository) {


    @EventHandler
    fun on(contractCreated: ContractCreated) {
        val contract = Contract(contractCreated.contractId.id, contractCreated.creationDateTime)
        repository.save(contract)
    }

    @EventHandler
    fun on(contractOpened: ContractOpened) {
        val id = contractOpened.contractId.id
        repository.findById(id).doOnSuccess(Contract::openContract).flatMap( fun (x:Contract) :Mono<Contract>{return repository.save(x)})
    }

    @QueryHandler
    fun handle(findContractQuery: FindContractQuery):Mono<Contract> = repository.findById(findContractQuery.contractId)

    @QueryHandler
    fun handle(findAllContractQuery: FindAllContractQuery): Flux<Contract> = repository.findAll()
}