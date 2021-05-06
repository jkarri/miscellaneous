package com.jk.codetest.fixedincome;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.LongAdder;

public class TradingRepository {
    private BlockingQueue<TradeTick> allTradeTicks = new LinkedBlockingQueue<>();

    private BlockingQueue<TradeTick> tradeTicks = new LinkedBlockingQueue<>();
    private Map<String, TradeTick> simpleMap;
    private final ConcurrentMap<String, TradeTick> largestTrade;
    private final ConcurrentMap<String, TradeAverage> tradeAverage;

    public TradingRepository() {
        simpleMap = new HashMap<>();
        largestTrade = new ConcurrentHashMap<>();
        tradeAverage = new ConcurrentHashMap<>();
    }

    public void addTradeTick(TradeTick tradeTick) {
        tradeTicks.offer(tradeTick);
        allTradeTicks.offer(tradeTick);
    }

    public synchronized void processTraceTickSerially() {
        TradeTick tradeTick;
        try {
                tradeTick = tradeTicks.take();
                TradeTick existingTradeTick = simpleMap.get(tradeTick.getSymbol());
                if (existingTradeTick == null) {
                    simpleMap.put(tradeTick.getSymbol(), tradeTick);
                } else {
                    if (existingTradeTick.getPrice().compareTo(tradeTick.getPrice()) < 0) {
                        simpleMap.put(tradeTick.getSymbol(), tradeTick);
                    }
                }
        } catch (InterruptedException e) {
            System.out.println("Interrupted when processing tick");
        }
    }

    public void processTradeTickGracefully() {
        try {
            final TradeTick tradeTick = tradeTicks.take();
            largestTrade.merge(tradeTick.getSymbol(), tradeTick, (t1, t2) -> {
                if (t1 == null) {
                    return t2;
                } else if (t2 == null){
                    return t1;
                } else {
                    if (t1.getPrice().compareTo(t2.getPrice()) < 0) {
                        return t2;
                    } else {
                        return t1;
                    }
                }
            });
            tradeAverage.compute(tradeTick.getSymbol(), (symbol, tradeAverage) -> {
                if (tradeAverage == null) {
                    LongAdder longAdder = new LongAdder();
                    longAdder.increment();
                    return new TradeAverage(tradeTick.getPrice(), longAdder);
                }
                LongAdder count = tradeAverage.getCount();
                BigDecimal average = tradeAverage.getAverage();
                BigDecimal currentSum = average.multiply(new BigDecimal(count.longValue()), MathContext.DECIMAL32).add(tradeTick.getPrice());
                count.increment();
                BigDecimal newAverage = currentSum.divide(new BigDecimal(count.longValue()), MathContext.DECIMAL32);
//                System.out.println("Count is " + count.longValue());
//                System.out.println("Average is " + average.setScale(5, BigDecimal.ROUND_HALF_EVEN));
//                System.out.println("Price is " + tradeTick.getPrice().setScale(5, BigDecimal.ROUND_HALF_EVEN));
//                System.out.println("New Average is " + newAverage.setScale(5, BigDecimal.ROUND_HALF_EVEN));
                return new TradeAverage(newAverage, count);
            });
        } catch (InterruptedException e) {
        }
    }

    public TradeTick getLargestTrade(String symbol) {
//        return simpleMap.get(symbol);
        return largestTrade.get(symbol);
    }

    public List<TradeTick> getAllTradeTicks() {
        List<TradeTick> allTrades = new ArrayList<>();
        allTradeTicks.drainTo(allTrades);
        return allTrades;
    }

    public TradeAverage getTradeAverage(String symbol) {
        return tradeAverage.get(symbol);
    }
}
