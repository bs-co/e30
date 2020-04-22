package com.bs.activity.contract.document

import com.bs.activity.contract.model.ContractStatus
import java.time.LocalDateTime
import java.util.*

data class Contract(val id:UUID ,val creationDateTime: LocalDateTime) {
     var startDateTime: LocalDateTime ? =null
     var endDateTime:LocalDateTime ? = null
     var statut = ContractStatus.None

     fun openContract(){
         statut =ContractStatus.Opened
     }

 }