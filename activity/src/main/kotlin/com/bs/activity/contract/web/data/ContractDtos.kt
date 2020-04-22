package com.bs.activity.contract.web.data

import java.time.LocalDateTime
import java.util.*

class CreateContractData(val startDate:LocalDateTime?,val endDate:LocalDateTime?)
class GetContractData(val id:UUID,val startDate: LocalDateTime?,endDate: LocalDateTime?)