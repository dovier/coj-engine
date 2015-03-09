/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.adapters;

import com.sun.corba.se.impl.util.JDKBridge;
import cu.uci.coj.model.SubmissionJudge;
import cu.uci.uengine.Verdicts;
import cu.uci.uengine.model.Submission;
import java.util.Date;

/**
 *
 * @author lan
 */
public class SubmissionToSubmissionJudgeAdapter extends SubmissionJudge {

    public SubmissionToSubmissionJudgeAdapter(Submission submission) {
        setSid(submission.getId());
        
        setVerdict(submission.getVerdict());
        setTimeUsed(submission.getTimeUsed()==null?0:submission.getTimeUsed().intValue());
        setCpuTimeUsed(submission.getCpuTimeUsed() ==null?0:submission.getCpuTimeUsed().intValue());
        
        setMemoryUsed(submission.getMemoryUsed()==null?0:submission.getMemoryUsed());
        setErrMsg(submission.getErrorMessage());
        setAcTestCases(submission.getAcceptedDatasets());
        setTotalTestCases(submission.getProcessedDatasets());
        setFirstWaCase(getFirstFailedDataset(submission)==null?0:getFirstFailedDataset(submission));
        setMinTimeUsed(submission.getMinTimeUsed()==null?0:submission.getMinTimeUsed().intValue());
        setMaxTimeUsed(submission.getMaxTimeUsed()==null?0:submission.getMaxTimeUsed().intValue());
        setAvgTimeUsed(submission.getAverageTimeUsed()==null?0:submission.getAverageTimeUsed().intValue());
        
        //Extras
        setCid(submission.getContestId());
        setLang(submission.getLanguageName());        
        setAccepted(submission.getVerdict()==Verdicts.AC);
        setStatus(submission.getVerdict().associatedMessage());
        setPid(submission.getProblemId());
        setDate(new Date());
        
        //Pieeee
        SubmissionJudge submissionJudge = ((SubmissionJudgeToSubmissionAdapter)submission).getSubmissionJudge();
        setUid(submissionJudge.getUid());
    }

    private Integer getFirstFailedDataset(Submission submission) {
        if (submission.getFirstRunnerFailedDataset() != null && submission.getFirstEvaluationFailedDataset() != null) {
            return Math.min(submission.getFirstRunnerFailedDataset(), submission.getFirstEvaluationFailedDataset());
        } else {
            return submission.getFirstRunnerFailedDataset() != null ? submission.getFirstRunnerFailedDataset() : submission.getFirstEvaluationFailedDataset();
        }
    }
}
