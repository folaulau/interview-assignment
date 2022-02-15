package com.folautech.cleanupdata.service;

import java.io.FileNotFoundException;

public interface FileProcessorService {

    public String cleanUp(String filename) throws FileNotFoundException;
}
