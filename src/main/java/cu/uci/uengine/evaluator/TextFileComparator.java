/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.evaluator;

import java.util.EnumSet;

/**
 *
 * @author lan
 */
public abstract class TextFileComparator {

    public abstract ComparatorResult compare(String filePathA, String filePathB,EnumSet<ComparatorFlag> flags);
    
    public enum ComparatorFlag {
        IGNORE_ALL_SPACE, IGNORE_TRAILING_SPACE,IGNORE_BLANK_LINES
    }

    public enum ComparatorResult {
        EQUAL, DIFFERENT,ERROR
    }
}
