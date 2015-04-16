/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.model.dto;

import cu.uci.uengine.Verdicts;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lan
 */
public class VerdictDTO {

    protected int id;

    protected int problemId;

    protected Map<String, Object> metadata;

    protected Verdicts verdict;

    protected Long timeUsed;
    protected Long cpuTimeUsed;
    protected Long memoryUsed;

    protected String message;

    protected int acceptedDatasets;
    protected int processedDatasets;

    protected Integer firstFailedDataset;

    protected Long minTimeUsed;
    protected Long maxTimeUsed;
    protected Long averageTimeUsed;

    protected Date evaluationDate;

    private List<DatasetVerdictDTO> datasetVerdictDTO;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    /**
     * @return the metadata
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    /**
     * @return the evaluationDate
     */
    public Date getEvaluationDate() {
        return evaluationDate;
    }

    /**
     * @param evaluationDate the evaluationDate to set
     */
    public void setEvaluationDate(Date evaluationDate) {
        this.evaluationDate = evaluationDate;
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

    /**
     * @return the datasetVerdictDTO
     */
    public List<DatasetVerdictDTO> getDatasetVerdictDTO() {
        return datasetVerdictDTO;
    }

    /**
     * @param datasetVerdictDTO the datasetVerdictDTO to set
     */
    public void setDatasetVerdictDTO(List<DatasetVerdictDTO> datasetVerdictDTO) {
        this.datasetVerdictDTO = datasetVerdictDTO;
    }

}
