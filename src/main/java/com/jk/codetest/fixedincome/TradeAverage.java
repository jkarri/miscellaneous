package com.jk.codetest.fixedincome;

import java.math.BigDecimal;
import java.util.concurrent.atomic.LongAdder;

public class TradeAverage {

    private final BigDecimal average;
    private final LongAdder count;

    public TradeAverage(BigDecimal average, LongAdder count) {
        this.average = average;
        this.count = count;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public LongAdder getCount() {
        return count;
    }
}
