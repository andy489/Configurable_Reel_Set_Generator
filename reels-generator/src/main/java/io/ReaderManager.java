package io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.InvalidJsonFormatException;
import reel.ReelSetsCollectionData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReaderManager {
    private static final String INVALID_FILE = "Invalid file format. See user manual.";
    private static final String READ_FAIL = "Failed to read from %s";

    public static ReelSetsCollectionData readFile(String filePath, ObjectMapper objectMapper) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            try {
                return objectMapper.readValue(new File(filePath), ReelSetsCollectionData.class);
            } catch (JsonProcessingException e) {
                throw new InvalidJsonFormatException(INVALID_FILE, e.getCause());
            }
        } catch (IOException e) {
            throw new FileNotFoundException(String.format(READ_FAIL, filePath));
        }
    }
}
