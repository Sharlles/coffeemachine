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
	
	
	public boolean planejarTroco(int troco) {
		
		for (Coin moeda : Coin.reverse()) {
			
			if (moeda.getValue() <= troco && this.factory.getCashBox().count(moeda) > 0) {
				troco -= moeda.getValue();
			}
		}
			
		return troco == 0;
	}
	
	public void liberarTroco(int troco2) {
		
		for (Coin moeda : Coin.reverse()) {
			while (moeda.getValue() <= troco2) {
				factory.getCashBox().release(moeda);
				this.troco.add(moeda);
				troco2 -= moeda.getValue();
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


		if(drink == this.drink.WHITE_SUGAR){
			planejarTroco(calcTroco());	
		}
		
		if(drink == this.drink.WHITE){
			if(!planejarTroco(this.totalCents - manager.getPreco())) {
				factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
				returnCoins();
				return;
			}
		}

		this.manager.mix();
		this.manager.release();
		
		if(drink == this.drink.WHITE_SUGAR){
		  liberarTroco(calcTroco());

		}
		
		//if(drink == this.drink.WHITE_SUGAR){
			//liberarTroco(this.totalCents - manager.getPreco());
		//}
		
		
		newSession();
	
	}
	
}
