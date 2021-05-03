package com.jk.codetest.fixedincome;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class TradeTickDemo {
    public static void main(String[] args) throws InterruptedException {
        TradingRepository tradingRepository = new TradingRepository();
        int numberOfRequests = 100;

        CountDownLatch producerLatch = new CountDownLatch(numberOfRequests);
        CountDownLatch consumerLatch = new CountDownLatch(numberOfRequests);
        ExecutorService service = Executors.newFixedThreadPool(10);

        TradeTickPresentation tradeTickPresentation = new TradeTickPresentation(tradingRepository);

        for (int i = 0; i < numberOfRequests; i++) {
            service.execute(new TradeTickProducer(tradingRepository, producerLatch));
            service.execute(new TradeTickConsumer(tradingRepository, consumerLatch));
        }
        producerLatch.await();
        consumerLatch.await();
        tradeTickPresentation.start();

        System.out.println("Total number of trade ticks generated " + tradingRepository.getAllTradeTicks().size());
        System.out.println("All trades with max values");
        Map<String, Optional<TradeTick>> maxTradeTicks = tradingRepository.getAllTradeTicks().stream()
                        .collect(Collectors.groupingBy(TradeTick::getSymbol, Collectors.maxBy(Comparator.comparing(
                                        TradeTick::getPrice))));

        maxTradeTicks.entrySet().stream().forEach(entry -> System.out.println("Trade + " + entry.getKey() + " price " + entry.getValue().get().getPrice()));
    }

}
