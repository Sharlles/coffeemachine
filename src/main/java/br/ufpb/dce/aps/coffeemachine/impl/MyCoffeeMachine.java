package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.MockComponentsFactory;

public class MyCoffeeMachine implements CoffeeMachine {

	private int totalCent;
	private int totalGeral;
	private ComponentsFactory factory;

	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		this.factory.getDisplay().info("Insert coins and select a drink!");
	}

	public void insertCoin(Coin coin) {
		totalCent = coin.getValue();
		int Decimal = totalCent / 100;
		int Centavos = totalCent % 100;
		this.factory.getDisplay()
				.info("Total: US$ " + Decimal + "." + Centavos);
	}

	public void insertCoins(Coin coins) {
		totalCent = coins.getValue();
		totalGeral = totalGeral + totalCent;
		int Decimals = totalGeral / 100;
		int Centavo = totalGeral % 100;
		this.factory.getDisplay()
				.info("Total: US$ " + Decimals + "." + Centavo);

	}

}
