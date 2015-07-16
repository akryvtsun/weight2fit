package org.weight2fit.application.ui;

import org.weight2fit.domain.FitParamsConsumer;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Creates <code>FileParamsConsumer</code> by request with concrete file name.
 *
 * @author Andriy Kryvtsun
 */
public interface FileParamsConsumerCreator {

    FitParamsConsumer create(File file) throws FileNotFoundException;
}
