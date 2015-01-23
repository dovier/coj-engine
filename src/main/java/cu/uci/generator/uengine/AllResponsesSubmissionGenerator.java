package cu.uci.generator.uengine;

import cu.uci.uengine.model.SubmissionJudge;
import cu.uci.uengine.runnable.SubmitRunner;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AllResponsesSubmissionGenerator {

    static Log log = LogFactory.getLog(SubmitRunner.class.getName());

    public static final void main(String[] args) throws IOException {

        ApplicationContext context
                = new AnnotationConfigApplicationContext(RabbitMQConfig.class);
        AmqpTemplate template = context.getBean(AmqpTemplate.class);

        Queue submitQueue = (Queue) context.getBean("submits");

        boolean[] test = {true,//AC
            true,//WA
            true,//Case TLE
            true,//TLE
            true,//MLE
            true,//RTE
    };

        Long[] memory = {new Long("2264682496"),//AC
            new Long("2264682496"),//WA
            new Long("2264682496"),//Case TLE
            new Long("2264682496"),//TLE
            new Long("22646"),//MLE
            new Long("2264682496"),//RTE
    };

        int[] caseTime = {5000,//AC
            5000,//WA
            1,//Case TLE
            5000,//TLE
            5000,//MLE
            5000,//RTE
    };

        int[] time = {10000,//AC
            10000,//WA
            10000,//Case TLE
            3,//TLE
            10000,//MLE
            10000,//RTE
    };

        String[] sources = {"#include <stdio.h>\nint main(){\nint a,b;\nscanf(\"%d %d\",&a,&b);\nprintf(\"%d\",a+b);\nreturn 0;\n}",
            "#include <stdio.h>\nint main(){\nint a,b;\nscanf(\"%d %d\",&a,&b);\nprintf(\"ESTA NO ES LA RESPUESTA\");\nreturn 0;\n}",
            "#include <stdio.h>\nint main(){\nint a,b;\nscanf(\"%d %d\",&a,&b);\nprintf(\"%d\",a+b);\nreturn 0;\n}",
            "#include <stdio.h>\nint main(){\nint a,b;\nscanf(\"%d %d\",&a,&b);\nprintf(\"%d\",a+b);\nreturn 0;\n}",
            "#include <stdio.h>\nint main(){\nint a,b;\nscanf(\"%d %d\",&a,&b);\nprintf(\"%d\",a+b);\nreturn 0;\n}",
        "#include <stdio.h>\nint main(){\nint a,b;\nscanf(\"%d %d\",&a,&b);\nb = 0;\nprintf(\"%d\",a/b);\nreturn 0;\n}"};

        SubmissionJudge submission = new SubmissionJudge();
        submission.setStatus(null);
        submission.setCid(0);
        submission.setPid(1000);
        submission.setUid(2);
        submission.setDate(null);
        submission.setSid(0);
        submission.setLang("C");
        submission.setAcTestCases(0);
        submission.setTotalTestCases(0);
        submission.setFirstWaCase(0);
        submission.setMinTimeUsed(0);
        submission.setMaxTimeUsed(0);
        submission.setAvgTimeUsed(0);
        submission.setAccepted(false);

        for (int i = 0; i < test.length; i++) {
            if (!test[i]) {
                continue;
            }
            final int index = i;

            submission.setSid(submission.getSid() + 1);
            submission.setSource(sources[i]);
            submission.setTimeLimit(time[i]);
            submission.setCaseTimeLimit(caseTime[i]);
            submission.setMemoryLimit(memory[i]);

            template.convertAndSend(submitQueue.getName(), submission);

            System.out.println(String.format("Problem %s sample solution with id %s sent in", submission.getPid(), submission.getSid()));
        }

        System.out.println("All languages sent.");
        ((CachingConnectionFactory)context.getBean(ConnectionFactory.class)).destroy();
    }

}
