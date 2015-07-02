/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.adapters;

import cu.uci.coj.model.SubmissionJudge;
import cu.uci.uengine.model.Submission;
import java.util.HashMap;

/**
 *
 * @author lan
 */
@Deprecated
public class SubmissionJudgeToSubmissionAdapter extends Submission {

    private final SubmissionJudge submissionJudge;

    public SubmissionJudgeToSubmissionAdapter(SubmissionJudge submissionJudge) {
        super();
        this.submissionJudge = submissionJudge;

        this.id = submissionJudge.getSid();
        this.problemId = submissionJudge.getPid();
        this.metadata = new HashMap<>();
        this.metadata.put("Cid", submissionJudge.getCid());

        this.memoryLimit = (long) submissionJudge.getMemoryLimit();
        this.timeLimit = (long) submissionJudge.getTimeLimit();
        this.caseTimeLimit = (long) submissionJudge.getCaseTimeLimit();

        this.evaluationType = submissionJudge.isSpecialJudge() ? "SpecialJudge" : "PrototypeOutput";
        this.languageName = submissionJudge.getLang();
        this.sourceCode = submissionJudge.getSource();
        //TODO: @Lan-Hasta que venga el parámetro
        this.setTrusted(false);
        setAllResults(true);
    }

    public SubmissionJudge getSubmissionJudge() {
        return this.submissionJudge;
    }

}
