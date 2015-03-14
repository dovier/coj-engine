package cu.uci.uengine;

/**
     * @deprecated
     * This class must be removed as soon as SubmissionJudge
     */
@Deprecated
public class Language {
	private String name;
	private String ext;
	private String compileCmd;
	private String execCmd;
	private int timeMultiplier;
	private int memoryMultiplier;
        private int retries;

        public int getRetries() {
            return retries;
        }

        public void setRetries(int retries) {
            this.retries = retries;
        }	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getCompileCmd() {
		return compileCmd;
	}
	public void setCompileCmd(String compileCmd) {
		this.compileCmd = compileCmd;
	}
	public String getExecCmd() {
		return execCmd;
	}
	public void setExecCmd(String execCmd) {
		this.execCmd = execCmd;
	}
	public int getTimeMultiplier() {
		return timeMultiplier;
	}
	public void setTimeMultiplier(int timeMultiplier) {
		this.timeMultiplier = timeMultiplier;
	}
	public int getMemoryMultiplier() {
		return memoryMultiplier;
	}
	public void setMemoryMultiplier(int memoryMultiplier) {
		this.memoryMultiplier = memoryMultiplier;
	}
	

	
	
}
