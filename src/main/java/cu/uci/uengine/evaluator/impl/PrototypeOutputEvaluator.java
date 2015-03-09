/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.evaluator.impl;

import cu.uci.uengine.evaluator.TextFileComparator;
import cu.uci.uengine.evaluator.Evaluable;
import cu.uci.uengine.evaluator.Evaluator;
import cu.uci.uengine.evaluator.EvaluatorResult;
import cu.uci.uengine.evaluator.TextFileComparator.ComparatorFlag;
import cu.uci.uengine.evaluator.exceptions.EvaluationException;
import java.util.EnumSet;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 *
 * @author lan
 */
@Component(value = "PrototypeOutput")
public class PrototypeOutputEvaluator implements Evaluator {

    @Resource
    protected TextFileComparator comparator;
    
    @Override
    public EvaluatorResult evaluate(Evaluable answer) throws EvaluationException{
        TextFileComparator.ComparatorResult comparatorResult = comparator.compare(answer.getEvaluableFilePath(), answer.getEvaluablePrototypeFilePath(), EnumSet.of(ComparatorFlag.IGNORE_TRAILING_SPACE));
        
        return fromComparatorResult(comparatorResult);
    }

    private EvaluatorResult fromComparatorResult(TextFileComparator.ComparatorResult comparatorResult) throws EvaluationException {
        switch (comparatorResult) {
            case EQUAL:
                return new EvaluatorResult(EvaluatorResult.Result.ACCEPTED);
            case DIFFERENT:
                return new EvaluatorResult(EvaluatorResult.Result.WRONG_ANSWER);
            case ERROR:
                throw new EvaluationException("There was an error with the evaluation process.");
        }
        return null;
    }
}
