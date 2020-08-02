package com.bs.activity

import org.axonframework.config.Configurer
import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.eventhandling.EventMessage
import org.axonframework.extensions.kafka.KafkaProperties
import org.axonframework.extensions.kafka.configuration.KafkaMessageSourceConfigurer
import org.axonframework.extensions.kafka.eventhandling.KafkaMessageConverter
import org.axonframework.extensions.kafka.eventhandling.consumer.ConsumerFactory
import org.axonframework.extensions.kafka.eventhandling.consumer.Fetcher
import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.StreamableKafkaMessageSource
import org.axonframework.extensions.kafka.eventhandling.consumer.subscribable.SubscribableKafkaMessageSource
import org.axonframework.extensions.kafka.eventhandling.producer.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import java.util.function.Function
import java.util.function.Predicate
import javax.annotation.PostConstruct
import kotlin.reflect.KClass


fun main(args: Array<out String>) {
    //Application().startAndAwait()
    SpringApplication.run(Application::class.java, *args)
    //runApplication<Application>(*args)
}

@SpringBootApplication
class Application {
//   private val httpHandler:HttpHandler
//    private val server:HttpServer
//
//    constructor(port: Int=8080){
//        val context = GenericApplicationContext().apply {
//            beans().initialize(this)
//            refresh()
//        }
//        httpHandler= WebHttpHandlerBuilder.applicationContext(context).build()
//        server = HttpServer.create().host("127.0.0.1").port(port)
//
//
//    }
//    fun startAndAwait() {
//        server.handle(ReactorHttpHandlerAdapter(httpHandler)).bindUntilJavaShutdown(Duration.ofSeconds(45), null)
//    }


}

@Configuration
class KafkaEventPublicationConfiguration {

    @Bean
    fun producerFactory(kafkaProperties: KafkaProperties): ProducerFactory<String, ByteArray>? {
        return DefaultProducerFactory.builder<String, ByteArray>()
            .configuration(kafkaProperties.buildProducerProperties())
            .producerCacheSize(10_000)
            .confirmationMode(kafkaProperties.publisher.confirmationMode)
            .build()
    }

    @Bean
    fun kafkaPublisher(producerFactory: ProducerFactory<String, ByteArray>): KafkaPublisher<String, ByteArray> {
        return KafkaPublisher.builder<String, ByteArray>()
            .producerFactory(producerFactory)
            .build();
    }

    @Bean
    fun kafkaEventPublisher(kafkaPublisher: KafkaPublisher<String, ByteArray>): KafkaEventPublisher<String, ByteArray> {
        return KafkaEventPublisher.builder<String, ByteArray>()
            .kafkaPublisher(kafkaPublisher)
            .build();
    }


    fun regeisterPublisherToEventProcessor(eventProcessingConfigurer: EventProcessingConfigurer,
                                           kafkaEventPublisher: KafkaEventPublisher<String, ByteArray>) {
        val processingGroup = KafkaEventPublisher.DEFAULT_PROCESSING_GROUP;
        eventProcessingConfigurer
            .registerEventHandler {
                kafkaEventPublisher
            }
            .assignHandlerTypesMatching(processingGroup,
             { it.isAssignableFrom(kafkaEventPublisher.javaClass) })
            .registerSubscribingEventProcessor(processingGroup)
    }
}

class BeanInitialisation: ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(context:GenericApplicationContext) {
        beans().initialize(context)
    }
}
/**
 * If the Consumer Processor Mode is set to Tracking, that's when we register a
 * [org.axonframework.eventhandling.TrackingEventProcessor] using the [StreamableKafkaMessageSource] which the auto
 * configuration creates.
 */
//@Configuration
//@ConditionalOnProperty(value = ["axon.kafka.consumer.event-processor-mode"], havingValue = "TRACKING")
//class TrackingConfiguration {
//
//    @Autowired
//    fun configureStreamableKafkaSource(configurer: EventProcessingConfigurer,
//                                       streamableKafkaMessageSource: StreamableKafkaMessageSource<String, ByteArray>) {
//        configurer.registerTrackingEventProcessor("kafka-group") { streamableKafkaMessageSource }
//    }
//}

///**
// * If the Consumer Processor Mode is set to Subscribing, that's when we register a
// * [org.axonframework.eventhandling.SubscribingEventProcessor] using the [SubscribableKafkaMessageSource] which this
// * Configuration class builds.
// */
//@Configuration
//@ConditionalOnProperty(value = ["axon.kafka.consumer.event-processor-mode"], havingValue = "SUBSCRIBING")
//class SubscribingConfiguration {
//
//    /**
//     * To start a [SubscribableKafkaMessageSource] at the right point in time, we should add those sources to a
//     * [KafkaMessageSourceConfigurer].
//     */
//    @Bean
//    fun kafkaMessageSourceConfigurer() = KafkaMessageSourceConfigurer()
//
//    /**
//     * The [KafkaMessageSourceConfigurer] should be added to Axon's [Configurer] to ensure it will be called upon start up.
//     */
//    @Autowired
//    fun registerKafkaMessageSourceConfigurer(configurer: Configurer,
//                                             kafkaMessageSourceConfigurer: KafkaMessageSourceConfigurer) {
//        configurer.registerModule(kafkaMessageSourceConfigurer)
//    }
//
//    /**
//     * The auto configuration currently does not create a [SubscribableKafkaMessageSource] bean because the user is
//     * inclined to provide the group-id in all scenarios. Doing so provides users the option to create several
//     * [org.axonframework.eventhandling.SubscribingEventProcessor] beans belonging to the same group, thus giving
//     * Kafka the opportunity to balance the load.
//     *
//     * Additionally, this subscribable source should be added to the [KafkaMessageSourceConfigurer] to ensure it will
//     * be started and stopped within the configuration lifecycle.
//     */
//    @Bean
//    fun subscribableKafkaMessageSource(
//            kafkaProperties: KafkaProperties,
//            consumerFactory: ConsumerFactory<String, ByteArray>,
//            fetcher: Fetcher<String, ByteArray, EventMessage<*>>,
//            messageConverter: KafkaMessageConverter<String, ByteArray>,
//            kafkaMessageSourceConfigurer: KafkaMessageSourceConfigurer
//    ): SubscribableKafkaMessageSource<String, ByteArray> {
//        val subscribableKafkaMessageSource = SubscribableKafkaMessageSource.builder<String, ByteArray>()
//            .topics(listOf(kafkaProperties.defaultTopic))
//            .groupId("kafka-group")
//            .consumerFactory(consumerFactory)
//            .fetcher(fetcher)
//            .messageConverter(messageConverter)
//            .build()
//        kafkaMessageSourceConfigurer.registerSubscribableSource { subscribableKafkaMessageSource }
//        return subscribableKafkaMessageSource
//    }
//
//    @Autowired
//    fun configureSubscribableKafkaSource(eventProcessingConfigurer: EventProcessingConfigurer,
//                                         subscribableKafkaMessageSource: SubscribableKafkaMessageSource<String, ByteArray>) {
//        eventProcessingConfigurer.registerSubscribingEventProcessor("kafka-group") { subscribableKafkaMessageSource }
//    }
//}



