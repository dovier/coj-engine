/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.compiler;

import cu.uci.uengine.model.Identifiable;
import cu.uci.uengine.model.TemporaryDirectoryAware;
import java.io.File;

/**
 *
 * @author lan
 */
public interface Compilable extends TemporaryDirectoryAware, Identifiable {

    public String getExecutablePath();

    public void setExecutablePath(String executablePath);

    public String getLanguageName();

    public String getSourceCode();

    public File getSourceFile();

}
