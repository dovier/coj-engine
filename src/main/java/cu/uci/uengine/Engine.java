package cu.uci.uengine;

import cu.uci.uengine.amqp.SubmitsListener;
import cu.uci.uengine.compiler.Compiler;
import cu.uci.uengine.compiler.exceptions.CompilationException;
import cu.uci.uengine.compiler.exceptions.CompilerException;
import cu.uci.uengine.creators.LimitsFactory;
import cu.uci.uengine.creators.RunnerContextBuilder;
import cu.uci.uengine.creators.VerdictFactory;
import cu.uci.uengine.evaluator.Evaluator;
import cu.uci.uengine.evaluator.EvaluatorResult;
import cu.uci.uengine.languages.Languages;
import cu.uci.uengine.model.Submission;
import cu.uci.uengine.runner.Limits;
import cu.uci.uengine.runner.Runner;
import cu.uci.uengine.runner.RunnerResult;
import cu.uci.uengine.runner.exceptions.InvalidDataSetsFormatException;
import cu.uci.uengine.utils.FileUtils;
import cu.uci.uengine.utils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Properties;
import javax.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Engine {

    static Log log = LogFactory.getLog(Engine.class.getName());

    @Resource
    protected Compiler compiler;

    @Resource
    protected Runner runner;

    @Resource
    protected IOFileFilter inDataFilter;

    @Resource
    protected IOFileFilter outDataFilter;

    @Resource
    protected Limits engineLimits;

    @Resource
    protected Comparator<File> dataSetFileComparator;

    @Resource
    protected AbstractApplicationContext context;

    @Resource
    Languages languages;

    @Resource
    protected Properties properties;

    @Resource
    protected File intructionsDirectory;

    @Resource
    protected File temporaryDirectory;

    public Submission call(Submission submission) throws Exception {

        try {
            prepareForCompilation(submission);

            //@Lan: If compiler isn't available it should be a interpreted language
            if (!compiler.isCompilerAvailable(submission.getLanguageName()) || compile(submission, true)) {

                File datasetsDirectory = getDatasetsDirectory(submission);

                if (runner.isRunnable(submission.getLanguageName())) {
                    run(submission, datasetsDirectory);
                }

                evaluate(submission, datasetsDirectory);
            }
        } catch (Exception exception) {
            submission.setVerdict(Verdicts.SIE);
            submission.setErrorMessage(exception.getMessage());
        } finally {
            if (submission.getTemporaryDirectory() != null && !SubmitsListener.isDebugging) {

                FileUtils.deleteQuietly(submission.getTemporaryDirectory());
                log.info(String.format("Temporary Directory (%s) erased.", submission.getTemporaryDirectory().getAbsolutePath()));

            } else {
                log.info(String.format("Debugging is enabled, Temporary Directory (%s) wasn't erased.", submission.getTemporaryDirectory().getAbsolutePath()));
            }

        }

        return submission;
    }

    private void run(Submission submit, File problemDir) throws NumberFormatException, IOException, InterruptedException, InvalidDataSetsFormatException {

        File[] inputs = problemDir.listFiles((FilenameFilter) inDataFilter);

        submit.setLimits(validateLimits(String.valueOf(submit.getId()), submit.getLimits()));

        validateInputs(inputs);

        Arrays.sort(inputs, dataSetFileComparator);

        RunnerContextBuilder runnerContextBuilder = context.getBean(RunnerContextBuilder.class);
        runnerContextBuilder.setTemporaryDirectory(submit.getTemporaryDirectory());

        for (File input : inputs) {
            runnerContextBuilder.setInputFile(input);
            RunnerResult caseResult = runner.run(submit, runnerContextBuilder.build());

            submit.addRunnerResult(caseResult);
            if (!submit.isAllResults() && submit.getVerdict() != null) {
                break;
            }
        }
    }

    private void evaluate(Submission submission, File problemDir) throws Exception {
        if (submission.getVerdict() == null || submission.isAllResults()) {
            log.info("Evaluation started");

            File[] prototypes = problemDir.listFiles((FilenameFilter) outDataFilter);

            Arrays.sort(prototypes, dataSetFileComparator);

            Evaluator evaluator = context.getBean(submission.getEvaluationType(), Evaluator.class);

            prepareForEvaluation(submission, problemDir);

            Iterator<RunnerResult> it = submission.getRunnerResults().iterator();
            for (int i = 0; i < prototypes.length; ++i) {

                if (it.next().getResult() != RunnerResult.Result.OK) {
                    submission.addEvaluation((EvaluatorResult) null);
                    continue;
                }

                File prototype = prototypes[i];

                submission.setEvaluablePrototypeFilePath(prototype.getAbsolutePath());

                submission.setEvaluableFilePath(submission.getTemporaryDirectory().getAbsolutePath() + "/" + prototype.getName());

                submission.setInputFile(new File(FilenameUtils.removeExtension(prototype.getAbsolutePath()) + ".in"));

                EvaluatorResult evaluate = evaluator.evaluate(submission);
                submission.addEvaluation(evaluate);
                if (!submission.isAllResults() && evaluate.getResult() != EvaluatorResult.Result.AC) {
                    break;
                }
            }
        }
    }

    private void prepareForEvaluation(Submission submission, File problemDir) throws IOException {
        if (submission.getEvaluationType().equals("SpecialJudge")) {
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
            submission.setEvaluatorCheckerCommand(cmd);
        }
    }

    private File getDatasetsDirectory(Submission submission) {
        return new File(this.properties.getProperty("problems.dir"), String.valueOf(submission.getProblemId()));
    }

    private void prepareForCompilation(Submission submission) throws IOException, InterruptedException {
        submission.setLanguage(languages.getLanguage(submission.getLanguageName()));

        submission.setTemporaryDirectory(FileUtils.forceMkdir(temporaryDirectory, String.valueOf(submission.getId())));

        String fileName = String.format("%s.%s", Long.valueOf(submission.getId()), submission.getLanguage().getExtension());
        File sourceFile = new File(FileUtils.writeStringToFile(fileName, submission.getTemporaryDirectory(), submission.getSourceCode(), true));
        submission.setSourceFile(sourceFile);

    }

    private Limits validateLimits(String id, Limits limits) throws NumberFormatException {
        boolean isMaxMemoryInvalid = limits.getMaxMemory() == null || limits.getMaxMemory() > engineLimits.getMaxMemory();
        boolean isMaxCaseExecutionTimeInvalid = limits.getMaxCaseExecutionTime() == null || limits.getMaxCaseExecutionTime() > engineLimits.getMaxCaseExecutionTime();
        boolean isMaxTotalExecutionTimeInvalid = limits.getMaxTotalExecutionTime() == null || limits.getMaxTotalExecutionTime() > engineLimits.getMaxTotalExecutionTime();
        boolean isMaxSourceCodeLenghtInvalid = limits.getMaxSourceCodeLenght() == null || limits.getMaxSourceCodeLenght() > engineLimits.getMaxSourceCodeLenght();
        boolean isMaxOutputInvalid = limits.getMaxOutput() == null || limits.getMaxOutput() > engineLimits.getMaxOutput();

        if (isMaxMemoryInvalid || isMaxCaseExecutionTimeInvalid || isMaxTotalExecutionTimeInvalid || isMaxSourceCodeLenghtInvalid || isMaxOutputInvalid) {
            long adjustedMaxMemory = isMaxMemoryInvalid ? engineLimits.getMaxMemory() : limits.getMaxMemory(),
                    adjustedMaxCaseExecutionTime = isMaxCaseExecutionTimeInvalid ? engineLimits.getMaxCaseExecutionTime() : limits.getMaxCaseExecutionTime(),
                    adjustedMaxTotalExecutionTime = isMaxTotalExecutionTimeInvalid ? engineLimits.getMaxTotalExecutionTime() : limits.getMaxTotalExecutionTime(),
                    adjustedMaxSourceCodeLenght = isMaxSourceCodeLenghtInvalid ? engineLimits.getMaxSourceCodeLenght() : limits.getMaxSourceCodeLenght(),
                    adjustedMaxOutput = isMaxOutputInvalid ? engineLimits.getMaxOutput() : limits.getMaxOutput();

            Limits adjusted = LimitsFactory.create(limits.getClass().getSimpleName(), adjustedMaxMemory, adjustedMaxCaseExecutionTime, adjustedMaxTotalExecutionTime, adjustedMaxSourceCodeLenght, adjustedMaxOutput);

            log.warn(String.format("Limit adjusted FROM %s TO %s, because of engine maximum limits configuration.", limits.toString(), adjusted.toString()));
            return adjusted;
        }
        return limits;
    }

    private boolean autoFix(Submission submission) throws IOException, InterruptedException {
        switch (submission.getLanguageName()) {
            case "Java":
                return Utils.fixJavaName(submission);
        }
        return false;
    }

    private boolean compile(Submission submission, boolean autoFix) throws Exception {

        boolean isCompiled = false, isFixed = false;

        try {
            isCompiled = compiler.compile(submission);
        } catch (CompilerException ex) {
            submission.setErrorMessage(ex.getMessage());
            submission.setVerdict(Verdicts.SIE);
        } catch (CompilationException ex) {
            submission.setErrorMessage(ex.getMessage());
            submission.setVerdict(Verdicts.CE);
        }

        if (!isCompiled && autoFix) {
            isFixed = autoFix(submission);
        }

        if (isFixed) {
            submission.setVerdict(null);
            isCompiled = compile(submission, false);
        }

        return isCompiled;
    }

    private void validateInputs(File[] inputs) throws InvalidDataSetsFormatException {
        if (inputs.length == 0) {
            throw new InvalidDataSetsFormatException("There must be at least one dataset");
        }
    }

}
