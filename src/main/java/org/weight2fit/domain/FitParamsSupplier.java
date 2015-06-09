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
     * @return  FIT options set of <code>null</code> if option set creation was canceled
     * @throws FitException  if something was wrong during options set creation
     */
    FitParams get() throws FitException;
}
