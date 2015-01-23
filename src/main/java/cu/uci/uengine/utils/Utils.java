package cu.uci.uengine.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import cu.uci.coj.model.SubmissionJudge;
import cu.uci.uengine.Language;

@Component
public class Utils {

    private static Map<String, Language> languages;

    private static FilenameFilter classFilesFilter = new FilenameFilter() {

        @Override
        public boolean accept(File arg0, String arg1) {
            // los archivos class que contienen $ son clases internas, que no
            // pueden ser ejecutables.
            return arg1.endsWith(".class") && !arg1.contains("$");
        }

    };

    public static boolean fixJavaName(SubmissionJudge submit) throws IOException,
            InterruptedException {
        // Si es error de compilacion y es Java, es posible que el error se
        // deba
        // a un fallo de compilacion porque el nombre de la clase no
        // corresponde
        // con el nombre del archivo. en ese caso hay que buscar la clase y
        // renombrar el archivo para que funcione.
        String err = submit.getErrMsg();
        int idx = err
                .indexOf(" is public, should be declared in a file named");

        if (idx != -1) {
            err = err.substring(0, idx);
            String[] words = err.split(" ");
            // el nombre que debe tener el archivo fuente para que javac
            // pueda compilarlo
            String filename = words[words.length - 1];
            submit.createSourceFile(filename);
            submit.createExecFile(filename);

            saveSourceFile(submit.getSourceFile(), submit.getSource());
            dos2unixFileFixer(submit.getSourceFile().getAbsolutePath());

            return true;

        }

        return false;
    }

    public static void saveSourceFile(File file, String content) throws IOException,
            InterruptedException {

        FileUtils.writeStringToFile(file, content);
    }

    public static boolean dos2unixFileFixer(String filePath) throws IOException, InterruptedException {
        // bash es muy sensible a los cambios de linea \r\n de Windows. Para
        // prevenir que esto cause Runtime Errors, es necesario convertirlos a
        // un sistema comprensible por ubuntu: \n normal en linux. El comando
        // dos2unix hace esto.
        // se lo dejamos a todos los codigos para evitar que algun otro lenguaje
        // tambien padezca de esto

        Properties langProps = new Properties();
        langProps.load(ClassLoader
                .getSystemResourceAsStream("uengine.properties"));

        String dos2unixPath = langProps.getProperty("dos2unix.path");

        ProcessBuilder pb = new ProcessBuilder(dos2unixPath, filePath);
        Process process = pb.start();
        process.waitFor();

        return process.exitValue() == 0;
    }

    public static void setVerdict(SubmissionJudge submit) {
        submit.setAccepted(false);
        switch (submit.getVerdict()) {
            case AC:
                submit.setStatus("Accepted");
                submit.setAccepted(true);
                break;
            case WA:
                submit.setStatus("Wrong Answer");
                break;
            case TLE:
                submit.setStatus("Time Limit Exceeded");
                break;
            case CE:
                submit.setStatus("Compilation Error");
                break;
            case IVF:
                submit.setStatus("Invalid Function");
                break;
            case MLE:
                submit.setStatus("Memory Limit Exceeded");
                break;
            case OLE:
                submit.setStatus("Output Limit Exceeded");
                break;
            case PE:
                submit.setStatus("Presentation Error");
                break;
            case RTE:
                submit.setStatus("Runtime Error");
                break;
            case SIE:
                submit.setStatus("Internal Error");
                break;
            case CTLE:
                //TODO: @Lan modified UEngine clients to manage this new status
                submit.setStatus("Case Time Limit Exceeded");
                break;
            default:
                break;
        }
    }

    public static String getKey(SubmissionJudge submit) {
        String key = null;
        switch (submit.getStatus()) {
            case "Accepted":
                key = "ac";
                break;
            case "Memory Limit Exceeded":
                key = "mle";
                break;
            case "Time Limit Exceeded":
                key = "tle";
                break;
            case "Wrong Answer":
                key = "wa";
                break;
            case "Compilation Error":
                key = "ce";
                break;
            case "Runtime Error":
                key = "rte";
                break;
            case "Output Limit Exceeded":
                key = "ole";
                break;
            case "Presentation Error":
                key = "pe";
                break;
            case "Unqualified":
                key = "uqd";
                break;
            case "Judging":
                key = "jdg";
                break;
            case "Invalid Function":
                key = "ivf";
                break;
            case "Case Time Limit Exceeded":
                key = "ctle";
                break;
        }
        return key;
    }

    public static Map<String, Language> getLanguages() {
        return languages;
    }

    @PostConstruct
    private void loadLanguages() throws IOException {
        Properties langProps = new Properties();
        langProps.load(ClassLoader
                .getSystemResourceAsStream("languages.properties"));

        String[] langs = langProps.getProperty("language.list").split(",");
        languages = new HashMap<>();

        for (String lang : langs) {
            Language l = new Language();
            l.setName(lang.trim());
            l.setExt(langProps.getProperty(lang.trim() + ".ext"));
            l.setCompileCmd(langProps.getProperty(lang.trim() + ".compile"));
            l.setExecCmd(langProps.getProperty(lang.trim() + ".exec"));
            l.setTimeMultiplier(Integer.valueOf(langProps.getProperty(lang
                    .trim() + ".time_mult")));
            l.setRetries(Integer.valueOf(langProps.getProperty(lang
                    .trim() + ".retries")));

            l.setMemoryMultiplier(Integer.valueOf(langProps.getProperty(lang
                    .trim() + ".memory_mult")));
            languages.put(l.getName(), l);
        }

    }
}
