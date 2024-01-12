package com.acme.mytrader.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
@Component
public class TradingStrategy implements PriceListener {

	private final String targetStock;
	private final double triggerPrice;
	private final int lotsToBuy;
	private final ExecutionService executionService;
	private static final Logger logger = LoggerFactory.getLogger(TradingStrategy.class);

	public TradingStrategy(String targetStock, double triggerPrice, int lotsToBuy, ExecutionService executionService) {
		this.targetStock = targetStock;
		this.triggerPrice = triggerPrice;
		this.lotsToBuy = lotsToBuy;
		this.executionService = executionService;
	}

	@Override
	public void priceUpdate(String security, double price) {
		try {
			validatePriceUpdate(security, price);

			if (targetStock.equals(security) && price < triggerPrice) {
				excuteBuyOrder();
			}
		} catch (IllegalArgumentException e) {
			logger.error("Validation error: {}", e.getMessage());
		} catch (Exception e) {
			logger.error("An error occurred: {}", e.getMessage());
		}
	}

	private void validatePriceUpdate(String security, double price) {
		if (security == null || security.isEmpty()) {
			throw new IllegalArgumentException("Security cannot be null or empty");
		}

		if (price <= 0) {
			throw new IllegalArgumentException("Price must be greater than zero");
		}
	}

	private void executeBuyOrder() {
		try {
			executionService.buy(targetStock, triggerPrice, lotsToBuy);
		} catch (Exception e) {
			logger.error("Error executing buy order: {}", e.getMessage());
		}
	}

}
