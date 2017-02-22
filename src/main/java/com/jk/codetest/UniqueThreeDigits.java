package com.jk.codetest;

import java.util.List;
import java.util.Random;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class UniqueThreeDigits {

    /**
     * Returns the unique three digit number from a list of 4 digits.
     * @param input digits
     * @return unique three digit number
     */
    public String getUniqueThreeDigitNumber(List<Integer> input) {
        Preconditions.checkArgument(input != null && input.size() == 4);

        StringBuilder digitAccumulator = new StringBuilder();
        while (input.size() > 1) {
            digitAccumulator.append(getDigitAndRemove(input));
        }
        return digitAccumulator.toString();
    }

    /**
     * Gets the count of unique three digit numbers from a list of four digits.
     * @param input list of digits
     * @return count of unique three digit numbers
     */
    public int getUniqueThreeDigitNumberCount(List<Integer> input) {
        Preconditions.checkArgument(input != null && input.size() == 4);

        return input.size() * (input.size() - 1) * (input.size() - 2);
    }

    private int getDigitAndRemove(List<Integer> input) {
        int rnd = new Random().nextInt(input.size());
        int firstDigit = input.get(rnd);
        input.remove(rnd);
        return firstDigit;
    }

    public static void main(String[] args) {
        UniqueThreeDigits uniqueThreeDigits = new UniqueThreeDigits();
        System.out.println("Unique number is " + uniqueThreeDigits.getUniqueThreeDigitNumber(Lists.newArrayList(1, 2, 3, 4)));

        System.out.println("Total count of three digit numbers is: " + uniqueThreeDigits.getUniqueThreeDigitNumberCount(Lists.newArrayList(1, 2, 3, 4)));

    }
}
