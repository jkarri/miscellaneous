package com.jk.codetest.fixedincome;

import java.util.Arrays;

public class TradeTickPresentation extends Thread {

    private TradingRepository tradingRepository;

    public TradeTickPresentation(TradingRepository tradingRepository) {
        this.tradingRepository = tradingRepository;
    }

    @Override
    public void run() {
        System.out.println("All trades with max values");
        Arrays.stream(TradeTickProducer.SYMBOLS)
                        .forEach(symbol -> System.out.println(symbol + " - " + tradingRepository.getLargestTrade(symbol)));
    }
}
