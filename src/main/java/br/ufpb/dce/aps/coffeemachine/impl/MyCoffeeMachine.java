package br.ufpb.dce.aps.coffeemachine.impl;

import static org.mockito.Matchers.anyDouble;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;
import br.ufpb.dce.aps.coffeemachine.MockComponentsFactory;

public class MyCoffeeMachine implements CoffeeMachine {

	private int totalCents;
	private ComponentsFactory factory;
	ArrayList< Coin > moedas = new ArrayList< Coin >();
	private Drink drink;
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		this.factory.getDisplay().info("Insert coins and select a drink!");
	}

	public void insertCoin(Coin coin)throws CoffeeMachineException {
		try{
			this.totalCents += coin.getValue();
			this.factory.getDisplay()
			.info("Total: US$ "+this.totalCents/100+"." + this.totalCents%100);
		}
		catch(NullPointerException e){
			throw new CoffeeMachineException("Esta moeda não é aceita"); 
		}
		
		int size = moedas.size();
		if (size== 0) {
			moedas.add(coin);
		}
		
		for(int i = 0; i< size ; i++){
			if (coin == null || (coin.getValue() >= moedas.get(i).getValue())) { 
				moedas.add(i, coin);
			}
			else{
				moedas.add(coin);
			}
		}
	}

	public void cancel() {
		if(totalCents == 0) {
			throw new CoffeeMachineException("Sem moedas");
		}else{
			this.factory.getDisplay().warn(Messages.CANCEL);
			if(moedas.size() > 0) {
				returnCoins();
			}
		}
	}
	
	void returnCoins(){
		
		for(int i = 0; i< moedas.size() ; i++){
			
			this.factory.getCashBox().release(moedas.get(i));
		}
		this.factory.getDisplay().info(Messages.INSERT_COINS);
	}

	void newSession(){
		moedas.clear();
		this.factory.getDisplay().info(Messages.INSERT_COINS);
	}

	public void select(Drink drink) {
	
		this.drink = drink;
		this.factory.getCupDispenser().contains(1);
		
		if(! factory.getWaterDispenser().contains(0.1)){ 
			factory.getDisplay().warn(Messages.OUT_OF_WATER); 
			returnCoins();
			return;
		}
		
		if(! factory.getCoffeePowderDispenser().contains(0.1)){ 
			factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER); 
			returnCoins();
			return;
		}
	
		
		if(drink == this.drink.BLACK_SUGAR){
			if(! factory.getSugarDispenser().contains(0.1)){ 
				factory.getDisplay().warn(Messages.OUT_OF_SUGAR); 
				returnCoins();
				return;
			}	
			
		}
		
		this.factory.getDisplay().info(Messages.MIXING);
		this.factory.getCoffeePowderDispenser().release(0.1);
		this.factory.getWaterDispenser().release(0.1);
	
		if(drink == this.drink.BLACK_SUGAR){
			this.factory.getSugarDispenser().release(0.1);
		}
		
		this.factory.getDisplay().info(Messages.RELEASING);
		this.factory.getCupDispenser().release(1);
		this.factory.getDrinkDispenser().release(0.1);
		
		this.factory.getDisplay().info(Messages.TAKE_DRINK);	
		
		newSession();
	
	
	}
	
}
