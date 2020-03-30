package com.bs.activity.contract.aggregate

import com.bs.activity.contract.ContractDetails
import com.bs.activity.contract.ContractId
import com.bs.activity.contract.ContractStatus
import com.bs.activity.contract.command.CreateContractCommand
import com.bs.activity.contract.command.OpenContractCommand
import com.bs.activity.contract.event.ContractCreated
import com.bs.activity.contract.event.ContractOpened
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class ContractAggregate {

    @AggregateIdentifier
    lateinit var contractId: ContractId
    lateinit var contractDetails: ContractDetails
    lateinit var contractStatus:ContractStatus



    @CommandHandler
    fun handle(openContractCommand:OpenContractCommand){
        AggregateLifecycle.apply(ContractOpened(openContractCommand.contractId))
    }

    @CommandHandler
    fun handle(createContractCommand: CreateContractCommand){
        AggregateLifecycle.apply(ContractCreated(createContractCommand.contractId,createContractCommand.contractDetails))
    }

    @EventSourcingHandler
    fun on(contractCreated: ContractCreated){
        contractId = contractCreated.contractId
        contractDetails = contractCreated.contractDetails
    }

    @EventSourcingHandler
    fun on(contractOpened: ContractOpened){
        contractStatus = ContractStatus.Opened
    }

}