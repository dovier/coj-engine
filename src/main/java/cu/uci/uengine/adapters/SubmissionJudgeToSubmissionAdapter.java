/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.adapters;

import cu.uci.coj.model.SubmissionJudge;
import cu.uci.uengine.model.Submission;

/**
 *
 * @author lan
 */
public class SubmissionJudgeToSubmissionAdapter extends Submission {

    private final SubmissionJudge submissionJudge;

    public SubmissionJudgeToSubmissionAdapter(SubmissionJudge submissionJudge) {
        this.submissionJudge = submissionJudge;

        this.id = submissionJudge.getSid();
        this.problemId = submissionJudge.getPid();
        this.contestId = submissionJudge.getCid();
        
        this.memoryLimit = (long) submissionJudge.getMemoryLimit();
        this.timeLimit = (long) submissionJudge.getTimeLimit();
        this.caseTimeLimit = (long) submissionJudge.getCaseTimeLimit();

        this.evaluationType = submissionJudge.isSpecialJudge() ? "SpecialJudge" : "PrototypeOutput";
        this.languageName = submissionJudge.getLang();
        this.sourceCode = submissionJudge.getSource();

    }
    
    public SubmissionJudge getSubmissionJudge(){
        return this.submissionJudge;
    }

}
