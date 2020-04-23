package com.bs.activity.contract.web

import com.bs.activity.contract.web.data.CreateContractRequest
import com.bs.activity.contract.web.data.CreateContractResponse
import com.bs.activity.contract.web.data.GetContractResponse
import com.bs.activity.contract.web.service.ContractService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.EntityResponse.*
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.*

@Component
@Suppress
class ContractHandler(private val service:ContractService) {
    fun createContract(request: ServerRequest): Mono<ServerResponse> {

        return request
            .bodyToMono(CreateContractRequest::class.java).flatMap {
                 fromPublisher(service.createContract(it),CreateContractResponse::class.java).build()
            }
    }

   /* fun getContract(request: ServerRequest):Mono<ServerResponse>{
        var id = request.pathVariable("id")
        var res = service.getContract(UUID.fromString(id)).block()
       // return ServerResponse.ok().body(fromObject(res).)
    }*/
}