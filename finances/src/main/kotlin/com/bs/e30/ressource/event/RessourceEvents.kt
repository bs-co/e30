package com.bs.e30.ressource.event

import com.bs.e30.ressource.ContractId
import java.util.*

data class RessourceRequested(val contractId:UUID)
data class ContractCreated(val contractId:ContractId)
data class RessourceGranted(val contractId:UUID)
data class RessourceAllocated(val contractId:UUID)