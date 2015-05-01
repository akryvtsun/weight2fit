package org.weight2fit.domain;

/**
 * Domain model exception.
 *
 * @author Andriy Kryvtsun
 */
public class FitException extends Exception {

    public FitException(String message, Throwable cause) {
        super(message, cause);
    }
}
