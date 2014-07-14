package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.MockComponentsFactory;

public class MyCoffeeMachine implements CoffeeMachine {
	private ComponentsFactory factory;

	public MyCoffeeMachine(ComponentsFactory factory) {
		MockComponentsFactory mensagem = new MockComponentsFactory();
		mensagem.getDisplay().info("Insert coins and select a drink!");
		this.factory = factory;
	}
}
