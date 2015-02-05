package cu.uci.uengine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.uci.coj.model.SubmissionJudge;
import cu.uci.uengine.utils.Utils;
import java.io.BufferedInputStream;
import java.io.InputStream;

@Component
public class SpecialJudgeComparator extends ComparatorBase {

    @Autowired
    private Properties properties;

    public File getTmpOutFile(SubmissionJudge submit, String name) {
        return new File(submit.getTmpDirSid(), name + ".out");
    }

    @Override
    public boolean diffFile(int idx, File problemDir, File problemInFile,
            File problemOutFile, File tmpOutFile, SubmissionJudge submit)
            throws IOException, InterruptedException {
        String cmd = properties.get("checker.config").toString();
        Properties p = new Properties();
        p.load(new FileInputStream(new File(problemDir, cmd)));
        cmd = p.getProperty("checker.exec");
        // esto es para no quitar la configuracion que existia del motor
        // anterior, que tenia ./ delante
        if (cmd.startsWith("./")) {
            cmd = "Checker" + cmd.substring(1);
        }

        cmd = problemDir + "/" + cmd;
        // entrada
        cmd = cmd.replaceAll("<1>", problemInFile.getAbsolutePath());
        // solucion correcta
        cmd = cmd.replaceAll("<2>", problemOutFile.getAbsolutePath());
        // solucion del usuario
        cmd = cmd.replaceAll("<3>",tmpOutFile.getAbsolutePath() );

        String[] strings = cmd.trim().split(" ");
        ProcessBuilder pb = new ProcessBuilder(strings);
        Process process = pb.start();
        process.waitFor();

        if (process.exitValue() != 0) {
            submit.setVerdict(process.exitValue() == 200?Verdicts.AC:Verdicts.WA);
        } else {
            InputStream inputStream = process.getErrorStream();
            
            String veredict = Utils.readInputStream(inputStream);
            submit.setVerdict(veredict.equals("Accepted") ? Verdicts.AC : Verdicts.WA);
        }
        return false;
    }
}
