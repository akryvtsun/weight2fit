package org.weight2fit.application.ui;

import org.weight2fit.domain.FitParamsSupplier;

import java.io.File;

/**
 * FIT params supplier for output file.
 *
 * @author Andriy Kryvtsun
 */
public interface UiFitParamsSupplier extends FitParamsSupplier {

    /**
     * Output file for params
     *
     * @return  file name
     */
    File getFile();
}
