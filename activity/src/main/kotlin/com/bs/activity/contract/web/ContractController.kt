package com.bs.activity.contract.web

import com.bs.activity.contract.web.service.ContractService

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