package org.weight2fit.application.ui.gui;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

/**
 * Content checkers for main windows input fields.
 *
 * @author Andriy Kryvtsun
 */
public class FieldVerifiers {

    static VerifyListener DATE = new DateVerifyListener();
    static VerifyListener DOUBLE = new DoubleVerifyListener();
    static VerifyListener INTEGER = new IntVerifyListener();

    static class DateVerifyListener implements VerifyListener {
        @Override
        public void verifyText(VerifyEvent e) {
            e.doit = true;
            for (int i = 0; i < e.text.length(); i++) {
                char c = e.text.charAt(i);
                e.doit = Character.isDigit(c) || c == '-';
                if (!e.doit)
                    break;
            }
        }
    }

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
}
