package com.jk.codetest.fixedincome;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TradeTickProducer extends Thread {
    public static String[] SYMBOLS = new String[] { "ABC", "XYZ", "AAPL", "AMZN", "MSFT", "GOOG", "BRK.A", "BRK.B" };
    private Random random = new Random();
    private static String[] STATUS = new String[] {"X", "Y", "Z", "A", "C"};

    private List<TradeTick> tradeTicks = new ArrayList<>();
    private TradingRepository tradingRepository;

    public TradeTickProducer(TradingRepository tradingRepository) {
        this.tradingRepository = tradingRepository;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 100) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Producer interrupted, exiting now...");
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {

            }
            TradeTick tradeTick = generateTradeTick();
//            System.out.println("Generating trade tick " + tradeTick);
            tradeTicks.add(tradeTick);
            tradingRepository.addTradeTick(tradeTick);
            i++;
        }
    }

    private TradeTick generateTradeTick() {
        String symbol = SYMBOLS[random.nextInt(SYMBOLS.length)];
        BigDecimal price = new BigDecimal(random.nextDouble() * 100);
        price = price.setScale(2, BigDecimal.ROUND_DOWN);
        String status = STATUS[random.nextInt(STATUS.length)];

        return new TradeTick(symbol, price, random.nextInt(1000), status, System.currentTimeMillis());
    }

    public List<TradeTick> getAllTradeTicks() {
        return tradeTicks;
    }
}
