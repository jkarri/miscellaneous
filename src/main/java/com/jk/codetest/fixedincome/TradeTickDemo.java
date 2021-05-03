package com.jk.codetest.fixedincome;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TradeTickDemo {
    public static void main(String[] args) {
        TradingRepository tradingRepository = new TradingRepository();

        TradeTickProducer tradeTickProducer = new TradeTickProducer(tradingRepository);
        TradeTickConsumer tradeTickConsumer = new TradeTickConsumer(tradingRepository);
        TradeTickPresentation tradeTickPresentation = new TradeTickPresentation(tradingRepository);

        tradeTickProducer.start();
        tradeTickConsumer.start();
        tradeTickPresentation.start();
        try {
            tradeTickProducer.join();
        } catch (InterruptedException e) {
        }


        System.out.println("Total number of trade ticks generated " + tradeTickProducer.getAllTradeTicks().size());
        System.out.println("All trades with max values");
        Map<String, Optional<TradeTick>> maxTradeTicks = tradeTickProducer.getAllTradeTicks().stream()
                        .collect(Collectors.groupingBy(TradeTick::getSymbol, Collectors.maxBy(Comparator.comparing(
                                        TradeTick::getPrice))));

        maxTradeTicks.entrySet().stream().forEach(entry -> System.out.println("Trade + " + entry.getKey() + " price " + entry.getValue().get().getPrice()));
    }

}
