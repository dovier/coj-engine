/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cu.uci.uengine.evaluator.exceptions;

/**
 *
 * @author lan
 */
public class EvaluationException extends Exception {

    /**
     * Creates a new instance of <code>EvaluationException</code> without detail
     * message.
     */
    public EvaluationException() {
    }

    /**
     * Constructs an instance of <code>EvaluationException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EvaluationException(String msg) {
        super(msg);
    }
}
