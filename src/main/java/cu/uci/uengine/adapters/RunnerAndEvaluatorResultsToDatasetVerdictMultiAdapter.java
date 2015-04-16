/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.adapters;

import cu.uci.uengine.creators.VerdictFactory;
import cu.uci.uengine.evaluator.EvaluatorResult;
import cu.uci.uengine.model.dto.DatasetVerdictDTO;
import cu.uci.uengine.runner.RunnerResult;

/**
 *
 * @author lan
 */
public class RunnerAndEvaluatorResultsToDatasetVerdictMultiAdapter extends DatasetVerdictDTO {

    RunnerResult runnerResult;
    EvaluatorResult evaluatorResult;

    public RunnerAndEvaluatorResultsToDatasetVerdictMultiAdapter(RunnerResult runnerResult, EvaluatorResult evaluatorResult) {
        this.runnerResult = runnerResult;
        this.evaluatorResult = evaluatorResult;

        setMessage(runnerResult.getMessage());
        setUserTime(runnerResult.getUserTime());
        setCpuTime(runnerResult.getCpuTime());
        setMemory(runnerResult.getMemory());

        if (runnerResult.getResult() != RunnerResult.Result.OK) {
            this.setVerdict(VerdictFactory.create(runnerResult.getResult()));
        } else {
            this.setVerdict(VerdictFactory.create(evaluatorResult.getResult()));
        }
    }

}
