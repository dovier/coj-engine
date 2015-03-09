/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cu.uci.uengine.exceptions;

/**
 *
 * @author lan
 */
public class InvalidDataSetNameException extends Exception {

    /**
     * Creates a new instance of <code>InvalidDataSetNameException</code>
     * without detail message.
     */
    public InvalidDataSetNameException() {
    }

    /**
     * Constructs an instance of <code>InvalidDataSetNameException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidDataSetNameException(String msg) {
        super(msg);
    }
}
