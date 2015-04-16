package cu.uci.coj.uengine;

import cu.uci.uengine.compiler.exceptions.CompilationException;
import cu.uci.uengine.compiler.exceptions.CompilerException;
import cu.uci.uengine.compiler.exceptions.UnsupportedLanguageException;
import cu.uci.uengine.evaluator.TextFileComparator;
import cu.uci.uengine.utils.FileUtils;
import java.io.IOException;
import java.util.EnumSet;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Unit test for CompilerTest.
 */
public class TextFileComparatorTest
        extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TextFileComparatorTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(TextFileComparatorTest.class);
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
    public void testEqualsComparisson() throws InterruptedException, CompilerException, CompilationException, UnsupportedLanguageException, IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext("cu.uci.uengine.evaluator");
        TextFileComparator comparator = appCtx.getBean(TextFileComparator.class);
        
        String fileAPath = FileUtils.writeStringToFile("data1.out", FileUtils.getTempDirectory(), "This is an answer");
        String fileBPath = FileUtils.writeStringToFile("prototype1.out", FileUtils.getTempDirectory(), "This is an answer");
        
        TextFileComparator.ComparatorResult compare = comparator.compare(fileAPath,fileBPath, EnumSet.of(TextFileComparator.ComparatorFlag.IGNORE_TRAILING_SPACE));
        assertTrue("Files are expected to be equals.",compare == TextFileComparator.ComparatorResult.EQUAL);
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
    public void testDifferentComparisson() throws InterruptedException, CompilerException, CompilationException, UnsupportedLanguageException, IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext("cu.uci.uengine.evaluator");
        TextFileComparator comparator = appCtx.getBean(TextFileComparator.class);
        
        String fileAPath = FileUtils.writeStringToFile("data1.out", FileUtils.getTempDirectory(), "This is an answer");
        String fileBPath = FileUtils.writeStringToFile("prototype1.out", FileUtils.getTempDirectory(), "This is other answer");
        
        TextFileComparator.ComparatorResult compare = comparator.compare(fileAPath,fileBPath, EnumSet.of(TextFileComparator.ComparatorFlag.IGNORE_TRAILING_SPACE));
        assertTrue("Files are expected to be Different.",compare == TextFileComparator.ComparatorResult.DIFFERENT);
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
    public void testIncorrectPathError() throws InterruptedException, CompilerException, CompilationException, UnsupportedLanguageException, IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext("cu.uci.uengine.evaluator");
        TextFileComparator comparator = appCtx.getBean(TextFileComparator.class);
        
        TextFileComparator.ComparatorResult compare = comparator.compare("/invalid/archive/path.out", "/invalid/archive/path2.out", EnumSet.of(TextFileComparator.ComparatorFlag.IGNORE_TRAILING_SPACE));
        assertTrue("Expected an error because incorrect file paths",compare == TextFileComparator.ComparatorResult.ERROR);
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
    public void testIgnoreAllSpaceFlagComparisson() throws InterruptedException, CompilerException, CompilationException, UnsupportedLanguageException, IOException {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext("cu.uci.uengine.evaluator");
        TextFileComparator comparator = appCtx.getBean(TextFileComparator.class);
        
        String fileAPath = FileUtils.writeStringToFile("data1.out", FileUtils.getTempDirectory(), "This   is   an a  nswer");
        String fileBPath = FileUtils.writeStringToFile("prototype1.out", FileUtils.getTempDirectory(), "Th  is is    an ans   wer");
        
        TextFileComparator.ComparatorResult compare = comparator.compare(fileAPath,fileBPath, EnumSet.of(TextFileComparator.ComparatorFlag.IGNORE_ALL_SPACE));
        assertTrue("Files are expected to be equals ignoring all space.",compare == TextFileComparator.ComparatorResult.EQUAL);
    }
    
}
