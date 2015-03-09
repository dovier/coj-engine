package cu.uci.generator.uengine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import cu.uci.coj.model.SubmissionJudge;

@Component
public class PriorityTest implements MessageListener {

    @Resource
    private JsonMessageConverter jsonMessageConverter;

    static Log log = LogFactory.getLog(PriorityTest.class.getName());

    public static final void main(String[] args) throws IOException, InterruptedException {

        final Integer[] priorities = {1, 2, 5, 6, 3, 8, 4, 7, 9};

        ApplicationContext context
                = new AnnotationConfigApplicationContext(PriorityTestConfig.class);

        AmqpTemplate template = context.getBean(AmqpTemplate.class);

        AmqpAdmin admin = context.getBean(AmqpAdmin.class);

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-priority", 10);

        SubmissionJudge submission = new SubmissionJudge();
        submission.setStatus(null);
        submission.setCid(0);
        submission.setPid(1000);
        submission.setUid(2);

        // admin.declareQueue(new Queue("my-priority-queue", true, false, false, arguments));
        for (int i = 0; i < priorities.length; i++) {
            submission.setSid(i);

            final int index = i;

            if (priorities[i] < 5) {
                template.convertAndSend("submit.queue.phigh", submission, new MessagePostProcessor() {

                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        message.getMessageProperties().setPriority(priorities[index]);
                        return message;
                    }
                });
            } else {
                template.convertAndSend("submit.queue.plow", submission, new MessagePostProcessor() {

                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        message.getMessageProperties().setPriority(priorities[index]);
                        return message;
                    }
                });
            }

        }

//         final CountDownLatch latch = new CountDownLatch(3);
//        ch.basicConsume(QUEUE, true, new DefaultConsumer(ch) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
//                System.out.println("Received " + new String(body));
//                latch.countDown();
//            }
//        });
//
//        latch.await();
//        conn.close();
    }

    @Override
    public void onMessage(Message message) {
        SubmissionJudge result = (SubmissionJudge) jsonMessageConverter
                .fromMessage(message);
        if (result != null) {
            System.out.println(String.format("Accepted %s", result.getSid()));
        }

    }

}
