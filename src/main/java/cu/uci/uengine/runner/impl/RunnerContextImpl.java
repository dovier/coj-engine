/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cu.uci.uengine.runner.impl;

import cu.uci.uengine.runner.RunnerContext;
import java.io.File;

/**
 *
 * @author lan
 */

public class RunnerContextImpl implements RunnerContext{

    private File inputFile;
    
    private File instructionDirectory;
    
    private File temporaryDirectory;

    public RunnerContextImpl(File inputFile,File instructionDirectory,File temporaryDirectory ){
        this.inputFile = inputFile;
        this.instructionDirectory = instructionDirectory;
        this.temporaryDirectory = temporaryDirectory;
    }
    
    /**
     * @return the inputFile
     */
    @Override
    public File getInputFile() {
        return inputFile;
    }

    /**
     * @param inputFile the inputFile to set
     */
    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * @return the instructionDirectory
     */
    @Override
    public File getInstructionDirectory() {
        return instructionDirectory;
    }

    /**
     * @param instructionDirectory the instructionDirectory to set
     */
    public void setInstructionDirectory(File instructionDirectory) {
        this.instructionDirectory = instructionDirectory;
    }

    /**
     * @return the temporaryDirectory
     */
    @Override
    public File getTemporaryDirectory() {
        return temporaryDirectory;
    }

    /**
     * @param temporaryDirectory the temporaryDirectory to set
     */
    public void setTemporaryDirectory(File temporaryDirectory) {
        this.temporaryDirectory = temporaryDirectory;
    }
    
   
}
