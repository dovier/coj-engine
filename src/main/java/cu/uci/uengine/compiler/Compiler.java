package cu.uci.uengine.compiler;

import cu.uci.uengine.compiler.exceptions.ServerInternalException;
import cu.uci.uengine.compiler.exceptions.CompilationException;
import cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import cu.uci.uengine.runnable.SubmitRunner;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.accessibility.AccessibleRole;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lan Precondition: There have to be a file /languages.properties with
 * a coma separated list of languages with key language.list and a key
 * {lang}.compile with the compilation command. Example:
 * C++.compile=/usr/bin/g++ -g -O2 -static-libstdc++ <SRC> -o <EXE>
 */
@Component
public class Compiler {

    @Autowired
    private Properties properties;

    static Log log = LogFactory.getLog(Compiler.class.getName());
    private HashMap<String, String> commands;

    private String languageNameProcessor(String language) {
        return language.trim();
    }

    @PostConstruct
    private void loadLanguages() throws IOException {
        Properties langProps = new Properties();
        langProps.load(ClassLoader
                .getSystemResourceAsStream("languages.properties"));

        String[] languages = langProps.getProperty("language.list").split(",");
        commands = new HashMap<>();

        for (String language : languages) {

            language = languageNameProcessor(language);

            String compileCmd = langProps.getProperty(language + ".compile");

            if (!StringUtils.isEmpty(compileCmd.trim())) {
                commands.put(language, compileCmd);
            }
        }

    }

    public boolean isCompilerAvailable(String language) {
        return commands.containsKey(language);
    }

    public boolean compile(String language, String sourceCodePath,
            String exePath) throws ServerInternalException,
            CompilationException, UnsupportedLanguageException {

        if (!commands.containsKey(language)) {
            String message = "The language you are requesting to compile isn't available. Configure it first.";
            log.error(message);
            throw new UnsupportedLanguageException("The language you are requesting to compile isn't available. Configure it first.");
        }

        String command = commands.get(language);

        command = command.replace("<SRC>", sourceCodePath);
        command = command.replace("<EXE>", exePath);
        command = command.replaceAll("<EXEPATH>", exePath);
        // esto es importante porque el compilador de C/C++ genera un nombre
        // de ejecutable con espacios en blanco al final
        String[] strings = command.trim().split(" ");
        ProcessBuilder pb = new ProcessBuilder(strings);
        // combina error y output
        pb.redirectErrorStream(true);

        try {
            final Process process = pb.start();

            //This is because of limit on Compiler Execution Time
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        process.waitFor();
                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                    }
                }
            });

            thread.start();
            final long compilationTimeLimit = Long.valueOf(properties.getProperty("compiler.limit.time"));
            thread.join(compilationTimeLimit);

            try {
                if (process.exitValue() != 0) {
                    BufferedReader bis = new BufferedReader(
                            new InputStreamReader(new BufferedInputStream(
                                            process.getInputStream())));
                    StringBuilder sb = new StringBuilder();
                    String temp = null;
                    while ((temp = bis.readLine()) != null) {
                        sb.append(temp);
                    }
                    throw new CompilationException(sb.toString());
                }
            } catch (IllegalThreadStateException illegalThreadStateException) {//Caused by process.exitValue() if the subprocess represented by this Process object has not yet terminated 
                throw new CompilationException("Compilation Time Limit Exceeded");
            }
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
            throw new ServerInternalException(e.getMessage());
        }
        return true;
    }
}
