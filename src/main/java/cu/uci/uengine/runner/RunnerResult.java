package cu.uci.uengine.runner;

/**
 *
 * @author lan
 */
public class RunnerResult {

    private Result result;

    private String message;
    private Long userTime;
    private Long cpuTime;
    private Long memory;

    public RunnerResult(Result result, String message, Long userTime, Long cpuTime, Long memory) {
        this.result = result;
        this.message = message;
        this.userTime = userTime;
        this.cpuTime = cpuTime;
        this.memory = memory;
    }

    public RunnerResult(Result result, Long userTime, Long cpuTime, Long memory) {
        this(result, result.associatedMessage(), userTime, cpuTime, memory);
    }

    public RunnerResult(Result result) {
        this(result, result.associatedMessage(), null, null, null);
    }

    public RunnerResult(Result result, String message) {
        this(result, message, null, null, null);
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Concatenates the specified string to the end of existing message.
     *
     * @param message
     */
    public void messageConcat(String message) {
        this.message = this.message.concat(message);
    }

    /**
     * @return the userTime
     */
    public Long getUserTime() {
        return userTime;
    }

    /**
     * @param userTime the userTime to set
     */
    public void setUserTime(Long userTime) {
        this.userTime = userTime;
    }

    /**
     * @return the cpuTime
     */
    public Long getCpuTime() {
        return cpuTime;
    }

    /**
     * @param cpuTime the cpuTime to set
     */
    public void setCpuTime(Long cpuTime) {
        this.cpuTime = cpuTime;
    }

    /**
     * @return the memory
     */
    public Long getMemory() {
        return memory;
    }

    /**
     * @param memory the memory to set
     */
    public void setMemory(Long memory) {
        this.memory = memory;
    }

    /**
     * @return the result
     */
    public Result getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(Result result) {
        this.result = result;
    }

    public enum Result {

        PD {
                    @Override
                    public String associatedMessage() {
                        return "Pending";
                    }
                },
        OK {
                    @Override
                    public String associatedMessage() {
                        return "Okay";
                    }
                },
        RF {
                    @Override
                    public String associatedMessage() {
                        return "Restricted Function";
                    }
                },
        ML {
                    @Override
                    public String associatedMessage() {
                        return "Memory Limit Exceed";
                    }
                },
        OL {
                    @Override
                    public String associatedMessage() {
                        return "Output Limit Exceed";
                    }
                },
        CTL {
                    @Override
                    public String associatedMessage() {
                        return "Case Time Limit Exceed";
                    }
                },
        TL {
                    @Override
                    public String associatedMessage() {
                        return "Time Limit Exceed";
                    }
                },
        RT {
                    @Override
                    public String associatedMessage() {
                        return "Runtime Error";
                    }
                },
        AT {
                    @Override
                    public String associatedMessage() {
                        return "Abnormal Termination";
                    }
                },
        IE {
                    @Override
                    public String associatedMessage() {
                        return "Internal Error";
                    }
                },
        BP {
                    @Override
                    public String associatedMessage() {
                        return "Bad Policy";
                    }
                };

        public abstract String associatedMessage();

    }

}
