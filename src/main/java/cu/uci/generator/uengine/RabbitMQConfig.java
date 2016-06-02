package cu.uci.generator.uengine;

import cu.uci.generator.uengine.SubmissionGenerator;
import cu.uci.uengine.model.dto.VerdictDTO;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/uengine.properties")
@ComponentScan(value = {"cu.uci.generator.uengine"})
public class RabbitMQConfig {

    @Autowired
    Environment env;
    
    @Resource
    SubmissionGenerator submissionGenerator;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory bean = new CachingConnectionFactory(
                env.getProperty("rabbit.server"),
                Integer.valueOf(env.getProperty("rabbit.port")));
        bean.setUsername(env.getProperty("rabbit.user"));
        bean.setPassword(env.getProperty("rabbit.password"));
        return bean;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public DefaultClassMapper typeMapper() {
        DefaultClassMapper typeMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("UEngineVerdict", VerdictDTO.class);
        typeMapper.setIdClassMapping(idClassMapping);
        return typeMapper;
    }

    @Bean
    public JsonMessageConverter jsonMessageConverter() {
        JsonMessageConverter bean = new JsonMessageConverter();
        bean.setClassMapper(typeMapper());
        return bean;
    }
    
//
//    /**
//     * @return The reply listener container - the rabbit template is the
//     * listener.
//     */
//    @Bean
//    public SimpleMessageListenerContainer replyListenerContainer() throws IOException {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory());
//        container.setQueues(responses());
//        container.setMessageListener(rabbitTemplate());
//        return container;
//    }

    @Bean
    public Queue responses() throws IOException {

        String queueName = env.getProperty("rabbit.queue.response");
        Queue queue = new Queue(queueName);
        amqpAdmin().declareQueue(queue);

        return queue;
    }

    
    @Bean
    public AmqpTemplate submitTemplate() {
        RabbitTemplate bean = new RabbitTemplate(connectionFactory());
        bean.setMessageConverter(jsonMessageConverter());
		// para enviar, el binding del exchange se configura en el rabbitmq
        // server a la cola correspondiente (que aqui no interesa)
        bean.setExchange(env.getProperty("rabbit.exchange.submit"));
        return bean;
    }
    

    
    @Bean
    public SimpleMessageListenerContainer responseListener()
            throws IOException {
        
        SimpleMessageListenerContainer bean = new SimpleMessageListenerContainer(
                connectionFactory());
        
        bean.setQueueNames(env.getProperty("rabbit.queue.response"));
        bean.setMessageListener(submissionGenerator);
        
        return bean;
    }
    
}
