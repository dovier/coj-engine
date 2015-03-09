/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.creators;

import cu.uci.uengine.runner.RunnerContext;
import cu.uci.uengine.runner.impl.RunnerContextImpl;
import java.io.File;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author lan
 */
@Component
@Scope(value = "prototype")
public class RunnerContextBuilder {

    private File inputFile;

    private File instructionDirectory;

    private File temporaryDirectory;

    /**
     * @param inputFile the inputFile to set
     * @return 
     */
    public RunnerContextBuilder setInputFile(File inputFile) {
        this.inputFile = inputFile;
        return this;
    }

    /**
     * @param instructionDirectory the instructionDirectory to set
     * @return 
     */
    public RunnerContextBuilder setInstructionDirectory(File instructionDirectory) {
        this.instructionDirectory = instructionDirectory;
        return this;
    }

    /**
     * @param temporaryDirectory the temporaryDirectory to set
     * @return 
     */
    public RunnerContextBuilder setTemporaryDirectory(File temporaryDirectory) {
        this.temporaryDirectory = temporaryDirectory;
        return this;
    }
    
    public RunnerContext build(){
        return new RunnerContextImpl(inputFile, instructionDirectory, temporaryDirectory);
    }

}
