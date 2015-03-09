/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.languages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author lan
 */
@Component
@PropertySource(value = "classpath:languages.properties")
public class Languages {

    static Log log = LogFactory.getLog(Languages.class.getName());

    @Resource
    Properties languagesConfiguration;

    Map<String, Language> languages;

    public Languages(){
        languages = new HashMap();
    }
    
    @PostConstruct
    private void loadLanguages() throws IOException {

        String[] languagesConfig = languagesConfiguration.getProperty("language.list").split(",");

        for (String language : languagesConfig) {

            language = language.trim();

            String extension = languagesConfiguration.getProperty(language + ".ext").trim();

            if (!StringUtils.isEmpty(extension)) {
                addLanguage(new Language(language, extension));
            }
            

            Integer timeMultiplier = Integer.valueOf(languagesConfiguration.getProperty(language + ".time_mult").trim());
            Integer memoryMultiplier = Integer.valueOf(languagesConfiguration.getProperty(language + ".memory_mult").trim());

            if (!StringUtils.isEmpty(extension)) {
                addLanguage(new Language(language, extension,timeMultiplier,memoryMultiplier));
            }
        }
    }

    public void addLanguage(Language language) {
        languages.put(language.getName(), language);
    }

    public String getExtension(String language) {
        return languages.get(language).getExtension();
    }
    
    public Language getLanguage(String languageName){
        return languages.get(languageName);
    }
}
