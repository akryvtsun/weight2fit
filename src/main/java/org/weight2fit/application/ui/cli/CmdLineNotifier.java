package org.weight2fit.application.ui.cli;

import org.weight2fit.application.ui.UiNotifier;

/**
 * @author Andriy Kryvtsun
 */
class CmdLineNotifier implements UiNotifier {

    @Override
    public void showInfoMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showErrorMessage(String message) {
        System.err.println(message);
    }
}
