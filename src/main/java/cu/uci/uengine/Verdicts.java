package cu.uci.uengine;

public enum Verdicts {
        
        AC {
                    @Override
                    public String associatedMessage() {
                        return "Accepted";
                    }
                },
        WA {
                    @Override
                    public String associatedMessage() {
                        return "Wrong Answer";
                    }
                },
        RTE {
                    @Override
                    public String associatedMessage() {
                        return "Runtime Error";
                    }
                },
        TLE {
                    @Override
                    public String associatedMessage() {
                        return "Time Limit Exceeded";
                    }
                },
        MLE {
                    @Override
                    public String associatedMessage() {
                        return "Memory Limit Exceeded";
                    }
                },
        OLE {
                    @Override
                    public String associatedMessage() {
                        return "Output Limit Exceeded";
                    }
                },
        CE {
                    @Override
                    public String associatedMessage() {
                        return "Compilation Error";
                    }
                },
        IVF {
                    @Override
                    public String associatedMessage() {
                        return "Invalid Function";
                    }
                },
        PE {
                    @Override
                    public String associatedMessage() {
                        return "Presentation Error";
                    }
                },
        SIE {
                    @Override
                    public String associatedMessage() {
                        return "Internal Error";
                    }
                },
        CTLE {
                    @Override
                    public String associatedMessage() {
                        return "Case Time Limit Exceeded";
                    }
                };

        public abstract String associatedMessage();

    }
