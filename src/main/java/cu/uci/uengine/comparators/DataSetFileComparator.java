/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.comparators;

import cu.uci.uengine.exceptions.InvalidDataSetNameException;
import cu.uci.uengine.utils.RegexUtils;
import java.io.File;
import java.util.Comparator;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author lan
 */
@Component
public class DataSetFileComparator implements Comparator<File> {

    static Log log = LogFactory.getLog(DataSetFileComparator.class.getName());
    
    @Override
    public int compare(File fileA, File fileB) {
        int fileANumber, fileBNumber;
        try {
            fileANumber = RegexUtils.getDataSetNumber(fileA);
        } catch (InvalidDataSetNameException ex) {
            log.error(String.format("Invalid dataSet Name: %s. %s", fileA.getAbsolutePath(),ex.getMessage()));
            fileANumber = Integer.MAX_VALUE;//Max value push filename to the end
        }
        try {
            fileBNumber = RegexUtils.getDataSetNumber(fileB);
        } catch (InvalidDataSetNameException ex) {
            log.error(String.format("Invalid dataSets Name: %s. %s", fileB.getAbsolutePath(),ex.getMessage()));
            if (fileANumber == Integer.MAX_VALUE) {
                //If both filename are wrong sort by estándar lexicografical order
                return FilenameUtils.getBaseName(fileA.getName()).compareTo(FilenameUtils.getBaseName(fileB.getName()));
            } else {
                fileBNumber = Integer.MAX_VALUE;
            }
        }

        return fileANumber - fileBNumber;//Max value push filename to the end
    }

}
