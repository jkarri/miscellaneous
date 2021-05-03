package com.jk.codetest.fixedincome;

import java.math.BigDecimal;

import com.google.common.base.MoreObjects;

public class TradeTick {
    private final String symbol;
    private final BigDecimal price;
    private final int size;
    private final String status;
    private final long timestamp;

    public TradeTick(String symbol, BigDecimal price, int size, String status, long timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.size = size;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getSize() {
        return size;
    }

    public String getStatus() {
        return status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                        .add("symbol", symbol)
                        .add("price", price)
                        .add("size", size)
                        .add("status", status)
                        .add("timestamp", timestamp)
                        .toString();
    }
}
