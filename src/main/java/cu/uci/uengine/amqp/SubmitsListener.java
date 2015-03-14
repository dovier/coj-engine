/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.amqp;

import com.rabbitmq.client.Channel;
import cu.uci.coj.model.SubmissionJudge;
import cu.uci.uengine.Engine;
import cu.uci.uengine.Verdicts;
import cu.uci.uengine.adapters.SubmissionToSubmissionJudgeAdapter;
import cu.uci.uengine.adapters.SubmissionToVerdictDTOAdapter;
import cu.uci.uengine.config.Config;
import cu.uci.uengine.creators.SubmissionFactory;
import cu.uci.uengine.model.Submission;
import cu.uci.uengine.model.dto.VerdictDTO;
import java.io.IOException;
import java.util.Arrays;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author lan
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SubmitsListener implements ChannelAwareMessageListener {

    static Log log = LogFactory.getLog(SubmitsListener.class.getName());
    public static boolean isDebuguing;

    @Resource
    private Engine engine;

    @Resource
    private RabbitTemplate submitTemplate;

    @Resource
    private JsonMessageConverter jsonMessageConverter;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        SubmissionJudge submissionJudge = (SubmissionJudge) jsonMessageConverter.fromMessage(message);

        Submission submit = SubmissionFactory.create("Submission", submissionJudge);
        String lastStatus = submissionJudge.getStatus();

        log.info(String.format("Working on submission: %s", submit.toString()));

        Submission result = engine.call(submit);

        log.info(String.format("Sending sid %s: %s. %s", result.getId(), result.getVerdict(), result.getErrorMessage()));

        SubmissionJudge verdict = new SubmissionToSubmissionJudgeAdapter(result);

        //submitTemplate.convertAndSend(verdict);
        submitTemplate.convertAndSend(verdict, new MessagePostProcessor() {

            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("__TypeId__", "cu.uci.coj.model.SubmissionJudge");
                return message;
            }
        });

        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

//        if (lastStatus != null && !lastStatus.equals("Judging") && (!verdict.getStatus().equals(lastStatus) && !(verdict.getStatus().equals("Case Time Limit Exceeded") && lastStatus.equals("Time Limit Exceeded")))) {
//            submitTemplate.convertAndSend(verdict);
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//        } else {
//            //TODO: @LAN -- Uncomment ACK before deplyment
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        }
    }

    public static final void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext(Config.class);

        //TODO: @Lan refactorizar esto, sobre todo quitar la variable isDebuguin estática y hacer un mecanismo más práctico.
        if (args.length > 0) {
            
            isDebuguing = Arrays.asList(args).contains("debug");
            if (isDebuguing)
                log.info("DEBUGEANDO!!");
        }
    }

}
