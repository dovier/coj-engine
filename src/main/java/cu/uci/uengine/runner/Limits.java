/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.runner;

/**
 *
 * @author lan
 */
public interface Limits {

    public Long getMaxMemory();

    public Long getMaxCaseExecutionTime();

    public Long getMaxTotalExecutionTime();

    public Long getMaxSourceCodeLenght();
    
    public Long getMaxOutput();
}
