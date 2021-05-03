package com.jk.codetest.fixedincome;

import java.util.concurrent.CountDownLatch;

public class TradeTickConsumer extends Thread {
    private TradingRepository tradingRepository;
    private CountDownLatch countDownLatch;

    public TradeTickConsumer(TradingRepository tradingRepository, CountDownLatch countDownLatch) {
        this.tradingRepository = tradingRepository;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
//        System.out.println("Consuming...");
        tradingRepository.processTradeTickGracefully();
        countDownLatch.countDown();
    }
}
