package cu.uci.uengine.compiler.impl;

import cu.uci.uengine.compiler.Compilable;
import cu.uci.uengine.compiler.Compiler;
import cu.uci.uengine.compiler.exceptions.CompilerException;
import cu.uci.uengine.compiler.exceptions.CompilationException;
import cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException;
import cu.uci.uengine.languages.Languages;
import cu.uci.uengine.utils.FileUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author lan Precondition: There have to be a file /languages.properties with
 a coma separated list of languages with key language.list and a key
 {lang}.compile with the compilation command. Example:
 C++.compile=/usr/bin/g++ -g -O2 -static-libstdc++ <SRC> -o <EXE>
 */
@Component
public class CLICompiler implements Compiler {

    static Log log = LogFactory.getLog(CLICompiler.class.getName());
    private HashMap<String, String> commands;

    @Resource
    Languages languages;

    @PostConstruct
    private void loadLanguages() throws IOException {
        Properties langProps = new Properties();
        langProps.load(ClassLoader
                .getSystemResourceAsStream("languages.properties"));

        String[] languagesConfig = langProps.getProperty("language.list").split(",");
        commands = new HashMap();

        for (String language : languagesConfig) {

            language = language.trim();

            String compileCmd = langProps.getProperty(language + ".compile").trim();

            if (!StringUtils.isEmpty(compileCmd)) {
                commands.put(language, compileCmd);
            }
        }

    }

    @Override
    public boolean isCompilerAvailable(String language) {
        return commands.containsKey(language);
    }

    @Override
    public boolean compile(Compilable compilable) throws CompilerException,
            CompilationException, UnsupportedLanguageException, IOException, InterruptedException {

        checkCompilable(compilable);

        ProcessBuilder processBuilder = getProcessBuilder(compilable.getLanguageName(),
                getSourceFilePath(compilable),
                getExecutableFilePath(compilable));

        try {
            Process process = processBuilder.start();
            process.waitFor();
            if (process.exitValue() != 0) {
                BufferedReader bis = new BufferedReader(
                        new InputStreamReader(new BufferedInputStream(
                                        process.getInputStream())));
                StringBuilder sb = new StringBuilder();
                String temp = null;
                while ((temp = bis.readLine()) != null) {
                    sb.append(temp);
                }
                //TODO: @Lan - esto no debería ser una excepción, valorar abstraer también la salida del compilador y establecer esto como una compilación correcta pero qeu dio un resultado fallido.
                throw new CompilationException(sb.toString());
            }
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
            throw new CompilerException(e.getMessage());
        }
        return true;
    }

    private ProcessBuilder getProcessBuilder(String language, String sourceFilePath, String executablePath) {
        String command = commands.get(language);
        command = command.replace("<SRC>", sourceFilePath);
        command = command.replace("<EXE>", executablePath);
        command = command.replaceAll("<EXEPATH>", executablePath);
        // esto es importante porque el compilador de C/C++ genera un nombre
        // de ejecutable con espacios en blanco al final
        String[] strings = command.trim().split(" ");
        ProcessBuilder pb = new ProcessBuilder(strings);
        // combina error y output
        pb.redirectErrorStream(true);
        return pb;
    }

    private String getSourceFilePath(Compilable compilable) throws IOException, InterruptedException {
        if (compilable.getSourceFile() == null) {
            String fileName = String.format("%s.%s", Long.valueOf(compilable.getId()), languages.getExtension(compilable.getLanguageName()));
            return FileUtils.writeStringToFile(fileName, compilable.getTemporaryDirectory(), compilable.getSourceCode(), true);
        } else {
            return compilable.getSourceFile().getAbsolutePath();
        }
    }

    private String getExecutableFilePath(Compilable compilable) throws IOException, InterruptedException {
        if (compilable.getExecutablePath() == null) {
            String fileName = FilenameUtils.getBaseName(getSourceFilePath(compilable));
            String executablePath = new File(compilable.getTemporaryDirectory(), fileName).getAbsolutePath();

            compilable.setExecutablePath(executablePath);

            return executablePath;
        } else {
            return compilable.getExecutablePath();
        }
    }

    private void checkCompilable(Compilable compilable) throws UnsupportedLanguageException {
        if (!commands.containsKey(compilable.getLanguageName())) {
            String message = "The language you are requesting to compile isn't available. Configure it first.";
            log.error(message);
            throw new UnsupportedLanguageException("The language you are requesting to compile isn't available. Configure it first.");
        }
    }

}
