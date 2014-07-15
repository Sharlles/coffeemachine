package br.ufpb.dce.aps.coffeemachine.impl;


import org.mockito.Mock;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineTest;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.MockComponentsFactory;

public class MyCoffeeMachineTest extends CoffeeMachineTest{


	protected CoffeeMachine createFacade(ComponentsFactory factory){
		return new MyCoffeeMachine(factory);
		
	}

}
