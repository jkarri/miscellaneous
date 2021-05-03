package com.jk.codetest.fixedincome;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class TradeTickDemo {
    public static void main(String[] args) {
        TradingRepository tradingRepository = new TradingRepository();
        int numberOfRequests = 100;

        CountDownLatch producerLatch = new CountDownLatch(numberOfRequests);
        CountDownLatch consumerLatch = new CountDownLatch(numberOfRequests);

        TradeTickProducer tradeTickProducer = new TradeTickProducer(tradingRepository, producerLatch);
        TradeTickConsumer tradeTickConsumer = new TradeTickConsumer(tradingRepository, consumerLatch);
        TradeTickPresentation tradeTickPresentation = new TradeTickPresentation(tradingRepository);

        // TODO change this
        tradeTickProducer.start();
        tradeTickConsumer.start();
        tradeTickPresentation.start();
        try {
            tradeTickProducer.join();
        } catch (InterruptedException e) {
        }


        System.out.println("Total number of trade ticks generated " + tradingRepository.getAllTradeTicks().size());
        System.out.println("All trades with max values");
        Map<String, Optional<TradeTick>> maxTradeTicks = tradingRepository.getAllTradeTicks().stream()
                        .collect(Collectors.groupingBy(TradeTick::getSymbol, Collectors.maxBy(Comparator.comparing(
                                        TradeTick::getPrice))));

        maxTradeTicks.entrySet().stream().forEach(entry -> System.out.println("Trade + " + entry.getKey() + " price " + entry.getValue().get().getPrice()));
    }

}
