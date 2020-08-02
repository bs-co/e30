package com.bs.activity.contract.aggregate

import com.bs.activity.contract.ContractDetails
import com.bs.activity.contract.ContractId
import com.bs.activity.contract.message.CreateContractCommand
import com.bs.activity.contract.message.OpenContractCommand
import com.bs.activity.contract.event.ContractCreated
import com.bs.activity.contract.event.ContractStateChanged
import com.bs.activity.contract.message.CloseContactCommand
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
//State and methods to alter that state
class ContractAggregate  {


    @AggregateIdentifier
    lateinit var contractId: ContractId
    lateinit var contractDetails: ContractDetails
    lateinit var contractStatus: ContractStatus

    @CommandHandler
    constructor(createContractCommand: CreateContractCommand){
        AggregateLifecycle.apply(ContractCreated(createContractCommand.contractId, LocalDateTime.now(),createContractCommand.contractDetails))
    }

    @CommandHandler
    //decision making
    //insure if the aggregate is in correct state
    fun handle(openContractCommand:OpenContractCommand){
        //publish event
        AggregateLifecycle.apply(ContractStateChanged(openContractCommand.contractId,ContractStatus.Opened))
    }
    @CommandHandler
    fun handle(closeContractCommand:CloseContactCommand){
        //publish event
        if(contractStatus != ContractStatus.Opened)
            throw IllegalArgumentException()
        AggregateLifecycle.apply(ContractStateChanged(closeContractCommand.contractId,ContractStatus.Closed))
    }



    // state changes happens here
    //Aggregrate only react to their own events this is useful to recreate the state of the aggregate
    //TODO modifier can be private as longer as the jvm allow axon to modifiy the accessibility of a method
    //SAGA EVENT Handle
    @EventSourcingHandler
    fun on(contractCreated: ContractCreated){
        contractId = contractCreated.contractId
        contractDetails = contractCreated.contractDetails
        // it is possible to apply new events (reactions) here however axon will ignore te apply of
    }

    @EventSourcingHandler
    fun on(contractStatusChanged: ContractStateChanged){
        contractStatus = contractStatusChanged.status
    }


}