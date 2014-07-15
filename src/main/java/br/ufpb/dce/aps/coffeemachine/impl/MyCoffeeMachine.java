package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.MockComponentsFactory;

public class MyCoffeeMachine implements CoffeeMachine {
	
	private int totalcent;
	private ComponentsFactory factory;
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		this.factory.getDisplay().info("Insert coins and select a drink!");
	}

	public void insertCoin(Coin coin) {
		totalcent = coin.getValue();
		int Decimal =  totalcent/100;
		int Centavos = totalcent%100;
		this.factory.getDisplay().info("Total: US$ " + Decimal + "." + Centavos);
		
		
	}


}
