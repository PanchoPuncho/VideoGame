package com.local.app;

/** Imports **/

/**
 * @author Francisco Cuevas
 * Item class containing the commonalities between equipment and weapons
 **/
public class Item {
	// Constants
	protected static final int PRICE		= 0;
	protected static final int DEFENSE		= 1;
	protected static final int ATTACK		= 2;
	protected static final int ACCURACY 	= 3;
	protected static final int SPEED 		= 4;
	protected static final int NUM_STATS 	= 5;
	
	// Global variables
	protected String 	myName;
	protected int[] 	myStats;
	
	/**
	 *	Getters and setters
	 **/
	final public String getName() { return this.myName; }
	final public void setName(String name) { this.myName = name; }
	final public int[] getStats() { return this.myStats; }
	final public int getPrice() { return this.myStats[PRICE]; }
	final public void setPrice(int price) { this.myStats[PRICE] = price; }
	final public int getDefense() { return this.myStats[DEFENSE]; }
	final public void setDefense(int defense) { this.myStats[DEFENSE] = defense; }
	final public int getAttack() { return this.myStats[ATTACK]; }
	final public void setAttack(int attack) { this.myStats[ATTACK] = attack; }
	final public int getAccuracy() { return this.myStats[ACCURACY]; }
	final public void setAccuracy(int accuracy) { this.myStats[ACCURACY] = accuracy; }
	final public int getSpeed() { return this.myStats[SPEED]; }
	final public void setSpeed(int speed) { this.myStats[SPEED] = speed; }
}




