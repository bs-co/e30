package com.bs.activity

import com.bs.activity.contract.web.ContractHandler
import com.bs.activity.contract.web.ContractRoutes
import org.springframework.context.support.beans
import org.springframework.http.server.reactive.HttpHandler
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.RouterFunctions

fun  beans() = beans {
    bean<ContractHandler>()
    bean<ContractRoutes>()
    bean("webHandler") {
        RouterFunctions.toHttpHandler(ref<ContractRoutes>().router())

    }

}
