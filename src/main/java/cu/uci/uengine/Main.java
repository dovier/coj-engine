package cu.uci.uengine;



import cu.uci.uengine.config.Config;
import java.io.IOException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {

    public static final void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext(Config.class);
        
    }

}
