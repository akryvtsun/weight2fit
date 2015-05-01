package org.weight2fit.domain;

/**
 * Interface for FIT parameters processors.
 *
 * @author Andriy Kryvtsun
 */
public interface FitParamsConsumer {

    /**
     * Consumes FIT options set.
     *
     * @param params  FIT options set
     *
     * @throws NullPointerException  if <code>params</code> is null
     * @throws FitException  if something was wrong during options set consuming
     */
    void accept(FitParams params) throws FitException;
}
