/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.utils;

import cu.uci.uengine.exceptions.InvalidDataSetNameException;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author lan
 */
public class RegexUtils {

    public static int getDataSetNumber(File file) throws InvalidDataSetNameException {
        String baseName = FilenameUtils.getBaseName(file.getName());
        
        Pattern p = Pattern.compile("\\d+$");//Digits at string end.
        
        Matcher m = p.matcher(baseName);
        if (!m.find()){
            throw new InvalidDataSetNameException(String.format("DataSet %s is not a valid name.",baseName));
        }
        
        int dataSetNumber = Integer.parseInt(m.group());
        
        return dataSetNumber;
    }
}
