package cu.uci.uengine;

import cu.uci.uengine.compiler.Compiler;
import cu.uci.uengine.compiler.exceptions.CompilationException;
import cu.uci.uengine.compiler.exceptions.ServerInternalException;
import cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cu.uci.uengine.model.SubmissionJudge;
import cu.uci.uengine.runnable.SubmitRunner;
import cu.uci.uengine.utils.Utils;
import static cu.uci.uengine.utils.Utils.dos2unixFileFixer;
import static cu.uci.uengine.utils.Utils.saveSourceFile;

@Component
public class EngineManager {

    static Log log = LogFactory.getLog(SubmitRunner.class.getName());
    @Resource
    private Compiler compiler;
    @Resource
    private Runner runner;
    @Resource
    private StandardComparator comparator;
    @Resource
    private TextComparator textComparator;
    @Resource
    private SpecialJudgeComparator specialJudgeComparator;
    @Autowired
    private Properties properties;
    private File tmpDirFile;
    private File intDirFile;

    @PostConstruct
    private void init() {
        // carpeta para el trabajo con los envios
        this.tmpDirFile = new File(System.getProperty("user.dir"), "tmp");
        if (!this.tmpDirFile.exists()) {
            this.tmpDirFile.mkdirs();
        }
        // carpeta para almacenar las interrupciones de los codigos. Esto se
        // debe eliminar o comentar luego de la migracion
        this.intDirFile = new File(System.getProperty("user.dir"), "int");
        if (!this.intDirFile.exists()) {
            this.intDirFile.mkdirs();
        }

    }

            
    public SubmissionJudge call(SubmissionJudge submit) {

        try {
            File problemDir = new File(this.properties.getProperty("problems.dir"),String.valueOf(submit.getPid()));
            
            // crear la carpeta de trabajo del envio.
            submit.setTmpDirSid(new File(this.tmpDirFile, String.valueOf(submit
                    .getSid())));
            submit.getTmpDirSid().mkdir();
            
            File langIntDirFile = new File(this.intDirFile, submit.getLang());

            if (!langIntDirFile.exists()) {
                langIntDirFile.mkdir();
            }

            String filename = String.valueOf(submit.getSid());

            submit.createSourceFile(filename);

            Utils.saveSourceFile(submit.getSourceFile(), submit.getSource());
            Utils.dos2unixFileFixer(submit.getSourceFile().getAbsolutePath());

            submit.createExecFile(filename);
            
            compile(submit);
            
            submit = generateOutput(submit, problemDir, langIntDirFile);   
            
            evaluate(submit, problemDir);
            
            
        } catch (IOException e) {
            submit.setVerdict(Verdicts.SIE);
            submit.setErrMsg(e.getMessage());
            log.error(e.getMessage());
        } catch (InterruptedException e) {
            submit.setVerdict(Verdicts.SIE);
            submit.setErrMsg(e.getMessage());
            log.error(e.getMessage());
        } catch (Exception e) {
            submit.setVerdict(Verdicts.SIE);
            submit.setErrMsg(e.getMessage());
            log.error(e.getMessage());
        } finally {
            // eliminar la carpeta de trabajo del envio
            if (submit.getTmpDirSid() != null) {
                FileUtils.deleteQuietly(submit.getTmpDirSid());
            }
        }
        return submit;
    }

    private void evaluate(SubmissionJudge submit, File problemDir) throws IOException, InterruptedException {
        if (submit.getVerdict() == null) {
            if (submit.isSpecialJudge()) {
                specialJudgeComparator.compare(submit, problemDir);
            } else if ("Text".equals(submit.getLang())) {
                textComparator.compare(submit, problemDir);
            } else {
                comparator.compare(submit, problemDir);
            }
        }
    }

    private SubmissionJudge generateOutput(SubmissionJudge submit, File problemDir, File langIntDirFile) throws NumberFormatException {
        if (!StringUtils.isEmpty(submit.getLanguage().getExecCmd())) {
            
            submit = runner.run(submit, Long.valueOf(this.properties
                    .getProperty("output.limit")), Long
                            .valueOf(this.properties.getProperty("memory.limit")),
                    submit.getLanguage().getTimeMultiplier(), submit
                            .getLanguage().getMemoryMultiplier(),
                    problemDir, langIntDirFile);
        }
        return submit;
    }
    
    private boolean autoFix(SubmissionJudge submit) throws IOException, InterruptedException {
        switch (submit.getLang()){
            case "Java":
                return Utils.fixJavaName(submit); 
        }
        return false;         
    }

    private void compile(SubmissionJudge submit) throws Exception {
        boolean isCompiled = false, isFixed = false;
        
        try {
            isCompiled = compiler.compile(submit.getLanguage().getName(), submit.getSourceFile().getAbsolutePath(), submit.getExecFile().getAbsolutePath());
        } catch (ServerInternalException | CompilationException ex) {
            submit.setErrMsg(ex.getMessage());
            isFixed = autoFix(submit);
        }
        
        if (isFixed) {
            isCompiled = compiler.compile(submit.getLanguage().getName(), submit.getSourceFile().getAbsolutePath(), submit.getExecFile().getAbsolutePath());
        }
        
        if (!isCompiled) {
            throw new Exception("source code can not be compiled");
        }        
    }
    
}
