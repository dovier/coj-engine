/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cu.uci.uengine.compiler.exceptions;

/**
 *
 * @author lan
 */
public class CompilationException extends Exception {

    /**
     * Creates a new instance of <code>CompilationException</code> without
     * detail message.
     */
    public CompilationException() {
    }

    /**
     * Constructs an instance of <code>CompilationException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CompilationException(String msg) {
        super(msg);
    }
}
