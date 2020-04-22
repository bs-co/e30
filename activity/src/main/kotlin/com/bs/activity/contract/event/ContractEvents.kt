package com.bs.activity.contract.event

import com.bs.activity.contract.ContractDetails
import com.bs.activity.contract.ContractId
import java.time.LocalDateTime

data class ContractCreated(val contractId: ContractId, val creationDateTime: LocalDateTime, val contractDetails: ContractDetails)
data class ContractOpened(val contractId: ContractId)