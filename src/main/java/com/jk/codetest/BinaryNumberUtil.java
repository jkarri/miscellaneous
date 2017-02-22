package com.jk.codetest;

import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;

public class BinaryNumberUtil {
    private static final Pattern BINARY_NUMBER_PATTERN = Pattern.compile("^[01]+$");
    public String multiplyBinaryNumbers(String binaryNumber1, String binaryNumber2) {
        checkArgument(isNotBlank(binaryNumber1) && isBinary(binaryNumber1));
        checkArgument(isNotBlank(binaryNumber2) && isBinary(binaryNumber2));

        long bin1Long = getLongFromBinary(binaryNumber1);
        long bin2Long = getLongFromBinary(binaryNumber2);
        long result = 0L;

        if (bin1Long == 0L || bin2Long == 0L) {
            return "0";
        }

        char[] bin1Digits = binaryNumber1.toCharArray();
        for (int i = bin1Digits.length-1; i >= 0; i--) {
            if (bin1Digits[i] == '1') {
                result = addBinary(result, bin2Long * getFactor(bin1Digits.length - i - 1));
            }
        }

        return String.valueOf(result);
    }

    private long getFactor(int i) {
        if (i == 0) {
            return 1;
        }
        long result = 1;
        while (i > 0) {
            result = result * 10;
            i--;
        }
        return result;
    }

    private long addBinary(long a, long b) {
        String binaryA = String.valueOf(a);
        String binaryB = String.valueOf(b);

        int indexA = binaryA.length() - 1;
        int indexB = binaryB.length() - 1;
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        while (indexA >= 0 || indexB >= 0) {
            int sum = carry;
            if (indexA >= 0) {
                sum += Character.getNumericValue(binaryA.charAt(indexA));
                indexA--;
            }
            if (indexB >= 0) {
                sum += Character.getNumericValue(binaryB.charAt(indexB));
                indexB--;
            }
            if (sum > 1) {
                carry = 1;
            } else {
                carry = 0;
            }
            if (sum == 2 || sum == 0) {
                sb.append('0');
            } else {
                sb.append('1');
            }
        }
        if (carry > 0) {
            sb.append('1');
        }

        sb.reverse();
        return getLongFromBinary(String.valueOf(sb));
    }

    private long getLongFromBinary(String binaryNumber) {

        if (binaryNumber.contains("1")) {
            return Long.parseLong(binaryNumber.substring(binaryNumber.indexOf("1"), binaryNumber.length()));
        }
        return 0;
    }


    private boolean isBinary(String binaryNumber) {
        return BINARY_NUMBER_PATTERN.matcher(binaryNumber).find();
    }

    private boolean isNotBlank(String string) {
        return string != null && !string.isEmpty();
    }
}
