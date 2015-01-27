package cu.uci.uengine;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

import cu.uci.coj.model.SubmissionJudge;
import java.util.List;

public abstract class ComparatorBase {
	// estas constantes coinciden con el valor de regreso del comando diff segun
    // 'diff --help'. Los valores son: 0 si ambos archivos son iguales, 1 si son
    // diferentes, 2 si ocurre un error
    protected int DIFF_IDENTICAL = 0;
    protected int DIFF_DIFFERENT = 1;
    protected int DIFF_ERROR = 2;

    protected FileFilter outDataFilter = new FileFilter() {

        @Override
        public boolean accept(File pathname) {
            return pathname.getName().endsWith(".out");
        }
    };

    @Autowired
    protected FileFilter inDataFilter;
    protected Properties properties;

    protected int diff(int maxTime, String execOutFilePath,
            String problemOutFilePath) throws IOException, InterruptedException {
		// -Z es para ignorar la linea blanca al final de los archivos. Esa no
        // cuenta para el presentation error.
        ProcessBuilder pb = new ProcessBuilder("diff", "-Z", "-q",
                execOutFilePath, problemOutFilePath);
        Process p = pb.start();
        p.waitFor();
        return p.exitValue();
    }

    protected int diffNoBlankSpaces(int maxTime, String execOutFilePath,
            String problemOutFilePath) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("diff", "-w", "-Z", "-q",
                execOutFilePath, problemOutFilePath);
        Process p = pb.start();
        p.waitFor();
        return p.exitValue();
    }

    public abstract File getTmpOutFile(SubmissionJudge submit, String num);

    public void compare(SubmissionJudge submit, File problemDir)
            throws IOException, InterruptedException {

        boolean finished = false;
        int idx = 0;
        File[] outDataFiles = problemDir.listFiles(outDataFilter);

        while (!finished && idx < outDataFiles.length) {

            String name = outDataFiles[idx].getName().substring(0,
                    outDataFiles[idx].getName().lastIndexOf(".out"));

            File problemOutFile = new File(problemDir, name + ".out");
            File problemInFile = new File(problemDir, name + ".in");
            File tmpOutFile = getTmpOutFile(submit, name);

            finished = diffFile(idx, problemDir, problemInFile, problemOutFile,
                    tmpOutFile, submit);

            idx++;
        }
    }

    public boolean diffFile(int idx, File problemDir, File problemInFile,
            File problemOutFile, File tmpOutFile, SubmissionJudge submit)
            throws IOException, InterruptedException {
        boolean finished = false;
        int diff, diffNoSpace;
        if (!submit.getLanguage().getName().equals("Prolog")) {
            diff = diff(submit.getTimeLimit() * 1000,
                    tmpOutFile.getAbsolutePath(), problemOutFile.getAbsolutePath());
            diffNoSpace = diffNoBlankSpaces(submit.getTimeLimit() * 1000,
                    tmpOutFile.getAbsolutePath(), problemOutFile.getAbsolutePath());
        } else {
            diff = diffPl(submit.getTimeLimit() * 1000,
                    tmpOutFile.getAbsolutePath(), problemOutFile.getAbsolutePath());
            diffNoSpace = diffNoBlankSpacesPl(submit.getTimeLimit() * 1000,
                    tmpOutFile.getAbsolutePath(), problemOutFile.getAbsolutePath());
        }

		// sucedio algun error, asi que marcamos error interno y terminamos
        // la
        // ejecucion.
        if (diff == DIFF_ERROR || diffNoSpace == DIFF_ERROR) {
            submit.setVerdict(Verdicts.SIE);
            finished = true;
        }

		// no ocurrio ningun error, asi que vamos a comparar para determinar
        // AC,
        // WA o PE
        if (diff == DIFF_DIFFERENT) {
			// si la unica diferencia es en los espacios en blanco, se da
            // PE,
            // sino, WA
            submit.priorityVerdict(diffNoSpace == DIFF_IDENTICAL ? Verdicts.PE
                    : Verdicts.WA);

            if (submit.getFirstWaCase() == 0) {
                submit.setFirstWaCase(idx);
            } else {
                submit.setFirstWaCase(Math.min(idx, submit.getFirstWaCase()));
            }
        } else {
            submit.priorityVerdict(Verdicts.AC);
            submit.setAcTestCases(submit.getAcTestCases() + 1);
        }

        return finished;
    }

     protected int diffPl(int maxTime, String execOutFilePath,
            String problemOutFilePath) throws IOException, InterruptedException {
		// -Z es para ignorar la linea blanca al final de los archivos. Esa no
        // cuenta para el presentation error.
         //El modificador -I se utiliza para eliminar la comparación en las líneas que cumplan con la expresión regular especificada.
         //Esto es una churrada pero necesario para obviar la impresión que hace prolog de esos textos en los ficheros de salida, hasta encontrar una nueva solución esto
         //debe quedarse así.
        ProcessBuilder pb = new ProcessBuilder("diff","-B","-Z","-q","-I","GNU Prolog","-I","Copyright (C)","-I","By Daniel Diaz","-I","^yes$","-I","[0-9]* ms) yes",
                execOutFilePath, problemOutFilePath);
        
        String algo = pb.toString();
        List<String> algo2 = pb.command();
        Process p = pb.start();
        
        p.waitFor();
        return p.exitValue();
    }
     

    protected int diffNoBlankSpacesPl(int maxTime, String execOutFilePath,
            String problemOutFilePath) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("diff","-w","-B","-Z","-q","-I","GNU Prolog","-I","Copyright (C)","-I","By Daniel Diaz","-I","^yes$","-I","[0-9]* ms) yes",
                execOutFilePath, problemOutFilePath);
        Process p = pb.start();
        p.waitFor();
        return p.exitValue();
    }
}
