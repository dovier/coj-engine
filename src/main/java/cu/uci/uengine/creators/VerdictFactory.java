/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.creators;

import cu.uci.uengine.Verdicts;
import cu.uci.uengine.evaluator.EvaluatorResult;
import cu.uci.uengine.runner.RunnerResult;

/**
 *
 * @author lan
 */
public class VerdictFactory {

    public static Verdicts create(EvaluatorResult.Result evaluatorResult) {
        switch (evaluatorResult) {
            case AC:
                return Verdicts.AC;
            case WA:
                return Verdicts.WA;
            case PE:
                return Verdicts.PE;
            default:
                throw new AssertionError(evaluatorResult.name());
        }
    }

    public static Verdicts create(RunnerResult.Result runnerResult) {
        switch (runnerResult) {
            case OK:
                return Verdicts.AC;
            case RF:
                return Verdicts.IVF;
            case ML:
                return Verdicts.MLE;
            case OL:
                return Verdicts.OLE;
            case CTL:
                return Verdicts.CTLE;
            case TL:
                return Verdicts.TLE;
            case RT:
                return Verdicts.RTE;
            case AT:
            case PD:
            case IE:
            case BP:
                return Verdicts.SIE;
            default:
                throw new AssertionError(runnerResult.name());
        }
    }
}
