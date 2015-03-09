/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.evaluator;

import cu.uci.uengine.evaluator.exceptions.EvaluationException;

/**
 *
 * @author lan
 */
public interface Evaluator {

    public EvaluatorResult evaluate(Evaluable answer) throws Exception;
    
}    
