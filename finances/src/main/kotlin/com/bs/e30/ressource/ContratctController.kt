package com.bs.e30.ressource

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.reactivex.Single

@Controller("/contract")
class ContratctController {
    @Post()
    fun createContract(@Body contractDetails: Single<ContractDetails>): Single<HttpResponse<String>> {


        return contractDetails.map { c ->
            HttpResponse.created("OK")
        }
    }

}