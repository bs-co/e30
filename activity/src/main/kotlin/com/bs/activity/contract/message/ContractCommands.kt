package com.bs.activity.contract.message

import com.bs.activity.contract.ContractDetails
import com.bs.activity.contract.ContractId
import org.axonframework.modelling.command.TargetAggregateIdentifier
//@TargetAggregateIdentifier used for rooting other rooting startegy can be used
data class  CreateContractCommand(@TargetAggregateIdentifier val contractId: ContractId,val contractDetails: ContractDetails)
data class OpenContractCommand (@TargetAggregateIdentifier val contractId: ContractId)
data class CloseContactCommand(@TargetAggregateIdentifier val contractId: ContractId)
