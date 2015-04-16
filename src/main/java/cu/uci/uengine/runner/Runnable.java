/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.runner;

import cu.uci.uengine.model.Identifiable;
import java.io.File;

/**
 *
 * @author lan
 */
public interface Runnable extends Identifiable {

    public String getLanguageName();

    public File getRunnableFile();

    public Limits getLimits();
    
    public boolean isTrusted();

}
