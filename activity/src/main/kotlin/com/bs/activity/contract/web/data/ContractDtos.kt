package com.bs.activity.contract.web.data

import java.time.LocalDateTime
import java.util.*

class CreateContractRequest(val startDate:LocalDateTime?, val endDate:LocalDateTime?)
class CreateContractResponse(val id:UUID)

class GetContractResponse(val id:UUID, val startDate: LocalDateTime?, endDate: LocalDateTime?)