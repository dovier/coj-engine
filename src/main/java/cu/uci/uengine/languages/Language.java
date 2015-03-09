/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.languages;

/**
 *
 * @author lan
 */
public class Language {

    private String name;
    private String extension;
    private Integer timeMultiplier;
    private Integer memoryMultiplier;

    public Language(String name, String extension) {
        this.name = name;
        this.extension = extension;
    }

    public Language(String name, String extension, Integer timeMultiplier, Integer memoryMultiplier) {
        this(name, extension);
        this.timeMultiplier = timeMultiplier;
        this.memoryMultiplier = memoryMultiplier;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return the timeMultiplier
     */
    public Integer getTimeMultiplier() {
        return timeMultiplier;
    }

    /**
     * @param timeMultiplier the timeMultiplier to set
     */
    public void setTimeMultiplier(Integer timeMultiplier) {
        this.timeMultiplier = timeMultiplier;
    }

    /**
     * @return the memoryMultiplier
     */
    public Integer getMemoryMultiplier() {
        return memoryMultiplier;
    }

    /**
     * @param memoryMultiplier the memoryMultiplier to set
     */
    public void setMemoryMultiplier(Integer memoryMultiplier) {
        this.memoryMultiplier = memoryMultiplier;
    }
}
