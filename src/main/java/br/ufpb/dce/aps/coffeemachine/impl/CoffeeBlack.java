package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;

public class CoffeeBlack extends Bebida {

	public CoffeeBlack(Drink drink, ComponentsFactory factory) {
		this.factory = factory;
		
		if (drink == drink.BLACK) {
			this.drink = drink.BLACK;
		} else {
			this.drink = drink.BLACK_SUGAR;
		}
	}

	public void release() {
		if (drink == drink.BLACK_SUGAR) {
			this.factory.getSugarDispenser().release(5);
		}
	}

}
	
