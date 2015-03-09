/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cu.uci.uengine.runner.exceptions;

/**
 *
 * @author lan
 */
public class InvalidDataSetsFormatException extends Exception {

    /**
     * Creates a new instance of <code>InvalidDataSetsFormat</code> without
     * detail message.
     */
    public InvalidDataSetsFormatException() {
    }

    /**
     * Constructs an instance of <code>InvalidDataSetsFormat</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidDataSetsFormatException(String msg) {
        super(msg);
    }
}
