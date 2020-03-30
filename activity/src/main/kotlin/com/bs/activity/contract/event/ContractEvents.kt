package com.bs.activity.contract.event

import com.bs.activity.contract.ContractDetails
import com.bs.activity.contract.ContractId

data class ContractCreated(val contractId: ContractId, val contractDetails: ContractDetails)
data class ContractOpened(val contractId: ContractId)