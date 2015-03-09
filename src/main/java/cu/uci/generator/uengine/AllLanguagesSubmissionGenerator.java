package cu.uci.generator.uengine;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import cu.uci.coj.model.SubmissionJudge;

@Component
public class AllLanguagesSubmissionGenerator {

    @Resource
    private JsonMessageConverter jsonMessageConverter;

    static Log log = LogFactory.getLog(AllLanguagesSubmissionGenerator.class.getName());

    private int countAccepted;

    public static final void main(String[] args) throws IOException {

        ApplicationContext context
                = new AnnotationConfigApplicationContext(RabbitMQConfig.class);
        AmqpTemplate template = context.getBean(AmqpTemplate.class);

        Queue submitQueue = (Queue) context.getBean("submits");

        boolean[] test = {false,//C
            false,//Ruby
            false,//Pascal
            true,//C++
            false,//Java
            false,//C#
            false,//Bash
            false,//C++11
            false,//Perl
            false,//PHP
            false,//Python
            false};//Prolog

        String[] languages = {"C",
            "Ruby",
            "Pascal",
            "C++",
            "Java",
            "C#",
            "Bash",
            "C++11",
            "Perl",
            "PHP",
            "Python",
            "Prolog"};

        final int[] priorities = {1,//C
            1,//Ruby
            1,//Pascal
            1,//C++
            1,//Java
            1,//C#
            1,//Bash
            1,//C++11
            5,//Perl
            7,//PHP
            4,//Python
            4};//Prolog

        
        String[] sources = {"#include <stdio.h>\nint main(){\nint a,b;\nscanf(\"%d %d\",&a,&b);\nprintf(\"%d\",a+b);\nreturn 0;\n}",
            "a_string = gets\r\na_arr = a_string.split(\" \")\r\na_sum = Integer(a_arr[0]) + Integer(a_arr[1])\r\nprint(\"#{a_sum}\\n\")",
            "var\r\na,b : Integer;\r\nbegin\r\n  read(a,b);\r\n  writeln(a+b);\r\nend.",
            "#include<cstdio>\r\n\r\nint a,b;\r\n\r\nint main()\r\n{\r\n    scanf(\"%d%d\",&a,&b);\r\n    printf(\"%d\\n\",a+b);\r\n    return 0;\r\n}",
            "import java.util.Scanner;\r\npublic class AmasB {\r\n\r\n    public static void main(String[] args) {\r\n     \r\n     int a,b;\r\n        Scanner numero = new Scanner(System.in);\r\n     \r\n       a = numero.nextInt(); \r\n       b = numero.nextInt()+a;  \r\n\r\n     \r\n        System.out.println( b );\r\n    }\r\n}",
            "using System;\r\n\r\n\r\nnamespace COJ\r\n{\r\n    class Program\r\n    {\r\n        static void Main(string[] args)\r\n        {\r\n            string[] c = Console.ReadLine().Split(' ');\r\n            Console.WriteLine(byte.Parse(c[0]) + byte.Parse(c[1]));\r\n        }\r\n    }\r\n}",
            "read a b\r\necho $(($a+$b))",
            "#include <iostream>\r\n\r\nusing namespace std;\r\n\r\nint main() {\r\n    int a, b;\r\n    cin >> a >> b;\r\n    cout << a + b;\r\n    return 0;\r\n}",
            "$in = <STDIN>;\r\nchomp($in);\r\n\r\nwhile ($in) {\r\n\t($a, $b) = split(\" \", $in);\r\n\tprint $a + $b, \"\\n\";\r\n\t$in = <STDIN>;\r\n\tchomp($in);\r\n}",
            "<?php\r\n$stdin = fopen('php://stdin', 'r');\r\n$input = fgets($stdin, 10);\r\nlist($a,$b) = split(\" \",$input);\r\necho $a + $b,\"\\n\";\r\nfclose($stdin);\r\n?>",
            "import string\r\nvalues = raw_input()\r\ntry:\r\n    while values:\r\n        values = string.split(values,\" \")\r\n        print int(values[0])+int(values[1])\r\n        values = raw_input()\r\nexcept EOFError:\r\n    exit",
            "program:-read_number(A),read_number(B),C is A+B,write(C)."
        };

        SubmissionJudge submission = new SubmissionJudge();
        submission.setStatus(null);
        submission.setCid(0);
        submission.setPid(1000);
        submission.setUid(2);
        submission.setDate(null);
        submission.setSid(728342);
        submission.setTimeLimit(10000);
        submission.setCaseTimeLimit(5000);
        submission.setMemoryLimit(new Long("2264682496"));
        submission.setAcTestCases(0);
        submission.setTotalTestCases(0);
        submission.setFirstWaCase(0);
        submission.setMinTimeUsed(0);
        submission.setMaxTimeUsed(0);
        submission.setAvgTimeUsed(0);
        submission.setAccepted(false);

        while (true) {
            for (int i = 0; i < languages.length; i++) {
                if (!test[i]) {
                    continue;
                }
                final int index = i;

                submission.setSid(submission.getSid() + 1);
                submission.setLang(languages[i]);
                submission.setSource(sources[i]);

                template.convertAndSend(submitQueue.getName(), submission, new MessagePostProcessor() {

                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        message.getMessageProperties().setPriority(priorities[index]);
                        return message;
                    }
                });

                System.out.println(String.format("Problem %s sample solution with id %s sent in language %s.", submission.getPid(), submission.getSid(), languages[i]));
            }

            System.out.println("All languages sent.");
            System.in.read();
        }
        //context.getBean(CachingConnectionFactory.class).destroy();
    }

}
