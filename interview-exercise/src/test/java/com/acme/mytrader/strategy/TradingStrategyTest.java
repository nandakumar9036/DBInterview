package com.acme.mytrader.strategy;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalTime;

public class TradingStrategyTest {

	@Test
	void testPriceUpdateTriggerBuyOrder() {
		// Arrange
		String targetStock = "AAPL";
		double triggerPrice = 150.0;
		int lotsToBuy = 100;
		ExecutionService executionService = Mockito.mock(ExecutionService.class);
		TradingStrategy tradingStrategy = new TradingStrategy(targetStock, triggerPrice, lotsToBuy, executionService);

		tradingStrategy.priceUpdate(targetStock, 149.0);

		Mockito.verify(executionService).buy(targetStock, triggerPrice, lotsToBuy);
	}

	@Test
	void testPriceUpdateNoBuyOrder() {

		String targetStock = "AAPL";
		double triggerPrice = 150.0;
		int lotsToBuy = 100;
		ExecutionService executionService = Mockito.mock(ExecutionService.class);
		TradingStrategy tradingStrategy = new TradingStrategy(targetStock, triggerPrice, lotsToBuy, executionService);

		tradingStrategy.priceUpdate(targetStock, 151.0);

		Mockito.verify(executionService, Mockito.never()).buy(Mockito.any(), Mockito.anyDouble(), Mockito.anyInt());
	}

	@Test
	void testPriceUpdateValidation() {

		String targetStock = "AAPL";
		double triggerPrice = 150.0;
		int lotsToBuy = 100;
		ExecutionService executionService = Mockito.mock(ExecutionService.class);
		TradingStrategy tradingStrategy = new TradingStrategy(targetStock, triggerPrice, lotsToBuy, executionService);

		assertThrows(IllegalArgumentException.class, () -> tradingStrategy.priceUpdate(null, 149.0));
		assertThrows(IllegalArgumentException.class, () -> tradingStrategy.priceUpdate(targetStock, -1.0));
	}

	@Test
	void testExecuteBuyOrderDuringTradingHours() {
		// Arrange
		String targetStock = "AAPL";
		double triggerPrice = 150.0;
		int lotsToBuy = 100;
		ExecutionService executionService = Mockito.mock(ExecutionService.class);
		TradingStrategy tradingStrategy = new TradingStrategy(targetStock, triggerPrice, lotsToBuy, executionService);

		spyTradingStrategy.priceUpdate(targetStock, 149.0);

		Mockito.verify(executionService).buy(targetStock, triggerPrice, lotsToBuy);
	}

	@Test
	void testExecuteBuyOrderOutsideTradingHours() {
		// Arrange
		String targetStock = "AAPL";
		double triggerPrice = 150.0;
		int lotsToBuy = 100;
		ExecutionService executionService = Mockito.mock(ExecutionService.class);
		TradingStrategy tradingStrategy = new TradingStrategy(targetStock, triggerPrice, lotsToBuy, executionService);

		spyTradingStrategy.priceUpdate(targetStock, 149.0);

		Mockito.verify(executionService, Mockito.never()).buy(Mockito.any(), Mockito.anyDouble(), Mockito.anyInt());
	}
}
