/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.adapters;

import cu.uci.uengine.model.Submission;
import cu.uci.uengine.model.dto.DatasetVerdictDTO;
import cu.uci.uengine.model.dto.VerdictDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lan
 */
public class SubmissionToVerdictDTOAdapter extends VerdictDTO {

    public SubmissionToVerdictDTOAdapter(Submission submission) {
        this.id = submission.getId();
        this.problemId = submission.getProblemId();
        this.metadata = submission.getMetadata();
        this.verdict = submission.getVerdict();
        this.timeUsed = submission.getTimeUsed();
        this.cpuTimeUsed = submission.getCpuTimeUsed();
        this.memoryUsed = submission.getMemoryUsed();
        this.message = submission.getErrorMessage();
        this.acceptedDatasets = submission.getAcceptedDatasets();
        this.processedDatasets = submission.getProcessedDatasets();
        this.firstFailedDataset = getFirstFailedDataset(submission);
        this.minTimeUsed = submission.getMinTimeUsed();
        this.maxTimeUsed = submission.getMaxTimeUsed();
        this.averageTimeUsed = submission.getAverageTimeUsed();

        if (submission.isAllResults()) {
            List<DatasetVerdictDTO> datasetVerdictDTOs = getDatasetVerdicts(submission);

            setDatasetVerdictDTO(datasetVerdictDTOs);
        }
        this.evaluationDate = new Date();
    }

    private List<DatasetVerdictDTO> getDatasetVerdicts(Submission submission) {
        List<DatasetVerdictDTO> datasetVerdictDTOs = new ArrayList<>();
        for (int i = 0; i < submission.getProcessedDatasets(); i++) {
            DatasetVerdictDTO datasetVerdictDTO = new RunnerAndEvaluatorResultsToDatasetVerdictMultiAdapter(submission.getRunnerResults().get(i), submission.getEvaluatorResults().get(i));
            datasetVerdictDTOs.add(datasetVerdictDTO);
        }
        return datasetVerdictDTOs;
    }

    private Integer getFirstFailedDataset(Submission submission) {
        if (submission.getFirstRunnerFailedDataset() != null && submission.getFirstEvaluationFailedDataset() != null) {
            return Math.min(submission.getFirstRunnerFailedDataset(), submission.getFirstEvaluationFailedDataset());
        } else {
            return submission.getFirstRunnerFailedDataset() != null ? submission.getFirstRunnerFailedDataset() : submission.getFirstEvaluationFailedDataset();
        }
    }
}
