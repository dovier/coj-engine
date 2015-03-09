/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.model.dto;

import cu.uci.uengine.Verdicts;

/**
 *
 * @author lan
 */
public class VerdictDTO {

    protected int submissionId;

    //TODO: @Lan - esto vamos a dejarlo aquí porque el COJ lo necesita debido a que separa las submisiones de los contest y del 24h
    protected Integer cid;

    protected Verdicts verdict;

    protected Long timeUsed;//Se refiere al userTime
    protected Long cpuTimeUsed;
    protected Long memoryUsed;

    protected String message;

    protected int acceptedDatasets;
    protected int processedDatasets;

    protected Integer firstFailedDataset;

    protected Long minTimeUsed;
    protected Long maxTimeUsed;
    protected Long averageTimeUsed;

    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Verdicts getVerdict() {
        return verdict;
    }

    public void setVerdict(Verdicts verdict) {
        this.verdict = verdict;
    }

    public Long getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(Long timeUsed) {
        this.timeUsed = timeUsed;
    }

    public Long getCpuTimeUsed() {
        return cpuTimeUsed;
    }

    public void setCpuTimeUsed(Long cpuTimeUsed) {
        this.cpuTimeUsed = cpuTimeUsed;
    }

    public Long getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(Long memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAcceptedDatasets() {
        return acceptedDatasets;
    }

    public void setAcceptedDatasets(int acceptedDatasets) {
        this.acceptedDatasets = acceptedDatasets;
    }

    public int getProcessedDatasets() {
        return processedDatasets;
    }

    public void setProcessedDatasets(int processedDatasets) {
        this.processedDatasets = processedDatasets;
    }

    public Integer getFirstFailedDataset() {
        return firstFailedDataset;
    }

    public void setFirstFailedDataset(Integer firstFailedDataset) {
        this.firstFailedDataset = firstFailedDataset;
    }

    public Long getMinTimeUsed() {
        return minTimeUsed;
    }

    public void setMinTimeUsed(Long minTimeUsed) {
        this.minTimeUsed = minTimeUsed;
    }

    public Long getMaxTimeUsed() {
        return maxTimeUsed;
    }

    public void setMaxTimeUsed(Long maxTimeUsed) {
        this.maxTimeUsed = maxTimeUsed;
    }

    public Long getAverageTimeUsed() {
        return averageTimeUsed;
    }

    public void setAverageTimeUsed(Long averageTimeUsed) {
        this.averageTimeUsed = averageTimeUsed;
    }

}
