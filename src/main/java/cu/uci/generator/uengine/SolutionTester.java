package cu.uci.generator.uengine;

import cu.uci.coj.model.SubmissionJudge;
import cu.uci.uengine.Verdicts;
import cu.uci.uengine.model.dto.SubmissionDTO;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class SolutionTester implements MessageListener {

    @Resource
    private JsonMessageConverter jsonMessageConverter;
    
    
    static Log log = LogFactory.getLog(SolutionTester.class.getName());
    
    private int countAccepted;
    
    public static final void main(String[] args) throws IOException {

        ApplicationContext context
                = new AnnotationConfigApplicationContext(RabbitMQConfig.class);
        AmqpTemplate template = context.getBean(AmqpTemplate.class);

        
        SubmissionJudge submissionv = new SubmissionJudge();
        SubmissionDTO submission  = new SubmissionDTO();
        
        
       // "timeLimit":0,"caseTimeLimit":0,"memoryLimit":0,"lang":"C","source":null,"specialJudge":false,"timeUsed":0,"cpuTimeUsed":0,"errMsg":null,"memoryUsed":0,"acTestCases":0,"totalTestCases":0,"firstWaCase":0,"minTimeUsed":0,"maxTimeUsed":0,"avgTimeUsed":0,"accepted":false,"virtual":false,"rejudge":0,"frozen":false,"font":"924","statusClass":null,"statusName":null,"username":"KeGiRoZa","authorize":false,"ontest":false,"email":null,"code":null,"title":null,"userNick":null,"problemTitle":null,"enabled":true,"rank":0,"see_solution":false,"groupd":null,"color":null,"compileInfo":null,"ddate":null,"statusId":null,"error":null,"testcase":0,"rejudge_status":0,"lid":0,"key":null,"letter":null,"y":0,"yp":0,"yu":0,"sdate":null,"memoryMB":"0 bytes","fontMB":"924 bytes"}

        submission.setId(2);
        submission.setProblemId(1000);
        submission.setMemoryLimit(65536000L);
        submission.setTimeLimit(20000L);
        submission.setCaseTimeLimit(20000L);
        submission.setEvaluationType("PrototypeOutput");
        submission.setLanguage("C");
        submission.setSourceCode("#include <stdio.h>\nint main(){\nint a,b;\nscanf(\"%d %d\",&a,&b);\nprintf(\"%d\",a+b);\nreturn 0;\n}");
        
        HashMap<Object, Object> metadata = new HashMap<>(); //Metadata can be used to sent extra values to UEngine that it should return as equal for some reason.
        
        metadata.put("Cid", 0);
        metadata.put("Lang", "C");
        metadata.put("Uid", 2);
        
        
        for (int i = 0; i < 10; i++) {
            submission.setId(submission.getId() + 1);
            MessagePostProcessor post;
            
            template.convertAndSend(submission);
            
            System.out.println(String.format("%s sent", i + 1));
        }

        
    }

    

    @Override
    public void onMessage(Message message) {
        SubmissionJudge result = (SubmissionJudge) jsonMessageConverter
                .fromMessage(message);
        if (result != null && result.getVerdict() == Verdicts.AC){
            log.info(String.format("Accepted %s",countAccepted++));
        }
        
    }

}
