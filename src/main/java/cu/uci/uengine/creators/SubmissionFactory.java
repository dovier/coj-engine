/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.creators;

import cu.uci.coj.model.SubmissionJudge;
import cu.uci.uengine.adapters.SubmissionDTOToSubmissionAdapter;
import cu.uci.uengine.adapters.SubmissionJudgeToSubmissionAdapter;
import cu.uci.uengine.model.dto.SubmissionDTO;
import cu.uci.uengine.model.Submission;
import org.springframework.stereotype.Component;

/**
 *
 * @author lan
 */
@Component
public class SubmissionFactory {

    public static Submission create(String type, Object extraData) {
        switch (type) {
            case "Submission":
                return create(extraData);
        }

        return null;
    }

    private static Submission create(Object extraData) {

        if (SubmissionDTO.class.isInstance(extraData)) {
            return new SubmissionDTOToSubmissionAdapter((SubmissionDTO) extraData);
        }

        if (SubmissionJudge.class.isInstance(extraData)) {
            return new SubmissionJudgeToSubmissionAdapter((SubmissionJudge) extraData);
        }
        return null;
    }

}
