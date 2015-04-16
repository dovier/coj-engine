package cu.uci.uengine.config;

import cu.uci.uengine.amqp.SubmitsListener;
import cu.uci.uengine.runner.Limits;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;
import javax.annotation.PostConstruct;

import javax.annotation.Resource;
import org.apache.commons.io.FileUtils;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@ComponentScan(value = {"cu.uci.uengine"})
public class Config implements AsyncConfigurer {

    @Resource
    private SubmitsListener submitsListener;

    @Bean
    public IOFileFilter inDataFilter() {
        return new SuffixFileFilter(".in");
    }

    @Bean
    public IOFileFilter outDataFilter() {
        return new SuffixFileFilter(".out");
    }

    @Bean
    @PostConstruct
    public File temporaryDirectory() throws IOException {
        // carpeta para el trabajo con los envios
        File temporaryDirectory = new File(System.getProperty("user.dir"), "tmp");
        FileUtils.forceMkdir(temporaryDirectory);
        return temporaryDirectory;
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(4);
        pool.setMaxPoolSize(4);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public JsonMessageConverter jsonMessageConverter() {
        JsonMessageConverter bean = new JsonMessageConverter();
        return bean;
    }

    @Bean
    public Properties properties() throws IOException {
        Properties properties = new Properties();
        properties.load(ClassLoader
                .getSystemResourceAsStream("uengine.properties"));
        return properties;
    }

    @Override
    public Executor getAsyncExecutor() {
        return taskExecutor();
    }

    @Bean
    public CachingConnectionFactory connectionFactory() throws IOException {
        Properties properties = properties();
        CachingConnectionFactory bean = new CachingConnectionFactory(
                properties.getProperty("rabbit.server"),
                Integer.valueOf(properties.getProperty("rabbit.port")));
        bean.setUsername(properties.getProperty("rabbit.user"));
        bean.setPassword(properties.getProperty("rabbit.password"));

        return bean;
    }

    @Bean
    public Limits engineLimits() throws IOException {
        final Properties properties = properties();

        return new Limits() {
            @Override
            public Long getMaxMemory() {
                return Long.valueOf(properties.getProperty("limit.max_memory"));
            }

            @Override
            public Long getMaxCaseExecutionTime() {
                return Long.valueOf(properties.getProperty("limit.max_case_execution_time"));
            }

            @Override
            public Long getMaxTotalExecutionTime() {
                return Long.valueOf(properties.getProperty("limit.max_total_execution_time"));
            }

            @Override
            public Long getMaxSourceCodeLenght() {
                return Long.valueOf(properties.getProperty("limit.max_source_code_lenght"));
            }

            @Override
            public Long getMaxOutput() {
                return Long.valueOf(properties.getProperty("limit.max_output"));
            }
        };
    }

    @Bean
    public RabbitAdmin rabbitAdmin() throws IOException {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() throws IOException {
        Properties properties = properties();
        RabbitTemplate bean = new RabbitTemplate(connectionFactory());
        bean.setMessageConverter(jsonMessageConverter());
        // para enviar, el binding del exchange se configura en el rabbitmq
        // server a la cola correspondiente (que aqui no interesa)
        bean.setExchange(properties.getProperty("rabbit.exchange.response"));
        return bean;
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainer()
            throws IOException {
        Properties properties = properties();

        SimpleMessageListenerContainer bean = new SimpleMessageListenerContainer(
                connectionFactory());

        //bean.setMessageListener(submitRunner);
        bean.setMessageListener(submitsListener);

        bean.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        int threadCount = Integer.parseInt(properties.getProperty("thread.count"));
        bean.setConcurrentConsumers(threadCount);

        bean.setQueueNames(properties.getProperty("rabbit.queue.submit"));
        return bean;
    }
}
