package br.ufpb.dce.aps.coffeemachine;


import org.mockito.Mock;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.impl.MyCoffeeMachine;

public class MyCoffeeMachineTest extends CoffeeMachineTest{


	protected CoffeeMachine createFacade(ComponentsFactory factory){
		return new MyCoffeeMachine(factory);
		
	}

}
