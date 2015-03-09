/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.evaluator;

/**
 *
 * @author lan
 */
public class EvaluatorResult {

    private Result result;

    public EvaluatorResult(Result result) {
        this.result = result;
    }

    /**
     * @return the result
     */
    public Result getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(Result result) {
        this.result = result;
    }

    public enum Result {

        ACCEPTED, WRONG_ANSWER, PRESENTATION_ERROR
    }
}
