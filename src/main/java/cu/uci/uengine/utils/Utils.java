package cu.uci.uengine.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import cu.uci.uengine.model.Submission;
import java.io.InputStream;

@Component
public class Utils {

    public static boolean fixJavaName(Submission submission) throws IOException,
            InterruptedException {
        // Si es error de compilacion y es Java, es posible que el error se
        // deba
        // a un fallo de compilacion porque el nombre de la clase no
        // corresponde
        // con el nombre del archivo. en ese caso hay que buscar la clase y
        // renombrar el archivo para que funcione.
        String err = submission.getErrorMessage();
        int idx = err
                .indexOf(" is public, should be declared in a file named");

        if (idx != -1) {
            err = err.substring(0, idx);
            String[] words = err.split(" ");
            // el nombre que debe tener el archivo fuente para que javac
            // pueda compilarlo
            String filename = words[words.length - 1];

            String path = FileUtils.writeStringToFile(filename + ".java", submission.getTemporaryDirectory(), submission.getSourceCode(), true);
            submission.setSourceFile(new File(path));
            submission.setErrorMessage(null);
            submission.setExecutablePath(null);
            
            return true;
        }

        return false;
    }

    public static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int available;
        while ((available = inputStream.available()) > 0) {
            byte[] bytes = new byte[available];
            inputStream.read(bytes);
            stringBuilder.append(new String(bytes));
        }

        return stringBuilder.toString();
    }
}
