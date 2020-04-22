package com.bs.activity.contract.web

import com.bs.activity.contract.web.data.CreateContractData
import com.bs.activity.contract.web.service.ContractService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@SuppressWarnings("UNUSED_PARAMETER")
class ContractHandler(private val service:ContractService) {
    fun createContract(request: ServerRequest): Mono<ServerResponse> {

        return request
            .bodyToMono(CreateContractData::class.java)
            .doOnSuccess { data ->  service.createContract(data) }
            .then(ServerResponse.ok().build())
    }
}