package com.jk.codetest.fixedincome;

public class TradeTickConsumer extends Thread {
    private TradingRepository tradingRepository;

    public TradeTickConsumer(TradingRepository tradingRepository) {
        this.tradingRepository = tradingRepository;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Consumer interrupted, exiting now...");
            }
            tradingRepository.processTradeTickGracefully();
        }
    }
}
