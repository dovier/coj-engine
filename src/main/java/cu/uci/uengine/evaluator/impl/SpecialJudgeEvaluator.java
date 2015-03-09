/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.evaluator.impl;

import cu.uci.uengine.evaluator.Evaluable;
import cu.uci.uengine.evaluator.Evaluator;
import cu.uci.uengine.evaluator.EvaluatorResult;
import cu.uci.uengine.evaluator.TextFileComparator;
import cu.uci.uengine.utils.Utils;
import java.io.InputStream;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 *
 * @author lan
 */
@Component(value = "SpecialJudge")
public class SpecialJudgeEvaluator implements Evaluator {

    @Resource
    protected TextFileComparator comparator;

    @Override
    public EvaluatorResult evaluate(Evaluable answer) throws Exception {

        String command = answer.getCommand();

        // entrada
        command = command.replaceAll("<1>", answer.getInputFile().getAbsolutePath());
        // solucion correcta
        command = command.replaceAll("<2>", answer.getEvaluablePrototypeFilePath());
        // solucion del usuario
        command = command.replaceAll("<3>", answer.getEvaluableFilePath());

        String[] strings = command.trim().split(" ");
        ProcessBuilder pb = new ProcessBuilder(strings);
        Process process = pb.start();
        process.waitFor();

        if (process.exitValue() != 0) {
            return new EvaluatorResult(process.exitValue() == 200 ? EvaluatorResult.Result.ACCEPTED : EvaluatorResult.Result.WRONG_ANSWER);
        } else {
            InputStream inputStream = process.getErrorStream();
            String veredict = Utils.readInputStream(inputStream);
            return new EvaluatorResult(veredict.equals("Accepted") ? EvaluatorResult.Result.ACCEPTED : EvaluatorResult.Result.WRONG_ANSWER);
        }
    }
}
