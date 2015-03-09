/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cu.uci.uengine.runner.impl;

/**
 *
 * @author lan
 */
public class BasicLimits implements cu.uci.uengine.runner.Limits{
    
    private Long maxMemory;
    
    private Long maxCaseExecutionTime;
    
    private Long maxTotalExecutionTime;
    
    private Long maxOutput;

    public BasicLimits(Long maxMemory, Long maxCaseExecutionTime, Long maxTotalExecutionTime, Long maxOutput) {
        this.maxMemory = maxMemory;
        this.maxCaseExecutionTime = maxCaseExecutionTime;
        this.maxTotalExecutionTime = maxTotalExecutionTime;
        this.maxOutput = maxOutput;
    }

       
    /**
     * @return the maxMemory
     */
    @Override
    public Long getMaxMemory() {
        return maxMemory;
    }

    /**
     * @param maxMemory the maxMemory to set
     */
    @Override
    public void setMaxMemory(Long maxMemory) {
        this.maxMemory = maxMemory;
    }

    /**
     * @return the maxCaseExecutionTime
     */
    @Override
    public Long getMaxCaseExecutionTime() {
        return maxCaseExecutionTime;
    }

    /**
     * @param maxCaseExecutionTime the maxCaseExecutionTime to set
     */
    public void setMaxCaseExecutionTime(Long maxCaseExecutionTime) {
        this.maxCaseExecutionTime = maxCaseExecutionTime;
    }

    /**
     * @return the maxTotalExecutionTime
     */
    @Override
    public Long getMaxTotalExecutionTime() {
        return maxTotalExecutionTime;
    }

    /**
     * @param maxTotalExecutionTime the maxTotalExecutionTime to set
     */
    public void setMaxTotalExecutionTime(Long maxTotalExecutionTime) {
        this.maxTotalExecutionTime = maxTotalExecutionTime;
    }

    /**
     * @return the maxOutput
     */
    @Override
    public Long getMaxOutput() {
        return maxOutput;
    }

    /**
     * @param maxOutput the maxOutput to set
     */
    public void setMaxOutput(Long maxOutput) {
        this.maxOutput = maxOutput;
    }
}
