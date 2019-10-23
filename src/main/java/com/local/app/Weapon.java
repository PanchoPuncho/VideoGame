package com.local.app;

/**
 * @author Francisco Cuevas
 * Weapon class extends Item
 */
public class Weapon extends Item {	
	/**
	 * Possible weapon types
	 */
	public enum WeaponType {
		FIST, SWORD_ONE, SWORD_TWO, AXE_ONE, AXE_TWO, LANCE_ONE, LANCE_TWO, SHIELD, BOW
	}
	
	// Global Variables
	private WeaponType myWeaponType;
	
	/**
	 * Constructor for Weapon.class
	 * 
	 * @param	name:		The name of the weapon
	 * @param	type:		The weapon's type
	 * @param	price:		The weapon's price
	 * @param	defense:	The weapon's defense
	 * @param	attack:		The weapon's attack
	 * @param	accuracy:	The weapon's accuracy
	 * @param	speed:		The weapon's speed
	 */
	public Weapon(String name, WeaponType type, int price, int defense, int attack, int accuracy, int speed) {
		this.myName = name;
		this.myWeaponType = type;

		this.myStats = new int[NUM_STATS];
		this.myStats[PRICE] 	= price;
		this.myStats[DEFENSE] 	= defense;
		this.myStats[ATTACK] 	= attack;
		this.myStats[ACCURACY] 	= accuracy;
		this.myStats[SPEED] 	= speed;
	}
	
	/**
	 * Check if the weapon is two handed
	 * 
	 * @return	True if the weapon is two handed, false otherwise.
	 */
	public boolean isTwoHanded() {
		boolean toReturn = false;
		
		if (myWeaponType == WeaponType.BOW ||
				myWeaponType == WeaponType.SWORD_TWO ||
				myWeaponType == WeaponType.AXE_TWO ||
				myWeaponType == WeaponType.LANCE_TWO) {
			toReturn = true;
		}
		
		return toReturn;
	}
	
	/**
	 * Getters and setters
	 */
	public WeaponType getWeaponType() { return myWeaponType; }
	//public int getWeaponIndex() { return myWeaponType.ordinal(); }
}
