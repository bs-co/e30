package com.bs.activity.contract.web

import com.bs.activity.contract.web.data.CreateContractData
import com.bs.activity.contract.web.service.ContractService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.time.LocalDateTime

//@RestController
//@RequestMapping("/contracts")
class ContractController(private val contractService: ContractService) {

 /*   @PostMapping("/create")
    fun createContract () : ResponseEntity<Object>{
        val data = CreateContractData(LocalDateTime.now(), LocalDateTime.now())
        contractService.createContract(data)
        return ok().build()
       //return ServerResponse.ok().build()

    }
*/
}