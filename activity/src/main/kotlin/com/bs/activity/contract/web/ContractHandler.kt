package com.bs.activity.contract.web

import com.bs.activity.contract.web.data.*
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
                 fromPublisher(service.createContract(it),ContractResponse::class.java).build()
            }
    }
    fun openContract(request: ServerRequest): Mono<ServerResponse> {

        return request
            .bodyToMono(OpenContractRequest::class.java).flatMap {
                fromPublisher(service.openContract(it),ContractResponse::class.java).build()
            }
    }
    fun closeContract(request: ServerRequest): Mono<ServerResponse> {

        return request
            .bodyToMono(CloseContractRequest::class.java).flatMap {
                fromPublisher(service.closeContract(it),ContractResponse::class.java).build()
            }
    }

//    fun getContract(request: ServerRequest):Mono<ServerResponse>{
//        var id = request.pathVariable("id")
//        var res = service.getContract(UUID.fromString(id)).block()
//       // return ServerResponse.ok().body(fromObject(res).)
//    }

    fun getContract(request: ServerRequest): Mono<ServerResponse> {
        return request
            .bodyToMono(UUID::class.java).flatMap {
                fromPublisher(service.getContract(it), GetContractResponse::class.java).build()
            }
    }
}