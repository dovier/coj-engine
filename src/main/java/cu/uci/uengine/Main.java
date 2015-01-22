package cu.uci.uengine;



import cu.uci.uengine.config.Config;
import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {

    public static final void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext(Config.class);
        Properties props = (Properties) appCtx.getBean("properties");
    }

}
