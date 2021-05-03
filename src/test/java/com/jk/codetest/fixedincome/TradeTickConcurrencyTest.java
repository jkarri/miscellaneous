package com.jk.codetest.fixedincome;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TradeTickConcurrencyTest {

    @Test
    public void testCounterWithConcurrency() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        int numberOfRequests = 10000;
        CountDownLatch producerLatch = new CountDownLatch(numberOfRequests);
        CountDownLatch consumerLatch = new CountDownLatch(numberOfRequests);

        TradingRepository tradingRepository = new TradingRepository();
        TradeTickPresentation tradeTickPresentation = new TradeTickPresentation(tradingRepository);

        for (int i = 0; i < numberOfRequests; i++) {
            service.execute(new TradeTickProducer(tradingRepository, producerLatch));
            service.execute(new TradeTickConsumer(tradingRepository, consumerLatch));
        }
        producerLatch.await();
        consumerLatch.await();

        tradeTickPresentation.start();
        Map<String, Optional<TradeTick>> maxTradeTicks = tradingRepository.getAllTradeTicks().stream()
                        .collect(Collectors.groupingBy(TradeTick::getSymbol, Collectors.maxBy(Comparator.comparing(
                                        TradeTick::getPrice))));

        maxTradeTicks.entrySet().stream()
                        .forEach(entry -> {
                            assertEquals(entry.getValue().get().getPrice(), tradingRepository.getLargestTrade(entry.getKey()).getPrice());
                            System.out.println("Trade + " + entry.getKey() + " price " + entry.getValue().get().getPrice());
                        });

    }
}