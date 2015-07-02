/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.adapters;

import cu.uci.uengine.model.dto.SubmissionDTO;
import cu.uci.uengine.model.Submission;

/**
 *
 * @author lan
 */
public class SubmissionDTOToSubmissionAdapter extends Submission {

    private final SubmissionDTO submissionDTO;

    public SubmissionDTOToSubmissionAdapter(SubmissionDTO submissionDTO) {
        super();
        this.submissionDTO = submissionDTO;

        this.id = submissionDTO.getId();
        this.problemId = submissionDTO.getProblemId();
        this.metadata = submissionDTO.getMetadata();

        this.memoryLimit = submissionDTO.getMemoryLimit();
        this.timeLimit = submissionDTO.getTimeLimit();
        this.caseTimeLimit = submissionDTO.getCaseTimeLimit();

        this.evaluationType = submissionDTO.getEvaluationType();
        this.languageName = submissionDTO.getLanguage();
        this.sourceCode = submissionDTO.getSourceCode();
        this.trusted = submissionDTO.isTrusted()==null?false:submissionDTO.isTrusted();
        this.allResults = submissionDTO.isAllResults()==null?false:submissionDTO.isAllResults();
    }

}
