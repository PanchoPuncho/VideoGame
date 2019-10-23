package com.local.app;

/**
 * @author Francisco Cuevas
 * Equipment class extends Item
 **/
public class Equipment extends Item {
	/**
	 * Possible armor types
	 */
	public enum ArmorType {
	    HELMET, SHOULDERGUARDS, BODY_ARMOR, GAUNTLETS, BOOTS
	}
	
	// Global variables
	private ArmorType myArmorType;
	
	/**
	 * Constructor for Equipment.class
	 * @param	name:		The name of the weapon
	 * @param	type:		The armor's type
	 * @param	price:		The armor's price
	 * @param	defense:	The armor's defense
	 * @param	attack:		The armor's attack
	 * @param	accuracy:	The armor's accuracy
	 * @param	speed:		The armor's speed
	 **/
	public Equipment(String name, ArmorType type, int price, int defense, int attack, int accuracy, int speed) {
		this.myName 		= name;
		this.myArmorType 	= type;
		
		this.myStats = new int[NUM_STATS];
		this.myStats[PRICE]		= price;
		this.myStats[DEFENSE] 	= defense;
		this.myStats[ATTACK] 	= attack;
		this.myStats[ACCURACY] 	= accuracy;
		this.myStats[SPEED] 	= speed;
	}

	/**
	 *	Getters and setters
	 **/
	public int getArmorIndex() { return myArmorType.ordinal(); }
}






