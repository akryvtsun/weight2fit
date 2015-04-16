package org.weight2fit.domain;

/**
 * Interface for FIT parameters producers.
 *
 * @author Andriy Kryvtsun
 */
public interface FitParamsSupplier {

    public FitParams get() throws Exception;
}
