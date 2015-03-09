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
public class UnsupportedLanguageException extends Exception {

    /**
     * Creates a new instance of <code>UnsupportedLanguageException</code>
     * without detail message.
     */
    public UnsupportedLanguageException() {
    }

    /**
     * Constructs an instance of <code>UnsupportedLanguageException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public UnsupportedLanguageException(String msg) {
        super(msg);
    }
}
