/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.evaluator.impl;

import cu.uci.uengine.evaluator.TextFileComparator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author lan
 */
@Component
public class DiffToolCliAdapter extends TextFileComparator {

    @Override
    public ComparatorResult compare(String filePathA, String filePathB, EnumSet<ComparatorFlag> flags) {

        List<String> command = buildCommand(filePathA, filePathB, flags);
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            p.waitFor();
            return fromExitCode(p.exitValue());
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(DiffToolCliAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ComparatorResult.ERROR;
    }

    private List<String> buildCommand(String filePathA, String filePathB, EnumSet<ComparatorFlag> flags) {
        List<String> modifiers = new ArrayList();
        modifiers.add("diff");

        for (ComparatorFlag flag : flags) {
            modifiers.add(getDiffModifier(flag));
        }

        modifiers.add("-q");//Report only when files differ, No output extra messages
        modifiers.add(filePathA);
        modifiers.add(filePathB);
        return modifiers;
    }

    private String getDiffModifier(ComparatorFlag flag) {
        switch (flag) {
            case IGNORE_ALL_SPACE:
                return "-w";
            case IGNORE_TRAILING_SPACE:
                return "-Z";
            case IGNORE_BLANK_LINES:
                return "-B";
            default:
                throw new UnsupportedOperationException("Unavailable flag");
        }
    }

    private ComparatorResult fromExitCode(int exitCode) {
        switch (exitCode) {
            case 0:
                return ComparatorResult.EQUAL;
            case 1:
                return ComparatorResult.DIFFERENT;
            default:
                return ComparatorResult.ERROR;
        }
    }

}
