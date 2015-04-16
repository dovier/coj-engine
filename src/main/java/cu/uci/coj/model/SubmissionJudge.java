package cu.uci.coj.model;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import cu.uci.uengine.Language;
import cu.uci.uengine.Verdicts;
import cu.uci.uengine.evaluator.EvaluatorResult;
import java.util.List;

public class SubmissionJudge implements Comparable<SubmissionJudge>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6506777204076530661L;
	// argument attributes
	private int sid;
	private int pid;
	private int uid;
	private int timeLimit;
	private int caseTimeLimit;
	private long memoryLimit;
	private String lang;
	private int cid;
	private int timeUsed;
	private int cpuTimeUsed;
	private String errMsg;
	private long memoryUsed;
	private String source;
	private String status;
	private int acTestCases;
	private int totalTestCases;
	private int firstWaCase;
	private int minTimeUsed;
	private int maxTimeUsed;
	private Date date;
	private boolean specialJudge;

	private int avgTimeUsed;

	// work attributes
	private Language language;
	private Verdicts verdict;
	private File tmpDirSid;
	private File execFile;
	private File sourceFile;
        
        private List<EvaluatorResult> evaluatorResults;
	
	private boolean accepted;

	
	public boolean isSpecialJudge() {
		return specialJudge;
	}

	public void setSpecialJudge(boolean specialJudge) {
		this.specialJudge = specialJudge;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public File getTmpDirSid() {
		return tmpDirSid;
	}

	public void setTmpDirSid(File tmpDirSid) {
		this.tmpDirSid = tmpDirSid;
	}

	public File getExecFile() {
		return execFile;
	}

	public void setExecFile(File execFile) {
		this.execFile = execFile;
	}

	public void createExecFile(String filename) {
		execFile = new File(tmpDirSid, filename);
	}

	public File getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}

	public void createSourceFile(String filename) {
		this.sourceFile = new File(tmpDirSid, filename + "."
				+ language.getExt());
	}

	/*
	 * Esto se usa para decidir entre WA, PE y AC en el Comparator despues de
	 * los diff. Si el verdict actual es AC, esta es la minima prioridad y se
	 * sustituye por lo que sea que venga. Si es PE y lo que viene es WA, se
	 * sustituye, sino no se hace nada. Si es WA no se hace nada, ya que esta es
	 * la mayor prioridad de las 3 y no puede ser sustituida
	 */
	public void priorityVerdict(Verdicts verdict) {
		if (verdict == Verdicts.WA)
			this.verdict = Verdicts.WA;
		else if (this.verdict == null || this.verdict == Verdicts.AC)
			this.verdict = verdict;
	}

	public void setResponseValues(int totalUserTime, int totalCPUTime,
			long memoryUsed, int minUserTime, int maxUserTime,
			int inDataFilesCount) {
		this.timeUsed = totalUserTime;
		this.cpuTimeUsed = totalCPUTime;
		this.memoryUsed = memoryUsed;
		this.minTimeUsed = minUserTime;
		this.maxTimeUsed = maxUserTime;
		this.totalTestCases = inDataFilesCount;
		// Este max es solo para que no explote si se da el caso de que se usa
		// en un problema que no tiene juegos de datos.
		// ESTO NO DEBE SUCEDER, un problema sin juegos de datos es un error
		// grave del sistema.
		this.avgTimeUsed = timeUsed / Math.max(totalTestCases, 1);
	}

	public SubmissionJudge() {
		firstWaCase = 0;
		verdict = null;
	}

	public boolean isContest() {
		return cid == 0;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Verdicts getVerdict() {
		return verdict;
	}

	public void setVerdict(Verdicts verdict) {
		this.verdict = verdict;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTimeUsed() {
		return timeUsed;
	}

	public void setTimeUsed(int timeUsed) {
		this.timeUsed = timeUsed;
	}

	public int getCpuTimeUsed() {
		return cpuTimeUsed;
	}

	public void setCpuTimeUsed(int cpuTimeUsed) {
		this.cpuTimeUsed = cpuTimeUsed;
	}

	public long getMemoryUsed() {
		return memoryUsed;
	}

	public void setMemoryUsed(long memoryUsed) {
		this.memoryUsed = memoryUsed;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public int getAcTestCases() {
		return acTestCases;
	}

	public void setAcTestCases(int acTestCases) {
		this.acTestCases = acTestCases;
	}

	public int getTotalTestCases() {
		return totalTestCases;
	}

	public void setTotalTestCases(int totalTestCases) {
		this.totalTestCases = totalTestCases;
	}

	public int getFirstWaCase() {
		return firstWaCase;
	}

	public void setFirstWaCase(int firstWaCase) {
		this.firstWaCase = firstWaCase;
	}

	public int getMinTimeUsed() {
		return minTimeUsed;
	}

	public void setMinTimeUsed(int minTimeUsed) {
		this.minTimeUsed = minTimeUsed;
	}

	public int getMaxTimeUsed() {
		return maxTimeUsed;
	}

	public void setMaxTimeUsed(int maxTimeUsed) {
		this.maxTimeUsed = maxTimeUsed;
	}

	public int getAvgTimeUsed() {
		return avgTimeUsed;
	}

	public void setAvgTimeUsed(int avgTimeUsed) {
		this.avgTimeUsed = avgTimeUsed;
	}

	public int getCaseTimeLimit() {
		return caseTimeLimit;
	}

	public void setCaseTimeLimit(int caseTimeLimit) {
		this.caseTimeLimit = caseTimeLimit;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public long getMemoryLimit() {
		return memoryLimit;
	}

	public void setMemoryLimit(long memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SID:").append(sid).append(" ");
		sb.append("CONTEST:").append(cid).append(" ");
		sb.append("STATUS:").append(status).append(" ");
		sb.append("TIME USED:").append(timeUsed).append(" ");
		sb.append("CPU TIME USED:").append(cpuTimeUsed).append(" ");
		sb.append("MEMORY USED:").append(memoryUsed).append(" ");
		sb.append("AC TEST CASES:").append(acTestCases).append(" ");
		sb.append("TOTAL TEST CASES:").append(totalTestCases).append(" ");
		sb.append("MIN TIME:").append(minTimeUsed).append(" ");
		sb.append("MAX TIME:").append(maxTimeUsed).append(" ");
		sb.append("AVG TIME:").append(avgTimeUsed).append(" ");
		return sb.toString();
	}

	@Override
	public int compareTo(SubmissionJudge other) {
		// asegura que los envios en contest tengan prioridad sobre los envios
		// en 24h
		return (this.cid != 0 ? 1 : 0) - (other.cid != 0 ? 1 : 0);
	}

    /**
     * @return the evaluatorResults
     */
    public List<EvaluatorResult> getEvaluatorResults() {
        return evaluatorResults;
    }

    /**
     * @param evaluatorResults the evaluatorResults to set
     */
    public void setEvaluatorResults(List<EvaluatorResult> evaluatorResults) {
        this.evaluatorResults = evaluatorResults;
    }

}