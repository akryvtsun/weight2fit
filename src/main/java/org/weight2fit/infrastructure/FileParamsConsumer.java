package org.weight2fit.infrastructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * FIT parameters writer to a file.
 *
 * @author Andiry Kryvtsun
 */
public class FileParamsConsumer extends OutputStreamParamsConsumer {

    public FileParamsConsumer(File file) throws FileNotFoundException {
        super(new FileOutputStream(file));
    }
}
