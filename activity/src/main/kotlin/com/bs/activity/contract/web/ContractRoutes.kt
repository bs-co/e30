package com.bs.activity.contract.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class ContractRoutes(private val contractHandler: ContractHandler) {

    @Bean
    fun router() = router {

        "/contracts".nest {

            accept(MediaType.APPLICATION_JSON).nest {
                GET("/create", contractHandler::createContract)
            }
        }
    }

}