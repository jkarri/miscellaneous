package com.jk.codetest.fixedincome;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class TradeTickProducer extends Thread {
//    public static String[] SYMBOLS = new String[] { "ABC"};
    public static String[] SYMBOLS = new String[] { "ABC", "XYZ", "AAPL", "AMZN", "MSFT", "GOOG", "BRK.A", "BRK.B" };
    private Random random = new Random();
    private static String[] STATUS = new String[] {"X", "Y", "Z", "A", "C"};

    private TradingRepository tradingRepository;
    private CountDownLatch countDownLatch;

    public TradeTickProducer(TradingRepository tradingRepository, CountDownLatch countDownLatch) {
        this.tradingRepository = tradingRepository;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
//        System.out.println("Producing...");
        TradeTick tradeTick = generateTradeTick();
        tradingRepository.addTradeTick(tradeTick);
        countDownLatch.countDown();
    }

    private TradeTick generateTradeTick() {
        String symbol = SYMBOLS[random.nextInt(SYMBOLS.length)];
        BigDecimal price = new BigDecimal(random.nextDouble() * 100);
        price = price.setScale(2, BigDecimal.ROUND_DOWN);
        String status = STATUS[random.nextInt(STATUS.length)];

        return new TradeTick(symbol, price, random.nextInt(1000), status, System.currentTimeMillis());
    }

}
