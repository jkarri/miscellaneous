package com.jk.codetest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;

public class SmallestWords {
    private static final String NON_WORD_PATTERN = "\\W+";

    public List<String> findSmallestNumberOfWordsWithAllLetters(String filePath) throws IOException {
        Preconditions.checkArgument(filePath != null && !filePath.isEmpty(), "absolute file path should be provided");
        Path path = Paths.get(filePath);
        Stream<String> words = Files.lines(path).parallel().flatMap(line -> Arrays.stream(line.split(NON_WORD_PATTERN)));


        return null;
    }
}
