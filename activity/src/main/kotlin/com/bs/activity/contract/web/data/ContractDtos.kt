package com.bs.activity.contract.web.data

import java.time.LocalDateTime
import java.util.*

data class CreateContractRequest(val startDate:LocalDateTime?, val endDate:LocalDateTime?)
data class ContractResponse(val id:UUID)
data class OpenContractRequest(val id: UUID)
data class CloseContractRequest(val id: UUID)

class GetContractResponse(val id:UUID, val startDate: LocalDateTime?, endDate: LocalDateTime?,val status:String)