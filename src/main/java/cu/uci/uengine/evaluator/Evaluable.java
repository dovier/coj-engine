/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cu.uci.uengine.evaluator;

import java.io.File;
import java.util.Properties;

/**
 *
 * @author lan
 */
public interface Evaluable {
    public String getEvaluableFilePath();
    
    public String getEvaluablePrototypeFilePath();
    
    public File getInputFile();

    public String getCommand();
}
