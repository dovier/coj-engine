package cu.uci.generator.uengine;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import cu.uci.coj.model.SubmissionJudge;
import cu.uci.uengine.Verdicts;
import cu.uci.uengine.model.dto.SubmissionDTO;
import cu.uci.uengine.model.dto.VerdictDTO;


@Component
public class SubmissionGenerator implements MessageListener {

    @Resource
    private JsonMessageConverter jsonMessageConverter;
    
    static Log log = LogFactory.getLog(SubmissionGenerator.class.getName());
    
    private int countAccepted;
    
    public static final void main(String[] args) throws IOException {

        ApplicationContext context
                = new AnnotationConfigApplicationContext(RabbitMQConfig.class);
        AmqpTemplate template = context.getBean(AmqpTemplate.class);

        Queue submitQueue = (Queue) context.getBean("submits");

        SubmissionJudge submission = new SubmissionJudge();
        submission.setStatus(null);
        submission.setCid(0);
        submission.setPid(1000);
        submission.setUid(2);
        submission.setDate(null);
        submission.setSid(728342);
        submission.setTimeLimit(10000);
        submission.setCaseTimeLimit(5000);
        submission.setMemoryLimit(13000000);
        submission.setLang("C");
        submission.setSource("#include <stdio.h>\nint main(){\nint a,b;\nscanf(\"%d %d\",&a,&b);\nprintf(\"%d\",a+b);\nreturn 0;\n}");
        submission.setSpecialJudge(false);
        submission.setTimeUsed(0);
        submission.setCpuTimeUsed(0);
        submission.setErrMsg(null);
        submission.setMemoryUsed(0);
        submission.setAcTestCases(0);
        submission.setTotalTestCases(0);
        submission.setFirstWaCase(0);
        submission.setMinTimeUsed(0);
        submission.setMaxTimeUsed(0);
        submission.setAvgTimeUsed(0);
        submission.setAccepted(false);

        for (int i = 0; i < 10; i++) {
            submission.setSid(submission.getSid() + 1);
            MessagePostProcessor post;
            
            template.convertAndSend(submitQueue.getName(), submission);
            
            System.out.println(String.format("%s sent", i + 1));
        }

        //context.getBean(CachingConnectionFactory.class).destroy();
    }

    

    @Override
    public void onMessage(Message message) {
        VerdictDTO result = (VerdictDTO) jsonMessageConverter
                .fromMessage(message);
        if (result != null && result.getVerdict()== Verdicts.AC){
            log.info(String.format("Accepted %s",countAccepted++));
        }
        
    }

}
