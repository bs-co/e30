package com.bs.activity.contract.message

import com.bs.activity.contract.ContractDetails
import com.bs.activity.contract.ContractId
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class  CreateContractCommand(@TargetAggregateIdentifier val contractId: ContractId,val contractDetails: ContractDetails)
data class OpenContractCommand (@TargetAggregateIdentifier val contractId: ContractId)
