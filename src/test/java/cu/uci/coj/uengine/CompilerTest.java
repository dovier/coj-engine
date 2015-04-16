package cu.uci.coj.uengine;

import cu.uci.uengine.compiler.Compilable;
import cu.uci.uengine.compiler.exceptions.CompilationException;
import cu.uci.uengine.compiler.exceptions.CompilerException;
import cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException;
import cu.uci.uengine.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import cu.uci.uengine.compiler.Compiler;

/**
 * Unit test for CompilerTest.
 */
public class CompilerTest
        extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CompilerTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(CompilerTest.class);
    }

    /**
     * Rigourous Test :-)
     *
     * @throws java.lang.InterruptedException
     * @throws cu.uci.uengine.compiler.exceptions.CompilerException
     * @throws cu.uci.uengine.compiler.exceptions.CompilationException
     * @throws cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException
     * @throws java.io.IOException
     */
    public void testTextCompilation() throws InterruptedException, CompilerException, CompilationException, UnsupportedLanguageException, IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext("cu.uci.uengine.compiler","cu.uci.uengine.languages");
        Compiler compiler = appCtx.getBean(Compiler.class);

        assertEquals("Text can't be compiled", compiler.isCompilerAvailable("Text"), false);
    }

    /**
     * Rigourous Test :-)
     *
     * @throws java.lang.InterruptedException
     * @throws cu.uci.uengine.compiler.exceptions.CompilerException
     * @throws cu.uci.uengine.compiler.exceptions.CompilationException
     * @throws cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException
     * @throws java.io.IOException
     */
    public void testCCompilation() throws InterruptedException, CompilerException, CompilationException, UnsupportedLanguageException, IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext("cu.uci.uengine.compiler","cu.uci.uengine.languages");
        String sourceCode = "#include <stdio.h>\nint main(){\nint a,b;\nscanf(\"%d %d\",&a,&b);\nprintf(\"%d\",a+b);\nreturn 0;\n}";

        Compilable compilable = new MockCompilable(1, sourceCode, "C", "c", FileUtils.getTempDirectory());
        Compiler compiler = appCtx.getBean(Compiler.class);

        boolean compile = compiler.compile(compilable);
        assertEquals(compile, true);
    }

    /**
     * Rigourous Test :-)
     *
     * @throws java.lang.InterruptedException
     * @throws cu.uci.uengine.compiler.exceptions.CompilerException
     * @throws cu.uci.uengine.compiler.exceptions.CompilationException
     * @throws cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException
     * @throws java.io.IOException
     */
    public void testCCompilationError() throws InterruptedException, CompilerException, CompilationException, UnsupportedLanguageException, IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext("cu.uci.uengine.compiler","cu.uci.uengine.languages");
        String sourceCode = "#include <stdio.h>\nit main(){\nint a,b;\nscanf(\"%d %d\",&a,&b);\nprintf(\"%d\",a+b);\nreturn 0;\n}";

        Compilable compilable = new MockCompilable(1, sourceCode, "C", "c", FileUtils.getTempDirectory());
        Compiler compiler = appCtx.getBean(Compiler.class);

        boolean compile = false;
        try {
            compile = compiler.compile(compilable);
        } catch (CompilerException | CompilationException | UnsupportedLanguageException | IOException | InterruptedException compilerException) {

        }
        assertFalse("Compilation Error Expected", compile);
    }

    /**
     * Rigourous Test :-)
     *
     * @throws java.lang.InterruptedException
     * @throws cu.uci.uengine.compiler.exceptions.CompilerException
     * @throws cu.uci.uengine.compiler.exceptions.CompilationException
     * @throws cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException
     * @throws java.io.IOException
     */
    public void testJavaCompilation() throws InterruptedException, CompilerException, CompilationException, UnsupportedLanguageException, IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext("cu.uci.uengine.compiler","cu.uci.uengine.languages");
        String sourceCode = "import java.util.Scanner;\r\npublic class AmasB {\r\n\r\n    public static void main(String[] args) {\r\n     \r\n     int a,b;\r\n        Scanner numero = new Scanner(System.in);\r\n     \r\n       a = numero.nextInt(); \r\n       b = numero.nextInt()+a;  \r\n\r\n     \r\n        System.out.println( b );\r\n    }\r\n}";
        File sourceFile = new File(FileUtils.writeStringToFile("AmasB.java", FileUtils.getTempDirectory(), sourceCode, false));

        Compilable compilable = new MockCompilable(1, sourceFile, "Java", FileUtils.getTempDirectory());
        Compiler compiler = appCtx.getBean(Compiler.class);

        boolean compile = compiler.compile(compilable);
        assertEquals(compile, true);
    }

    /**
     * Rigourous Test :-)
     *
     * @throws java.lang.InterruptedException
     * @throws cu.uci.uengine.compiler.exceptions.CompilerException
     * @throws cu.uci.uengine.compiler.exceptions.CompilationException
     * @throws cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException
     * @throws java.io.IOException
     */
    public void testJavaCompilationError() throws InterruptedException, CompilerException, CompilationException, UnsupportedLanguageException, IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext("cu.uci.uengine.compiler","cu.uci.uengine.languages");
        String sourceCode = "import java.ut.Scanner;\r\npublic class AmasB {\r\n\r\n    public static void main(String[] args) {\r\n     \r\n     int a,b;\r\n        Scanner numero = new Scanner(System.in);\r\n     \r\n       a = numero.nextInt(); \r\n       b = numero.nextInt()+a;  \r\n\r\n     \r\n        System.out.println( b );\r\n    }\r\n}";
        File sourceFile = new File(FileUtils.writeStringToFile("AmasB.java", FileUtils.getTempDirectory(), sourceCode, false));

        Compilable compilable = new MockCompilable(1, sourceFile, "Java", FileUtils.getTempDirectory());
        Compiler compiler = appCtx.getBean(Compiler.class);

        boolean compile = false;
        try {
            compile = compiler.compile(compilable);
        } catch (CompilerException | CompilationException | UnsupportedLanguageException | IOException | InterruptedException compilerException) {

        }
        assertFalse("Compilation Error Expected", compile);

    }

    /**
     * Rigourous Test :-)
     *
     * @throws java.lang.InterruptedException
     * @throws cu.uci.uengine.compiler.exceptions.CompilerException
     * @throws cu.uci.uengine.compiler.exceptions.CompilationException
     * @throws cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException
     * @throws java.io.IOException
     */
    public void testPerlCompilation() throws InterruptedException, CompilerException, CompilationException, UnsupportedLanguageException, IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext("cu.uci.uengine.compiler","cu.uci.uengine.languages");
        Compiler compiler = appCtx.getBean(Compiler.class);

        assertEquals("Perl can't be compiled", compiler.isCompilerAvailable("Perl"), false);
    }

    /**
     * Rigourous Test :-)
     *
     * @throws java.lang.InterruptedException
     * @throws cu.uci.uengine.compiler.exceptions.CompilerException
     * @throws cu.uci.uengine.compiler.exceptions.CompilationException
     * @throws cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException
     * @throws java.io.IOException
     */
    public void testPythonCompilation() throws InterruptedException, CompilerException, CompilationException, UnsupportedLanguageException, IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext("cu.uci.uengine.compiler","cu.uci.uengine.languages");
        Compiler compiler = appCtx.getBean(Compiler.class);

        assertEquals("Python can't be compiled", compiler.isCompilerAvailable("Python"), false);
    }

    /**
     * Rigourous Test :-)
     *
     * @throws java.lang.InterruptedException
     * @throws cu.uci.uengine.compiler.exceptions.CompilerException
     * @throws cu.uci.uengine.compiler.exceptions.CompilationException
     * @throws cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException
     * @throws java.io.IOException
     */
    public void testRubyCompilation() throws InterruptedException, CompilerException, CompilationException, UnsupportedLanguageException, IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext("cu.uci.uengine.compiler","cu.uci.uengine.languages");
        Compiler compiler = appCtx.getBean(Compiler.class);

        assertEquals("Ruby can't be compiled", compiler.isCompilerAvailable("Ruby"), false);
    }

    /**
     * Rigourous Test :-)
     *
     * @throws java.lang.InterruptedException
     * @throws cu.uci.uengine.compiler.exceptions.CompilerException
     * @throws cu.uci.uengine.compiler.exceptions.CompilationException
     * @throws cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException
     * @throws java.io.IOException
     */
    public void testPHPCompilation() throws InterruptedException, CompilerException, CompilationException, UnsupportedLanguageException, IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext("cu.uci.uengine.compiler","cu.uci.uengine.languages");
        Compiler compiler = appCtx.getBean(Compiler.class);

        assertEquals("PHP can't be compiled", compiler.isCompilerAvailable("PHP"), false);
    }

    /**
     * Rigourous Test :-)
     *
     * @throws java.lang.InterruptedException
     * @throws cu.uci.uengine.compiler.exceptions.CompilerException
     * @throws cu.uci.uengine.compiler.exceptions.CompilationException
     * @throws cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException
     * @throws java.io.IOException
     */
    public void testBashCompilation() throws InterruptedException, CompilerException, CompilationException, UnsupportedLanguageException, IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext("cu.uci.uengine.compiler","cu.uci.uengine.languages");
        Compiler compiler = appCtx.getBean(Compiler.class);

        assertEquals("Bash can't be compiled", compiler.isCompilerAvailable("Bash"), false);
    }

    class MockCompilable implements Compilable {

        private final String sourceCode;
        private final String language;
        private final String languageExtension;
        private File sourceFile;
        private String executablePath;
        private final File temporaryDirectory;
        private final int id;

        public MockCompilable(int id, String sourceCode, String language, String languageExtension, File temporaryDirectory) {
            this.id = id;
            this.sourceCode = sourceCode;
            this.language = language;
            this.languageExtension = languageExtension;
            this.temporaryDirectory = temporaryDirectory;
            this.executablePath = new File(temporaryDirectory, String.valueOf(id)).getAbsolutePath();
        }

        public MockCompilable(int id, File sourceFile, String language, File temporaryDirectory) {
            this(id, "", language, "", temporaryDirectory);
            this.sourceFile = sourceFile;
        }

        @Override
        public String getExecutablePath() {
            
            return executablePath;
        }

        @Override
        public String getLanguageName() {
            return language;
        }

        @Override
        public String getSourceCode() {
            return sourceCode;

        }

        @Override
        public File getSourceFile() {
            if (sourceFile != null) {
                return sourceFile;
            }

            String sourceFilePath = null;
            try {
                sourceFilePath = FileUtils.writeStringToFile(String.format("%s.%s", id, languageExtension), FileUtils.getTempDirectory(), sourceCode);

            } catch (IOException ex) {
                Logger.getLogger(CompilerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new File(sourceFilePath);

        }

        @Override
        public File getTemporaryDirectory() {
            return temporaryDirectory;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public void setExecutablePath(String executablePath) {
            this.executablePath = executablePath;
        }

    }
}
