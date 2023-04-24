package core.api.config


//@EnableRabbit
//@Configuration
class AmqpEventPublicationConfiguration(
        //@Value("rabbitmq.exchange:customer-exchange")
        val exchangeName: String = "customer-exchange",
        //@Value("rabbitmq.queue:customer-queue")
        val queueName: String = "customer-queue",
        //@Value("rabbitmq.routingkey:customer#")
        val routingKey: String = "customer#",
) {

    /*   @Bean
       fun eventStorageEngine(): EventStorageEngine {
           return InMemoryEventStorageEngine()
       }
   */
    /* @Bean
     fun queue() = Queue(queueName)

     @Bean
     fun exchange() = DirectExchange(exchangeName)

     @Bean
     fun binding() = BindingBuilder.bind(queue()).to(exchange()).with(routingKey)

     @Bean
     fun jsonMessageConverter(): MessageConverter = Jackson2JsonMessageConverter()*/

}