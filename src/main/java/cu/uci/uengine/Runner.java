package cu.uci.uengine;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import cu.uci.coj.model.SubmissionJudge;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Runner {

	static Log log = LogFactory.getLog(Runner.class.getName());

	@Resource
	private FileFilter inDataFilter;
	@Resource
	private FileFilter outDataFilter;
	@Resource
	private Properties properties;

	public SubmissionJudge run(SubmissionJudge submit, long outputLimit,
			long maxMemoryLimit, int timeMultiplier, int memoryMultiplier,
			File problemDir, File intDir) throws IOException {

		String command = submit.getLanguage().getExecCmd();
		command = command.replace("<EXE>", submit.getExecFile().getName());
		command = command.replace("<EXEDIR>", submit.getTmpDirSid()
				.getAbsolutePath());
		command = command.replace("<EXEPATH>", submit.getExecFile()
				.getAbsolutePath());

		File[] inDataFiles = problemDir.listFiles(inDataFilter);
		File[] outDataFiles = problemDir.listFiles(outDataFilter);

		inFilesPreprocessor(submit, inDataFiles);

		if (ArrayUtils.isEmpty(inDataFiles) || ArrayUtils.isEmpty(outDataFiles)
				|| (inDataFiles.length != outDataFiles.length)) {
			submit.setErrMsg("UEngine Error: Malformed or unexistent datasets are present for this problems.");
			submit.setVerdict(Verdicts.SIE);
			log.error(submit.getErrMsg());
			submit.setResponseValues(0, 0, 0, 0, 0, 0);
			return submit;
		}

		boolean finished = false;
		int idx = 0;
		int totalUserTime = 0;
		int minUserTime = submit.getTimeLimit() * timeMultiplier;
		int maxUserTime = 0;
		int totalCPUTime = 0;
		long memoryUsed = 0;
		submit.setFirstWaCase(0);
		while (!finished
				&& totalUserTime < submit.getTimeLimit() * timeMultiplier
				&& idx < inDataFiles.length) {
			log.info("running dataset " + inDataFiles[idx].getName());
			String name = inDataFiles[idx].getName().substring(0,
					inDataFiles[idx].getName().lastIndexOf(".in"));

			File outFile = new File(submit.getTmpDirSid(), name + ".out");

			File errFile = new File(submit.getTmpDirSid(), name + ".err");
			File intFile = new File(intDir, String.valueOf(submit.getSid()));

			List<String> uengineArgs = new ArrayList<>();
			/*
			 * uengineArgs.add("schroot"); uengineArgs.add("-c");
			 * uengineArgs.add("trusty_i386");
			 */
			uengineArgs.add(properties.getProperty("python.path"));
			uengineArgs.add(properties.getProperty("uengine.script"));

			uengineArgs.add(String.valueOf(submit.getCaseTimeLimit()
					* timeMultiplier));
			uengineArgs.add(String.valueOf(Math.min(submit.getMemoryLimit()
					* memoryMultiplier, maxMemoryLimit)));
			uengineArgs.add(inDataFiles[idx].getAbsolutePath());
			uengineArgs.add(outFile.getAbsolutePath());
			uengineArgs.add(errFile.getAbsolutePath());
			uengineArgs.add(intFile.getAbsolutePath());
			uengineArgs.addAll(Arrays.asList(command.split(" ")));
			ProcessBuilder pb = new ProcessBuilder(
					uengineArgs.toArray(new String[0]));
			// System.out.println(uengineArgs);
			try {
				Process process = pb.start();
				String[] results = execute(submit, errFile, process, submit
						.getLanguage().getRetries());

				switch (results[Results.RESULT]) {
				case "OK":
					totalUserTime += Integer
							.valueOf(results[Results.USER_TIME]);
					minUserTime = Math.min(minUserTime,
							Integer.valueOf(results[Results.USER_TIME]));
					maxUserTime = Math.max(maxUserTime,
							Integer.valueOf(results[Results.USER_TIME]));
					totalCPUTime += Integer.valueOf(results[Results.CPU_TIME]);
					// nos quedamos con la mayor memoria utilizada en
					// los
					// casos de prueba.
					memoryUsed = Math.max(
							Long.valueOf(results[Results.MEMORY]), memoryUsed);
					break;
				case "RF":
					submit.setVerdict(Verdicts.IVF);
					break;
				case "RT":
					submit.setErrMsg(IOUtils.toString(process.getInputStream())
							+ " " + IOUtils.toString(process.getErrorStream()));
					submit.setVerdict(Verdicts.RTE);
					break;
				case "TL":
					submit.setVerdict(Verdicts.CTLE);
					break;
				case "ML":
					submit.setVerdict(Verdicts.MLE);
					break;
				case "OL":
					submit.setVerdict(Verdicts.OLE);
					break;
				case "PD":
					submit.setErrMsg("UEngine: Pending:\n"
							+ FileUtils.readFileToString(errFile));
					submit.setVerdict(Verdicts.SIE);
					break;
				case "AT":
					submit.setErrMsg("UEngine: Abnormal Termination:\n"
							+ FileUtils.readFileToString(errFile));
					submit.setVerdict(Verdicts.RTE);
					break;
				case "IE":
					submit.setErrMsg("UEngine: Internal Error:\n"
							+ FileUtils.readFileToString(errFile));
					submit.setVerdict(Verdicts.SIE);
					break;
				case "BP":
					submit.setErrMsg("UEngine: Bad Policy:\n"
							+ FileUtils.readFileToString(errFile));
					submit.setVerdict(Verdicts.SIE);
					break;
				}

				// si la salida es mas grande que el limite, entonces OLE.
				// Esto no se puede chequear por defecto debido a que el
				// libsandbox administra mas ficheros que no solo la salida,
				// tambien el de error, y por eso no se puede configurar.
				if (outFile.length() > outputLimit) {
					submit.setVerdict(Verdicts.OLE);
					finished = true;
				}

			} catch (IOException e) {
				submit.setErrMsg(e.getMessage());
				submit.setVerdict(Verdicts.SIE);
				log.error(e.getMessage());
			} catch (InterruptedException e) {
				submit.setErrMsg(e.getMessage());
				submit.setVerdict(Verdicts.SIE);
				log.error(e.getMessage());
			}
			idx++;
		}

		// es posible que el ultimo caso se pase del tiempo total.
		if (totalUserTime > submit.getTimeLimit() * timeMultiplier) {
			submit.setVerdict(Verdicts.TLE);
		}

		int a = Integer.parseInt((String) properties.get("time.adjust.a"));
		int b = Integer.parseInt((String) properties.get("time.adjust.b"));
		submit.setResponseValues(a * totalUserTime / b, a * totalCPUTime / b,
				memoryUsed, a * minUserTime / b, a * maxUserTime / b,
				inDataFiles.length);
		return submit;
	}

	public String[] execute(SubmissionJudge submit, File errFile,
			Process process, int retries) throws InterruptedException,
			IOException {
		String[] results = null;
		int count = 0;
		do {
			// String error = IOUtils.toString(process.getErrorStream());
			process.waitFor();
			if (process.exitValue() != 0) {
				submit.setErrMsg(FileUtils.readFileToString(errFile));
				submit.setVerdict(Verdicts.SIE);
				return null;
			} else {
				// result,usertime,cputime,memory
				results = IOUtils.toString(process.getInputStream()).split(",");

				// el resultado OK significa que no dio problema ejecutar
				// con libsandbox. Los demas resultados son errores internos
				// o resultados que no precisan que se siga ejecutando (TLE,
				// MLE, etc.)
				// En OK se debe seguir con el proximo juego de datos, los
				// demas resultados ya detienen la ejecucion.
			}
		} while (count++ < retries && "RT".equals(results[0]));
		return results;
	}

	private void inFilesPreprocessor(SubmissionJudge submit, File[] inDataFiles)
			throws IOException {
		switch (submit.getLanguage().getName()) {
		case "Prolog":
			for (int i = 0; i < inDataFiles.length; i++) {

				File file = inDataFiles[i];

				String readFileToString = FileUtils.readFileToString(file);

				File newFile = new File(
						submit.getTmpDirSid().getAbsolutePath(), file.getName());

				FileUtils.writeStringToFile(newFile, "program.\n"
						+ readFileToString);

				inDataFiles[i] = newFile;
			}

			break;

		}
	}

}
