package com.jk.codetest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;

public class LongestWord {

    private static final String NON_WORD_PATTERN = "\\W+";

    /**
     * Finds the longest word from a text file
     * @param filePath absolute file path
     * @return  longest word
     * @throws IOException exception of the file is not found
     */
    public String findLongestWord(String filePath) throws IOException {
        Preconditions.checkArgument(filePath != null && !filePath.isEmpty(), "absolute file path should be provided");

        Path path = Paths.get(filePath);

        Stream<String> words = Files.lines(path).parallel().flatMap(line -> Arrays.stream(line.split(NON_WORD_PATTERN)));

        Optional<String> longestWord = words.sorted((a, b) -> Integer.compare(b.length(), a.length())).findFirst();

        return longestWord.orElse("");
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        LongestWord longestWord = new LongestWord();
        String longestWord1 = longestWord.findLongestWord("/Users/jkarri/practice/codetest/src/main/resources/words.txt");
        System.out.println("Longest word is " + longestWord1);
    }
}
