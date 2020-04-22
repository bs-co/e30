package com.bs.activity.contract.repository

import com.bs.activity.contract.document.Contract
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.util.*

@Repository
interface ContractRepository : ReactiveMongoRepository<Contract,UUID>{
    fun findContractsByStartDateTime(startDateTime: LocalDateTime):Flux<List<Contract>>
    fun findContractsByEndDateTime(endDateTime: LocalDateTime):Flux<List<Contract>>

}
