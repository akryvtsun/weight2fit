package org.weight2fit.infrastructure;

import org.weight2fit.application.ui.FileSupplier;

import java.io.File;

/**
 * @author Andriy Kryvtsun
 */
public class EmptyFileSupplier implements FileSupplier {

    @Override
    public File getFile() {
        return null;
    }
}
