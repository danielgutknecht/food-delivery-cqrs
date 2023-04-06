package application.configuration

import java.util.Optional
import org.axonframework.commandhandling.CommandBus
import org.axonframework.config.EventProcessingConfiguration
import org.axonframework.eventhandling.EventProcessor
import org.axonframework.eventhandling.TrackingEventProcessor
import org.axonframework.messaging.interceptors.BeanValidationInterceptor
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AxonConfiguration(private val eventProcessingConfiguration: EventProcessingConfiguration) {

    /* Register a command interceptor */
    @Autowired
    fun registerInterceptors(commandBus: CommandBus) {
        commandBus.registerDispatchInterceptor(BeanValidationInterceptor())
    }

    @Bean
    fun snapshotterFactoryBean() = SpringAggregateSnapshotterFactoryBean()

    fun resetTrackingEventProcessor(processingGroup: String) = eventProcessingConfiguration
        .eventProcessorByProcessingGroup(processingGroup, TrackingEventProcessor::class.java)
        .ifPresent {
            it.shutDown()
            it.resetTokens()
            it.start()
        }

    fun getTrackingEventProcessors(): List<EventProcessor> =
        eventProcessingConfiguration.eventProcessors().values.filterIsInstance(TrackingEventProcessor::class.java)

    fun getEventProcessor(processingGroup: String): Optional<EventProcessor> =
        eventProcessingConfiguration.eventProcessorByProcessingGroup(processingGroup)
}