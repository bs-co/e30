package com.bs.activity.contract.aggregate

import com.bs.activity.contract.ContractDetails
import com.bs.activity.contract.ContractId
import com.bs.activity.contract.message.CreateContractCommand
import com.bs.activity.contract.message.OpenContractCommand
import com.bs.activity.contract.event.ContractCreated
import com.bs.activity.contract.event.ContractOpened
import com.bs.activity.contract.model.ContractStatus
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.time.LocalDateTime
import java.util.*

@Aggregate
@Suppress
class ContractAggregate  {

    @AggregateIdentifier
    var contractId: ContractId
    lateinit var contractDetails: ContractDetails
    lateinit var contractStatus: ContractStatus

    @CommandHandler
    constructor(createContractCommand: CreateContractCommand){
        contractId= ContractId(UUID.randomUUID())
        AggregateLifecycle.apply(ContractCreated(contractId, LocalDateTime.now(),createContractCommand.contractDetails))
    }

    @CommandHandler
    //decision making
    //insure if the aggregate is in correct state
    fun handle(openContractCommand:OpenContractCommand){
        //publish event
        AggregateLifecycle.apply(ContractOpened(openContractCommand.contractId))
    }



    // state changes accur here
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