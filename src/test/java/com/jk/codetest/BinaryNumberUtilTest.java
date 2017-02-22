package com.jk.codetest;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class BinaryNumberUtilTest {

    @Test
    public void testProductOfTwoZeros() throws Exception {
        // Given
        String input1 = "0";
        String input2 = "0";

        // When
        BinaryNumberUtil binaryNumberUtil = new BinaryNumberUtil();
        String sum = binaryNumberUtil.multiplyBinaryNumbers(input1, input2);

        // Then
        assertThat(sum, is("0"));
    }

    @Test
    public void testProductOfZeroAndOne() throws Exception {
        // Given
        String input1 = "0";
        String input2 = "1";

        // When
        BinaryNumberUtil binaryNumberUtil = new BinaryNumberUtil();
        String sum = binaryNumberUtil.multiplyBinaryNumbers(input1, input2);

        // Then
        assertThat(sum, is("0"));
    }

    @Test
    public void testProductOfOneAndOne() throws Exception {
        // Given
        String input1 = "1";
        String input2 = "1";

        // When
        BinaryNumberUtil binaryNumberUtil = new BinaryNumberUtil();
        String sum = binaryNumberUtil.multiplyBinaryNumbers(input1, input2);

        // Then
        assertThat(sum, is("1"));
    }

    @Test
    public void testProductOfTwoDigitNumbers() throws Exception {
        // Given
        String input1 = "10";
        String input2 = "11";

        // When
        BinaryNumberUtil binaryNumberUtil = new BinaryNumberUtil();
        String sum = binaryNumberUtil.multiplyBinaryNumbers(input1, input2);

        // Then
        assertThat(sum, is("110"));
    }

    @Test
    public void testProductOfThreeDigitNumbers() throws Exception {
        // Given
        String input1 = "101";
        String input2 = "111";

        // When
        BinaryNumberUtil binaryNumberUtil = new BinaryNumberUtil();
        String sum = binaryNumberUtil.multiplyBinaryNumbers(input1, input2);

        // Then
        assertThat(sum, is("100011"));
    }

    @Test
    public void testProductOfFourDigitNumbers() throws Exception {
        // Given
        String input1 = "1111";
        String input2 = "1111";

        // When
        BinaryNumberUtil binaryNumberUtil = new BinaryNumberUtil();
        String sum = binaryNumberUtil.multiplyBinaryNumbers(input1, input2);

        // Then
        assertThat(sum, is("11100001"));
    }
}