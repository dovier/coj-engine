/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.model;

import cu.uci.uengine.compiler.Compilable;
import cu.uci.uengine.Verdicts;
import cu.uci.uengine.evaluator.Evaluable;
import cu.uci.uengine.evaluator.EvaluatorResult;
import cu.uci.uengine.runner.Limits;
import cu.uci.uengine.creators.LimitsFactory;
import cu.uci.uengine.creators.VerdictFactory;
import cu.uci.uengine.languages.Language;
import cu.uci.uengine.runner.RunnerResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lan
 */
public class Submission implements Compilable, cu.uci.uengine.runner.Runnable, Evaluable {

    //Input
    protected int id;

    protected int problemId;

    protected Integer contestId;

    //Limits
    protected Long memoryLimit;

    protected Long timeLimit;

    protected Long caseTimeLimit;

    protected Long outputLimit;

    protected Limits limits;

    protected String evaluationType;

    protected String languageName;

    private Language language;

    protected String sourceCode;

    private Boolean isStopOnError;

    //End Input
    protected List<EvaluatorResult> evaluatorResults;
    protected List<RunnerResult> runnerResults;

    protected Verdicts verdict;

    protected String errorMessage;

    protected Integer lastProcessedTestCase;

    protected File temporaryDirectory;

    protected File sourceFile;

    protected String runnableFile;

    private File inputFile;

    protected String executablePath;

    protected String evaluableFilePath;

    protected String evaluablePrototypeFilePath;

    protected String evaluatorCheckerCommand;

    private Long timeUsed;
    private Long cpuTimeUsed;
    private Long memoryUsed;
    private int acceptedDatasets;
    private Integer firstEvaluationFailedDataset;
    private Integer firstRunnerFailedDataset;
    private Long minTimeUsed;
    private Long maxTimeUsed;

    public Submission() {
        evaluatorResults = new ArrayList(20);
        runnerResults = new ArrayList(20);
        isStopOnError = true;
        acceptedDatasets = 0;
    }

    @Override
    public String getExecutablePath() {
        return executablePath;
    }

    @Override
    public String getSourceCode() {
        return sourceCode;
    }

    @Override
    public File getTemporaryDirectory() {
        return temporaryDirectory;
    }

    public void setTemporaryDirectory(File temporaryDirectory) {
        this.temporaryDirectory = temporaryDirectory;
    }

    /**
     * @return the verdict
     */
    public Verdicts getVerdict() {

        return verdict;
    }

