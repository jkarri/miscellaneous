package com.jk.codetest.fixedincome;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;
import java.util.List;
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
        int numberOfRequests = 10;
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
        List<TradeTick> allTradeTicks = tradingRepository.getAllTradeTicks();
        Map<String, Optional<TradeTick>> maxTradeTicks = allTradeTicks.stream()
                        .collect(Collectors.groupingBy(TradeTick::getSymbol, Collectors.maxBy(Comparator.comparing(
                                        TradeTick::getPrice))));

        maxTradeTicks.entrySet().stream()
                        .forEach(entry -> {
                            assertEquals(entry.getValue().get().getPrice(), tradingRepository.getLargestTrade(entry.getKey()).getPrice());
                            System.out.println("Trade + " + entry.getKey() + " price " + entry.getValue().get().getPrice());
                        });

        Map<String, BigDecimal> averagesByTrade = allTradeTicks.stream().collect(Collectors.groupingBy(a -> a.getSymbol(),
                        Collectors.collectingAndThen(Collectors.toList(), l -> l.stream().map(t -> t.getPrice()).reduce(
                                        BigDecimal.ZERO, (a, b) -> a.add(b)).divide(new BigDecimal(l.size()), MathContext.DECIMAL64))));


        averagesByTrade.entrySet().stream().forEach(entry -> {
            assertEquals(entry.getValue().setScale(2, BigDecimal.ROUND_HALF_EVEN),
                            tradingRepository.getTradeAverage(entry.getKey()).getAverage().setScale(2, BigDecimal.ROUND_HALF_EVEN));
        });
    }
}