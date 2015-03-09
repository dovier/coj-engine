/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.runner;

import java.io.IOException;

/**
 *
 * @author lan
 */
public interface Runner {

    RunnerResult run(Runnable runnable,RunnerContext runnerContext) throws IOException, InterruptedException;

    boolean isRunnable(String language);
}
