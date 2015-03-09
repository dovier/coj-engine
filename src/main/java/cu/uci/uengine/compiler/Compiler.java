/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.compiler;

import cu.uci.uengine.compiler.exceptions.CompilationException;
import cu.uci.uengine.compiler.exceptions.CompilerException;
import cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException;
import java.io.IOException;

/**
 *
 * @author lan
 */
public interface Compiler {

    public boolean isCompilerAvailable(String language);

    public boolean compile(Compilable compilable) throws CompilerException,
            CompilationException, UnsupportedLanguageException, IOException, InterruptedException;
}
