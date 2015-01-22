package cu.uci.uengine;

import java.io.File;

import org.springframework.stereotype.Component;

import cu.uci.uengine.model.SubmissionJudge;

@Component
public class StandardComparator extends ComparatorBase{

	public File getTmpOutFile(SubmissionJudge submit,String name){
		return new File(submit.getTmpDirSid(), name + ".out");
	}
}
