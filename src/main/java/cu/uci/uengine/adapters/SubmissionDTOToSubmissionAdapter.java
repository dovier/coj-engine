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

    private SubmissionDTO submissionDTO;

    public SubmissionDTOToSubmissionAdapter(SubmissionDTO submissionDTO) {
        this.submissionDTO = submissionDTO;
    }

}
