package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;

public abstract class Bebida {

	protected Drink drink; 
	protected ComponentsFactory factory;
	
	public Drink getDrink(){
		return this.drink;
	}
	public abstract void release();	
	
}