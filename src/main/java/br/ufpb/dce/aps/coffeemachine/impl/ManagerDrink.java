package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class ManagerDrink {

	private ComponentsFactory factory;
	private Bebida drink;
	private int valor = 35;
	
	public ManagerDrink(ComponentsFactory factory){
		this.factory = factory;
	}
	
	public void selecionarCafe(Drink drink){
		if (drink == drink.BLACK ||drink == drink.BLACK_SUGAR){
			this.drink = new CoffeeBlack (drink, this.factory);
		}
		else{
			this.drink =  new CoffeeWhite (drink, this.factory);
		}
	}
	
	public boolean verificarIngredientes(Drink drink) {
		if (!this.factory.getCupDispenser().contains(1)) {
			this.factory.getDisplay().warn(Messages.OUT_OF_CUP);
			return false;
		}
		if (drink == drink.WHITE || drink == drink.WHITE_SUGAR){
			if(!this.factory.getWaterDispenser().contains(80)){
				this.factory.getDisplay().warn(Messages.OUT_OF_WATER);
				return false;
			}
		}
		else{
			
			if(!this.factory.getWaterDispenser().contains(100)) {
			this.factory.getDisplay().warn(Messages.OUT_OF_WATER);
			return false;
			}
		}
		if (!this.factory.getCoffeePowderDispenser().contains(15)) {
			this.factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
			return false;
		}
		else if (this.drink.getDrink() == Drink.WHITE || this.drink.getDrink() == Drink.WHITE_SUGAR){
			if (!this.factory.getCreamerDispenser().contains(20)){
				this.factory.getDisplay().warn(Messages.OUT_OF_CREAMER);
				return false;
			}
		}
			return true;
	}
	
	public boolean verificarAcucar(){
		if(this.drink.getDrink() == Drink.BLACK_SUGAR || this.drink.getDrink() == Drink.WHITE_SUGAR){
			if (!this.factory.getSugarDispenser().contains(5)) {
				this.factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
				return false;
			}
		}		
		return true;
	}	
	
	public void mix (Drink drink){
		this.factory.getDisplay().info(Messages.MIXING);
		this.factory.getCoffeePowderDispenser().release(15);
		if(drink == drink.WHITE || drink == drink.WHITE_SUGAR)
			this.factory.getWaterDispenser().release(80);
		else{
			this.factory.getWaterDispenser().release(100);
		}
	}
	
	public void release(){
		this.drink.release();
		this.factory.getDisplay().info(Messages.RELEASING);
		this.factory.getCupDispenser().release(1);
		this.factory.getDrinkDispenser().release(100);
		this.factory.getDisplay().info(Messages.TAKE_DRINK);
		
	}	

	public int getPreco(){
		return this.valor;
	}
}
