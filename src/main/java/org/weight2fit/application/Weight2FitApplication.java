package org.weight2fit.application;

/**
 * Defines application behavior.
 *
 * @author Andriy Kryvtsun
 */
// TODO: add constants for exit codes
public interface Weight2FitApplication {

    /**
     * Executes application
     *
     * @return  exit code:
     * <ul>
     *     <li>0 - if successful execution</li>
     *     <li>1 - if execution canceled by user</li>
     *     <li>2 - if something is wrong during execution</li>
     * </ul>
     */
    int execute();
}
