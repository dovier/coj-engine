/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.runner.impl;

import cu.uci.uengine.runner.Limits;
import cu.uci.uengine.runner.Runner;
import cu.uci.uengine.runner.Runnable;
import cu.uci.uengine.runner.RunnerContext;
import cu.uci.uengine.runner.RunnerResult;
import cu.uci.uengine.runner.SandboxResults;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author lan
 */
@Component
public class FileRunner implements Runner {

    static Log log = LogFactory.getLog(FileRunner.class);

    @Resource
    private Properties properties;

    private HashMap<String, String> executionCommands;

    @PostConstruct
    private void loadLanguages() throws IOException {
        Properties langProps = new Properties();
        langProps.load(ClassLoader
                .getSystemResourceAsStream("languages.properties"));

        String[] languages = langProps.getProperty("language.list").split(",");
        executionCommands = new HashMap();

        for (String language : languages) {

            language = language.trim();

            String executionCommand = langProps.getProperty(language + ".exec").trim();

            if (!StringUtils.isEmpty(executionCommand)) {
                executionCommands.put(language, executionCommand);
            }
        }
    }

    private String buildCommand(String language, String filePath, String directoryPath) {
        String command = executionCommands.get(language);

        command = command.replace("<EXE>", FilenameUtils.getName(filePath));
        command = command.replace("<EXEDIR>", directoryPath);
        command = command.replace("<EXEPATH>", filePath);
        return command;
    }

    @Override
    public RunnerResult run(Runnable runnable, RunnerContext runnerContext) throws IOException, InterruptedException {

        String command = buildCommand(runnable.getLanguageName(), runnable.getRunnableFile().getAbsolutePath(), runnerContext.getTemporaryDirectory().getAbsolutePath());

        log.info("Running dataset " + runnerContext.getInputFile().getName());

        String name = FilenameUtils.getBaseName(runnerContext.getInputFile().getName());

        File outFile = new File(runnerContext.getTemporaryDirectory(), name + ".out");
        File errFile = new File(runnerContext.getTemporaryDirectory(), name + ".err");
        File intFile = new File(runnerContext.getInstructionDirectory(), String.valueOf(runnable.getId()));

        ProcessBuilder pb = buildProcessBuilder(runnable.getLimits(), runnerContext.getInputFile().getAbsolutePath(), outFile, errFile, intFile, command);

        Process process = pb.start();

        process.waitFor();

        if (process.exitValue() != 0) {
            return new RunnerResult(RunnerResult.Result.IE, FileUtils.readFileToString(errFile));
        }
        // result,usertime,cputime,memory
        String[] results = IOUtils.toString(process.getInputStream())
                .split(",");

        // el resultado OK significa que no dio problema ejecutar
        // con libsandbox. Los demas resultados son errores internos
        // o resultados que no precisan que se siga ejecutando (TLE,
        // MLE, etc.)
        // En OK se debe seguir con el proximo juego de datos, los
        // demas resultados ya detienen la ejecucion.
        RunnerResult result = null;

        String resultCode = results[SandboxResults.RESULT];

        switch (resultCode) {
            case "OK":
                result = new RunnerResult(RunnerResult.Result.OK, name, Long.valueOf(results[SandboxResults.USER_TIME]), Long.valueOf(results[SandboxResults.CPU_TIME]), Long.valueOf(results[SandboxResults.MEMORY]));
                break;
            case "AT":
                result = new RunnerResult(RunnerResult.Result.RT);
            break;
            case "TL":
                result = new RunnerResult(RunnerResult.Result.CTL);
                break;
            case "RF":
            case "ML":
            case "OL":
            case "RT":
            case "IE":
            case "BP":
            case "PD":
                result = new RunnerResult(RunnerResult.Result.valueOf(resultCode));
                result.messageConcat(FileUtils.readFileToString(errFile));
                break;
        }

        boolean exceedOutputLimit = (runnable.getLimits().getMaxOutput() == null) ? false : outFile.length() > runnable.getLimits().getMaxOutput();
        boolean exceedMaxOutputLimit = outFile.length() > Long.valueOf(properties.getProperty("output.limit"));

        if (exceedOutputLimit || exceedMaxOutputLimit) {
            result = new RunnerResult(RunnerResult.Result.OL);
        }

        return result;
    }

    private ProcessBuilder buildProcessBuilder(Limits limits, String inputPath, File outFile, File errFile, File intFile, String command) {
        List<String> uengineArgs = new ArrayList<>();
        uengineArgs.add(properties.getProperty("python.path"));
        uengineArgs.add(properties.getProperty("uengine.script"));
        uengineArgs.add(String.valueOf(limits.getMaxCaseExecutionTime()));
        uengineArgs.add(String.valueOf(limits.getMaxMemory()));
        uengineArgs.add(inputPath);
        uengineArgs.add(outFile.getAbsolutePath());
        uengineArgs.add(errFile.getAbsolutePath());
        uengineArgs.add(intFile.getAbsolutePath());
        uengineArgs.addAll(Arrays.asList(command.split(" ")));
        return new ProcessBuilder(
                uengineArgs.toArray(new String[0]));
    }

    @Override
    public boolean isRunnable(String language) {
        return executionCommands.containsKey(language);
    }

}
