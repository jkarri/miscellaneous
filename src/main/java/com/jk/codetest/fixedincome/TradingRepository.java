package com.jk.codetest.fixedincome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

public class TradingRepository {
    private List<TradeTick> allTradeTicks = new ArrayList<>();

    private BlockingQueue<TradeTick> tradeTicks = new LinkedBlockingQueue<>();
    private Map<String, TradeTick> simpleMap;
    private final ConcurrentMap<String, TradeTick> largestTrade;

    public TradingRepository() {
        simpleMap = new HashMap<>();
        largestTrade = new ConcurrentHashMap<>();
    }

    public void addTradeTick(TradeTick tradeTick) {
        tradeTicks.offer(tradeTick);
        allTradeTicks.add(tradeTick);
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
            TradeTick tradeTick = tradeTicks.take();
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
        } catch (InterruptedException e) {
        }
    }

    public TradeTick getLargestTrade(String symbol) {
//        return simpleMap.get(symbol);
        return largestTrade.get(symbol);
    }

    public List<TradeTick> getAllTradeTicks() {
        return allTradeTicks;
    }

}
