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
public class DatasetVerdictDTO {

    private String message;
    private Long userTime;
    private Long cpuTime;
    private Long memory;

    private Verdicts verdict;
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the userTime
     */
    public Long getUserTime() {
        return userTime;
    }

    /**
     * @param userTime the userTime to set
     */
    public void setUserTime(Long userTime) {
        this.userTime = userTime;
    }

    /**
     * @return the cpuTime
     */
    public Long getCpuTime() {
        return cpuTime;
    }

    /**
     * @param cpuTime the cpuTime to set
     */
    public void setCpuTime(Long cpuTime) {
        this.cpuTime = cpuTime;
    }

    /**
     * @return the memory
     */
    public Long getMemory() {
        return memory;
    }

    /**
     * @param memory the memory to set
     */
    public void setMemory(Long memory) {
        this.memory = memory;
    }

    /**
     * @return the verdict
     */
    public Verdicts getVerdict() {
        return verdict;
    }

    /**
     * @param verdicts the verdict to set
     */
    public void setVerdict(Verdicts verdicts) {
        this.verdict = verdicts;
    }
   
}
