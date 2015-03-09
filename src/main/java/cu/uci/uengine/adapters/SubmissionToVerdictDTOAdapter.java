/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.adapters;

import cu.uci.uengine.model.Submission;
import cu.uci.uengine.model.dto.VerdictDTO;
import java.util.Date;

/**
 *
 * @author lan
 */
public class SubmissionToVerdictDTOAdapter extends VerdictDTO {

    public SubmissionToVerdictDTOAdapter(Submission submission) {
        this.submissionId = submission.getId();
        this.cid = submission.getContestId();
        this.verdict = submission.getVerdict();
        this.timeUsed = submission.getTimeUsed();
        this.cpuTimeUsed = submission.getCpuTimeUsed();
        this.memoryUsed = submission.getMemoryUsed();
        this.message = submission.getErrorMessage();
        this.acceptedDatasets = submission.getAcceptedDatasets();
        this.processedDatasets = submission.getProcessedDatasets();
        firstFailedDataset = getFirstFailedDataset(submission);
        this.minTimeUsed = submission.getMinTimeUsed();
        this.maxTimeUsed = submission.getMaxTimeUsed();
        this.averageTimeUsed = submission.getAverageTimeUsed();
    }

    private Integer getFirstFailedDataset(Submission submission) {
        if (submission.getFirstRunnerFailedDataset() != null && submission.getFirstEvaluationFailedDataset() != null) {
            return Math.min(submission.getFirstRunnerFailedDataset(), submission.getFirstEvaluationFailedDataset());
        } else {
            return submission.getFirstRunnerFailedDataset() != null ? submission.getFirstRunnerFailedDataset() : submission.getFirstEvaluationFailedDataset();
        }
    }
}
