package cu.uci.uengine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.uci.uengine.model.SubmissionJudge;

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
		String cmd = properties.get("checker.exec").toString();
		Properties p = new Properties();
		p.load(new FileInputStream(new File(problemDir, cmd)));
		cmd = p.getProperty("checker.exec");
		// esto es para no quitar la configuracion que existia del motor
		// anterior, que tenia ./ delante
		if (cmd.startsWith("./"))
			cmd = "Checker" + cmd.substring(1);
		// entrada
		cmd = cmd.replaceAll("<1>", problemInFile.getAbsolutePath());
		// solucion correcta
		cmd = cmd.replaceAll("<2>", tmpOutFile.getAbsolutePath());
		// solucion del usuario
		cmd = cmd.replaceAll("<3>", problemOutFile.getAbsolutePath());

		ProcessBuilder pb = new ProcessBuilder(
				new File(problemDir, cmd).getAbsolutePath());
		Process process = pb.start();

		process.waitFor();
		submit.setVerdict(process.exitValue() == 0 ? Verdicts.AC : Verdicts.WA);
		return false;
	}
}
