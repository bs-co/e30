package com.bs.activity.contract.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class ContractRoutes(private val contractHandler: ContractHandler) {

    @Bean
    fun router() = router {

            (accept(MediaType.APPLICATION_JSON) and "/contracts").nest {
                POST("/create", contractHandler::createContract)
            }

    }

}