    /**
     * @param verdict the verdict to set
     */
    public void setVerdict(Verdicts verdict) {
        this.verdict = verdict;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    /**
     * @return the memoryLimit
     */
    public Long getMemoryLimit() {
        return memoryLimit;
    }

    /**
     * @param memoryLimit the memoryLimit to set
     */
    public void setMemoryLimit(Long memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    /**
     * @return the timeLimit
     */
    public Long getTimeLimit() {
        return timeLimit;
    }

    /**
     * @param timeLimit the timeLimit to set
     */
    public void setTimeLimit(Long timeLimit) {
        this.timeLimit = timeLimit;
    }

    /**
     * @return the caseTimeLimit
     */
    public Long getCaseTimeLimit() {
        return caseTimeLimit;
    }

    /**
     * @param caseTimeLimit the caseTimeLimit to set
     */
    public void setCaseTimeLimit(Long caseTimeLimit) {
        this.caseTimeLimit = caseTimeLimit;
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * @return the problemId
     */
    public int getProblemId() {
        return problemId;
    }

    /**
     * @param problemId the problemId to set
     */
    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    @Override
    public String getEvaluableFilePath() {
        return evaluableFilePath;
    }

    public void setEvaluableFilePath(String evaluableFilePath) {
        this.evaluableFilePath = evaluableFilePath;
    }

    @Override
    public String getEvaluablePrototypeFilePath() {
        return evaluablePrototypeFilePath;
    }

    public void setEvaluablePrototypeFilePath(String evaluablePrototypeFilePath) {
        this.evaluablePrototypeFilePath = evaluablePrototypeFilePath;
    }

    /**
     * @return the lastProcessedTestCase
     */
    public Integer getLastProcessedTestCase() {
        return lastProcessedTestCase;
    }

    /**
     * @param lastProcessedTestCase the lastProcessedTestCase to set
     */
    public void setLastProcessedTestCase(Integer lastProcessedTestCase) {
        this.lastProcessedTestCase = lastProcessedTestCase;
    }

    /**
     * @return the evaluationType
     */
    public String getEvaluationType() {
        return evaluationType;
    }

    /**
     * @param evaluationType the evaluationType to set
     */
    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }

    @Override
    public void setExecutablePath(String executablePath) {
        this.executablePath = executablePath;
    }

    @Override
    public File getRunnableFile() {
        if (executablePath != null) {
            return new File(this.executablePath);
        }

        return getSourceFile();
    }

    /**
     * @return the isStopOnError
     */
    public Boolean isStopOnError() {
        return isStopOnError;
    }

    /**
     * @param isStopOnError the isStopOnError to set
     */
    public void setStopOnError(Boolean isStopOnError) {
        this.isStopOnError = isStopOnError;
    }

    public void addRunnerResult(RunnerResult... runnerResultsList) {

        for (RunnerResult runnerResult : runnerResultsList) {
            runnerResults.add(runnerResult);

            if (runnerResult.getUserTime() != null) {
                timeUsed = (timeUsed == null ? runnerResult.getUserTime() : timeUsed + runnerResult.getUserTime());
                minTimeUsed = (minTimeUsed != null ? Math.min(minTimeUsed, runnerResult.getUserTime()) : runnerResult.getUserTime());
                maxTimeUsed = (maxTimeUsed != null ? Math.max(maxTimeUsed, runnerResult.getUserTime()) : runnerResult.getUserTime());
            }

            if (runnerResult.getCpuTime() != null) {
                cpuTimeUsed = (cpuTimeUsed == null ? runnerResult.getCpuTime() : cpuTimeUsed + runnerResult.getCpuTime());
            }

            if (runnerResult.getMemory() != null) {
                memoryUsed = (memoryUsed == null ? runnerResult.getMemory() : memoryUsed + runnerResult.getMemory());
            }

            if (runnerResult.getResult() != RunnerResult.Result.OK && firstRunnerFailedDataset == null) {
                firstRunnerFailedDataset = runnerResults.size() - 1;
            }
        }

    }

    public void addEvaluation(EvaluatorResult... evaluatorResultList) {

        for (EvaluatorResult evaluatorResult : evaluatorResultList) {
            evaluatorResults.add(evaluatorResult);

            if (verdict == null || verdict == Verdicts.AC) {
                verdict = VerdictFactory.create(evaluatorResult.getResult());
            }
            if (evaluatorResult.getResult() == EvaluatorResult.Result.ACCEPTED) {
                ++acceptedDatasets;
            } else if (firstEvaluationFailedDataset == null) {
                firstEvaluationFailedDataset = evaluatorResults.size() - 1;
            }
        }
    }

    @Override
    public Limits getLimits() {
        if (limits == null) {
            setMemoryLimit(memoryLimit * language.getMemoryMultiplier());
            setTimeLimit(timeLimit * language.getTimeMultiplier());
            setCaseTimeLimit(caseTimeLimit * language.getTimeMultiplier());
            limits = LimitsFactory.create("BasicLimits", getMemoryLimit(), getCaseTimeLimit(), getTimeLimit(), outputLimit);
        }
        return limits;
    }

    public void setLimits(Limits limits) {
        this.limits = limits;
    }

    public Long getTimeUsed() {
        return timeUsed;
    }

    public Long getCpuTimeUsed() {

        return cpuTimeUsed;
    }

    public Long getMemoryUsed() {
        return memoryUsed;
    }

    public int getAcceptedDatasets() {

        return acceptedDatasets;
    }

    public int getProcessedDatasets() {
        return runnerResults.size();

    }

    public Integer getFirstEvaluationFailedDataset() {
        return firstEvaluationFailedDataset;
    }

    public Integer getFirstRunnerFailedDataset() {
        return firstRunnerFailedDataset;
    }

    public Long getMinTimeUsed() {
        return minTimeUsed;

    }

    public Long getMaxTimeUsed() {

        return maxTimeUsed;
    }

    public Long getAverageTimeUsed() {
        if (getProcessedDatasets() == 0 || getTimeUsed() == null) {
            return null;
        }

        return getTimeUsed() / (long) getProcessedDatasets();
    }

    public Integer getContestId() {
        return contestId;
    }

    @Override
    public File getInputFile() {
        return this.inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    @Override
    public String getCommand() {
        return this.evaluatorCheckerCommand;
    }

    public void setEvaluatorCheckerCommand(String evaluatorCheckerCommand) {
        this.evaluatorCheckerCommand = evaluatorCheckerCommand;
    }

    @Override
    public String getLanguageName() {
        return languageName;
    }

    /**
     * @return the languageName_
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * @param language the languageName_ to set
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

}
