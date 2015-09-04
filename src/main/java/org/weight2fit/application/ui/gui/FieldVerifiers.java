package org.weight2fit.application.ui.gui;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

/**
 * Content checkers for main windows input fields.
 *
 * @author Andriy Kryvtsun
 */
final class FieldVerifiers {

    static final VerifyListener DOUBLE = new DoubleVerifyListener();
    static final VerifyListener INTEGER = new IntVerifyListener();

    static class DoubleVerifyListener implements VerifyListener {
        @Override
        public void verifyText(VerifyEvent e) {
            e.doit = true;
            try {
                Text source = (Text)e.getSource();
                String toCheck = source.getText() + e.text;
                Double.parseDouble(toCheck);
            } catch (NumberFormatException e1) {
                e.doit = false;
            }
        }
    }

    static class IntVerifyListener implements VerifyListener {
        @Override
        public void verifyText(VerifyEvent e) {
            e.doit = true;
            try {
                Text source = (Text)e.getSource();
                String toCheck = source.getText() + e.text;
                Integer.parseInt(toCheck);
            } catch (NumberFormatException e1) {
                e.doit = false;
            }
        }
    }

    private FieldVerifiers() {}
}
