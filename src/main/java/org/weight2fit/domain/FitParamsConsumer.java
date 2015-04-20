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
     * @throws Exception  if something was wrong during options set consuming
     */
    // TODO check for null parameter
    public void accept(FitParams params) throws Exception;
}
