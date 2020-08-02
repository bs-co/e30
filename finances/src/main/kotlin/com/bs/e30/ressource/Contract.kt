package com.bs.e30.ressource

import java.time.LocalDateTime
import java.util.*

data class ContractId(val id:UUID)
data class ContractDetails (val start: LocalDateTime?, val end: LocalDateTime?)
class Contract(val contractId: ContractId)