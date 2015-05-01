package org.weight2fit.domain;

/**
 * Interface for FIT parameters producers.
 *
 * @author Andriy Kryvtsun
 */
public interface FitParamsSupplier {

    /**
     * Returns FIT options set.
     *
     * @return  FIT options set
     * @throws FitException  if something was wrong during options set creation
     */
    FitParams get() throws FitException;
}
