/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.model.dto;

import java.util.Map;

/**
 *
 * @author lan
 */
public class SubmissionDTO {

    private int id;

    private int problemId;

    private Map<String, Object> metadata;

    private Long memoryLimit;

    private Long timeLimit;

    private Long caseTimeLimit;

    private Long outputLimit;
    
    private Long sourceCodeLenghtLimit;
    
    private String evaluationType;

    private String language;

    private String sourceCode;

    private Boolean allResults;

    private Boolean trusted;

    public SubmissionDTO() {

    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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

    /**
     * @return the outputLimit
     */
    public Long getOutputLimit() {
        return outputLimit;
    }

    /**
     * @param outputLimit the outputLimit to set
     */
    public void setOutputLimit(Long outputLimit) {
        this.outputLimit = outputLimit;
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

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the sourceCode
     */
    public String getSourceCode() {
        return sourceCode;
    }

    /**
     * @param sourceCode the sourceCode to set
     */
    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    /**
     * @return the allResults
     */
    public Boolean isAllResults() {
        return allResults;
    }

    /**
     * @param allResults the allResults to set
     */
    public void setAllResults(Boolean allResults) {
        this.allResults = allResults;
    }

    /**
     * @return the trusted
     */
    public Boolean isTrusted() {
        return trusted;
    }

    /**
     * @param trusted the trusted to set
     */
    public void setTrusted(Boolean trusted) {
        this.trusted = trusted;
    }

    /**
     * @return the sourceCodeLenghtLimit
     */
    public Long getSourceCodeLenghtLimit() {
        return sourceCodeLenghtLimit;
    }

    /**
     * @param sourceCodeLenghtLimit the sourceCodeLenghtLimit to set
     */
    public void setSourceCodeLenghtLimit(Long sourceCodeLenghtLimit) {
        this.sourceCodeLenghtLimit = sourceCodeLenghtLimit;
    }

  
}
