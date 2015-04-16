package org.weight2fit.domain;

/**
 * Interface for FIT parameters processors.
 *
 * @author Andriy Kryvtsun
 */
public interface FitParamsConsumer {

    public void accept(FitParams params) throws Exception;
}
