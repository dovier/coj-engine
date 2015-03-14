/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cu.uci.uengine.languages;

import java.io.IOException;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author lan
 */
@Configuration
public class LanguagesConfig {
        @Bean
    public Properties languagesConfiguration() throws IOException {
        Properties languagesConfiguration = new Properties();
        languagesConfiguration.load(ClassLoader
                .getSystemResourceAsStream("languages.properties"));
        return languagesConfiguration;
    }    
}
