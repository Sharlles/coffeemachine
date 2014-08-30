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
	ArrayList< Coin > troco = new ArrayList< Coin >();
	private Drink drink;
	private ManagerDrink manager;
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		this.manager = new ManagerDrink(this.factory);
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
	
	
	public int calcTroco(){
		int contCoins = 0;
		for(Coin c : Coin.reverse()){
			for(Coin aux : moedas){
				if(aux == c){
					contCoins = contCoins + aux.getValue();
				}
			}
		}
		
		return contCoins - manager.getPreco();
	}

	public int[] planejarTroco() {

		int troco = calcTroco();
		int[] changePlan = new int[6];
		int i = 0;
		for (Coin r : Coin.reverse()) {
			if (r.getValue() <= troco && this.factory.getCashBox().count(r) > 0) {
				while (r.getValue() <= troco) {
					troco -= r.getValue();
					changePlan[i]++;
				}
			}
			i++;
		}
		if (troco != 0) {
			throw new CoffeeMachineException("");

		}

		return changePlan;
	}

	private void liberarTroco(int[] changePlan) {

		for (int i = 0; i < changePlan.length; i++) {
			int count = changePlan[i];
			Coin coin = Coin.reverse()[i];

			for (int j = 1; j <= count; j++) {
				this.factory.getCashBox().release(coin);
			}
		}
	}

	public void select(Drink drink) {
		
		this.drink = drink;
		this.manager.selecionarCafe(drink);

		
		if(this.totalCents < manager.getPreco() || this.totalCents == 0) {
			factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			returnCoins();
			return;
		}
		
		
		if (!this.manager.verificarIngredientes()) {
			returnCoins();
			return;
		}
		if(!this.manager.verificarAcucar()){ 
			returnCoins();
			return;
		} 
		
		int[] changePlan = null;
		
		if(drink != this.drink.BLACK){
			try {
				changePlan = planejarTroco();
		
			} catch (Exception e) {
				factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
				returnCoins();
				return;
			}
		}
		
		this.manager.mix();
		this.manager.release();
		
		if(drink != this.drink.BLACK){
			liberarTroco(changePlan);
		}
		
		newSession();
	
	}
	
}
