/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.creators;

import cu.uci.uengine.runner.Limits;
import cu.uci.uengine.runner.impl.BasicLimits;

/**
 *
 * @author lan
 */
public class LimitsFactory {

    public static Limits create(String type, Long maxMemory, Long maxCaseExecutionTime, Long maxTotalExecutionTime, Long maxOutput) {
        switch (type) {
            case "BasicLimits":
                return new BasicLimits(maxMemory, maxCaseExecutionTime, maxTotalExecutionTime, maxOutput);
            default:
                throw new NoClassDefFoundError("The is no Limits of type: " + type);
        }
    }
}
