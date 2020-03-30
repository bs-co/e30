package com.bs.activity.contract

import java.time.LocalDateTime
import java.util.*

data class ContractDetails (val start:LocalDateTime,val end:LocalDateTime)
data class ContractId(val id:UUID)