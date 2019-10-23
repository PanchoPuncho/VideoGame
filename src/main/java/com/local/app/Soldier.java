package com.local.app;

import java.awt.Image;
import java.util.Arrays;
import java.util.Random;

/** Imports **/

/**
 * @author Francisco Cuevas
 * Soldier object, complete with everything specific to individual Soldiers.
 **/
public class Soldier {
	// Constants
	private static final int NUM_TOTAL_STATS 	= 7;
	private static final int NUM_EQUIPMENT 		= 5;
	private static final int NUM_WEAPONS 		= 2;
	private static final int DEFAULT_LEVEL		= 1;
	private static final int DEFAULT_EXPERIENCE	= 0;
	private static final int DEFAULT_HEALTH		= 100;
	private static final int DEFAULT_DEFENSE	= 0;
	private static final int DEFAULT_ATTACK		= 10;
	private static final int DEFAULT_ACCURACY 	= 75;
	private static final int DEFAULT_SPEED		= 10;
	private static final int LEVEL		= 0;
	private static final int EXPERIENCE = 1;
	private static final int HEALTH 	= 2;
	private static final int DEFENSE	= 3;
	private static final int ATTACK		= 4;
	private static final int ACCURACY	= 5;
	private static final int SPEED		= 6;
	public 	static final boolean MAIN_HAND = true;

	// Global Variables
	private String 	myName;
	private boolean myDefensiveStance;

	// Health, Defense, Strength, Speed, Accuracy
	private int[] myBaseStats;
	private int[] myEquipmentStats;
	private Equipment[] myEquipment;
	private Weapon[] myWeapons;
	
	/**
	 * Copy constructor
	 * (I'm really iffy about this)
	 */
	public Soldier(Soldier soldier) {
		// Primitives
		this.myDefensiveStance = soldier.defensiveStance();
		
		// Objects
		this.myName = new String(soldier.getName());
		this.myBaseStats = Arrays.copyOf(soldier.getBaseStats(), soldier.getBaseStats().length);
		this.myEquipmentStats = Arrays.copyOf(soldier.getEquipmentStats(), soldier.getEquipmentStats().length);
		this.myEquipment = Arrays.copyOf(soldier.getEquipment(), soldier.getEquipment().length);
		this.myWeapons = Arrays.copyOf(soldier.getWeapons(), soldier.getWeapons().length);
	}

	/**
	 *	Soldier constructor for initializing values.
	 **/
	public Soldier(String name) {
		// Initialize variables
		this.myName 			= name;
		this.myDefensiveStance 	= false;
		
		// Stats
		this.myBaseStats = new int[NUM_TOTAL_STATS];
		this.myBaseStats[LEVEL] 		= DEFAULT_LEVEL;
		this.myBaseStats[EXPERIENCE]	= EXPERIENCE;
		this.myBaseStats[HEALTH] 		= DEFAULT_HEALTH;
		this.myBaseStats[DEFENSE] 		= DEFAULT_DEFENSE;
		this.myBaseStats[ATTACK] 		= DEFAULT_ATTACK;
		this.myBaseStats[ACCURACY] 		= DEFAULT_ACCURACY;
		this.myBaseStats[SPEED] 		= DEFAULT_SPEED;

		
		// Equipment stats
		this.myEquipmentStats = new int[Item.NUM_STATS];
		for (int i = 0; i < myEquipmentStats.length; i++) {
			this.myEquipmentStats[i] = 0;
		}
		
		// Equipment
		this.myEquipment = new Equipment[NUM_EQUIPMENT];
		this.myEquipment[0] = new Equipment("Head", Equipment.ArmorType.HELMET, 0, 0, 0, 0, 0);
		this.myEquipment[1] = new Equipment("Shoulders", Equipment.ArmorType.SHOULDERGUARDS, 0, 0, 0, 0, 0);
		this.myEquipment[2] = new Equipment("Body", Equipment.ArmorType.BODY_ARMOR, 0, 0, 0, 0, 0);
		this.myEquipment[3] = new Equipment("Hands", Equipment.ArmorType.GAUNTLETS, 0, 0, 0, 0, 0);
		this.myEquipment[4] = new Equipment("Feet", Equipment.ArmorType.BOOTS, 0, 0, 0, 0, 0);
		
		// Weapons
		this.myWeapons = new Weapon[NUM_WEAPONS];
		this.myWeapons[0] = new Weapon("Fist", Weapon.WeaponType.FIST, 0, 0, 0, 0, 0);
		this.myWeapons[1] = new Weapon("Fist", Weapon.WeaponType.FIST, 0, 0, 0, 0, 0);
	}
	
	/**
	 *	Soldier constructor for initializing values.
	 **/
	public Soldier(String name, Weapon m, Weapon w, Equipment h, Equipment s, Equipment body, Equipment g, Equipment boots) {
		// Initialize variables
		this.myName 		= name;
		this.myDefensiveStance = false;
		
		// Stats
		this.myBaseStats = new int[NUM_TOTAL_STATS];
		this.myBaseStats[LEVEL] 		= DEFAULT_LEVEL;
		this.myBaseStats[EXPERIENCE] 	= DEFAULT_EXPERIENCE;
		this.myBaseStats[HEALTH] 		= DEFAULT_HEALTH;
		this.myBaseStats[DEFENSE] 		= DEFAULT_DEFENSE;
		this.myBaseStats[ATTACK] 		= DEFAULT_ATTACK;
		this.myBaseStats[ACCURACY] 		= DEFAULT_ACCURACY;
		this.myBaseStats[SPEED] 		= DEFAULT_SPEED;
		
		// Equipment stats
		this.myEquipmentStats = new int[Item.NUM_STATS];
		int[] m_stats = m.getStats();
		int[] w_stats = w.getStats();
		int[] h_stats = h.getStats();
		int[] s_stats = s.getStats();
		int[] body_stats = body.getStats();
		int[] g_stats = g.getStats();
		int[] boots_stats = boots.getStats();
		for (int i = 0; i < myEquipmentStats.length; i++) {
			this.myEquipmentStats[i] =
					m_stats[i] + w_stats[i] + 
					h_stats[i] + s_stats[i] + body_stats[i] + g_stats[i] + boots_stats[i];
		}
		
		// Equipment
		this.myEquipment = new Equipment[NUM_EQUIPMENT];
		this.myEquipment[0] = h;
		this.myEquipment[1] = s;
		this.myEquipment[2] = body;
		this.myEquipment[3] = g;
		this.myEquipment[4] = boots;
		
		// Weapons
		this.myWeapons = new Weapon[NUM_WEAPONS];
		this.myWeapons[0] = m;
		this.myWeapons[1] = w;
	}
	
	/**
	 * Take the new armor and replace the old armor. Update the stats accordingly.
	 * @param armor		The new armor that will replace the old armor
	 * @return			Zero if the execution passed, nonzero otherwise
	 */
	public int changeEquipment(Equipment new_armor) {
		int toReturn = 0;
		
		int armorIndex = new_armor.getArmorIndex();
		updateEquipmentStats(myEquipment[armorIndex], new_armor);
		myEquipment[armorIndex] = new_armor;

		return toReturn;
	}
	
	/**
	 * Take the new weapon and replace the old weapon.
	 * Update the stats accordingly.
	 * @param armor		The new armor that will replace the old armor
	 * @return			Zero if the execution passed, nonzero otherwise
	 */
	public int changeWeapon(Weapon new_weapon, boolean main_hand) {
		int toReturn = 0;
		
		if (new_weapon.isTwoHanded()) {
			for (int hand = 0; hand < NUM_WEAPONS; hand++) {
				for (int i = 0; i < new_weapon.getStats().length; i++) {
					myEquipmentStats[i] -= myWeapons[hand].getStats()[i];
				}
			}
			updateWeaponStats(new_weapon);
			myWeapons[0] = new_weapon;
			myWeapons[1] = new Weapon("Fist", Weapon.WeaponType.FIST, 0, 0, 0, 0, 0);
		} else {
			if (main_hand) {
				updateWeaponStats(new_weapon, myWeapons[0], 0);
				myWeapons[0] = new_weapon;
			} else {
				updateWeaponStats(new_weapon, myWeapons[1], 1);
				myWeapons[1] = new_weapon;
			}
		}

		return toReturn;
	}

	/**
	 *	Replacement of equipment has been approved.
	 *	Take the new armor and update the statistics accordingly.
	 **/
	private int updateEquipmentStats(Equipment old_armor, Equipment new_armor) {
		int toReturn = 0;

		for (int i = 0; i < new_armor.getStats().length; i++) {
			myEquipmentStats[i] += new_armor.getStats()[i] - old_armor.getStats()[i];
		}

		return toReturn;
	}
	
	/**
	 *	Replacement of equipment has been approved.
	 *	Take the new armor and update the statistics accordingly.
	 **/
	private int updateWeaponStats(Weapon new_weapon) {
		int toReturn = 0;

		for (int i = 0; i < new_weapon.getStats().length; i++) {
			myEquipmentStats[i] += new_weapon.getStats()[i];
		}

		return toReturn;
	}
	
	/**
	 *	Replacement of equipment has been approved.
	 *	Take the new armor and update the statistics accordingly.
	 **/
	private int updateWeaponStats(Weapon old_weapon, Weapon new_weapon, int hand) {
		int toReturn = 0;

		for (int i = 0; i < myEquipmentStats.length - 1; i++) {
			myEquipmentStats[i] += new_weapon.getStats()[i] - myWeapons[hand].getStats()[i];
		}

		return toReturn;
	}
	
	/**
	 * Attack has been approved. Calculate the damage and deal accordingly.
	 * @param recipient	The soldier getting attacked
	 * @return			Positive if attack succeeded, 0 if the attack missed, negative if defender died
	 */
	public int attack(Soldier defender) {
		int toReturn = -5;
		
		if (defender == null) {
			System.err.println("Defender was null on attack(Defender) call.");
		}
		
		// Calculate necessary stats
		double attackChance = ((double) (getTotalAccuracy() - (defender.getTotalAccuracy()/10)) ) / 100;
		Weapon.WeaponType attackWeapon = myWeapons[0].getWeaponType();
		Weapon.WeaponType defendWeapon = defender.getWeapons()[0].getWeaponType();
		double bonus = calculateBonus(attackWeapon, defendWeapon);

		// Get chance info
		Random rand = new Random();
		double chance = rand.nextDouble();
		
		if (chance <= attackChance) {
			// Attack hit
			int defenderHealth = defender.getHealth();
			int totalDefense;
			if (defender.defensiveStance() == true) {
				totalDefense = defender.getTotalDefense() * 2;
			} else {
				totalDefense = defender.getTotalDefense();
			}
			int totalAttack = (int) (getTotalAttack() * bonus);
			int actualDamage = (totalAttack - totalDefense);

			if (actualDamage <= 0) {
				actualDamage = 1;
			}
						
			defenderHealth -= actualDamage;
			
			if (defenderHealth <= 0) {
				// Defender died
				defender.setHealth(0);
				toReturn = actualDamage*-1;
			} else {
				// Successful hit
				defender.setHealth(defenderHealth);
				toReturn = actualDamage;
			}
		} else {
			// Attack missed
			toReturn = 0;
		}
		
		return toReturn;
	}
	
	private double calculateBonus(Weapon.WeaponType attackWeapon, Weapon.WeaponType defendWeapon) {
		double toReturn = 0;
		
		if ( attackWeapon.equals(Weapon.WeaponType.SWORD_ONE) || attackWeapon.equals(Weapon.WeaponType.SWORD_TWO) ) {
			if ( defendWeapon.equals(Weapon.WeaponType.AXE_ONE) || defendWeapon.equals(Weapon.WeaponType.AXE_TWO) ) {
				toReturn = 1.25;
			} else if ( defendWeapon.equals(Weapon.WeaponType.LANCE_ONE) || defendWeapon.equals(Weapon.WeaponType.LANCE_TWO) ) {
				toReturn = 0.75;
			} else {
				toReturn = 1;
			}
		} else if ( attackWeapon.equals(Weapon.WeaponType.AXE_ONE) ) {
			if ( defendWeapon.equals(Weapon.WeaponType.LANCE_ONE) || defendWeapon.equals(Weapon.WeaponType.LANCE_TWO) ) {
				toReturn = 1.25;
			} else if ( defendWeapon.equals(Weapon.WeaponType.SWORD_ONE) || defendWeapon.equals(Weapon.WeaponType.SWORD_TWO) ) {
				toReturn = 0.75;
			} else {
				toReturn = 1;
			}
		} else if ( attackWeapon.equals(Weapon.WeaponType.LANCE_ONE) || attackWeapon.equals(Weapon.WeaponType.LANCE_TWO) ) {
			if ( defendWeapon.equals(Weapon.WeaponType.SWORD_ONE) || defendWeapon.equals(Weapon.WeaponType.SWORD_TWO) ) {
				toReturn = 1.25;
			} else if ( defendWeapon.equals(Weapon.WeaponType.AXE_ONE) || defendWeapon.equals(Weapon.WeaponType.AXE_TWO) ) {
				toReturn = 0.75;
			} else {
				toReturn = 1;
			}
		} else {
			toReturn = 1;
		}
		
		return toReturn;
	}

	public String toString() {
		return myName + ":\n" +
			"\tLevel:\t\t"		+ myBaseStats[LEVEL] 		+ "\n" +
			"\tExperience:\t" 	+ myBaseStats[EXPERIENCE] 	+ "\n" +
			"\tHealth:\t\t" 	+ myBaseStats[HEALTH] 	+ "\n" +
			"\tDefense:\t" 		+ myBaseStats[DEFENSE] 	+ "\t+ " 	+ myEquipmentStats[1] 	+ "\t-> " + getTotalDefense() + "\n" +
			"\tAttack:\t\t" 	+ myBaseStats[ATTACK] 	+ "\t+ " 	+ myEquipmentStats[2] 	+ "\t-> " + getTotalAttack() + "\n" +
			"\tAccuracy:\t" 	+ myBaseStats[ACCURACY]	+ "%\t+ " 	+ myEquipmentStats[3] 	+ "\t-> " + getTotalAccuracy() + "%\n" +
			"\tSpeed:\t\t" 		+ myBaseStats[SPEED]	+ "\t+ " 	+ myEquipmentStats[4] 	+ "\t-> " + getTotalSpeed();	
	}

	/**
	 *	Getters and setters
	 **/
	public int getLevel() { return this.myBaseStats[LEVEL]; }
	public int getExperience() { return this.myBaseStats[EXPERIENCE]; }
	public int getHealth() { return this.myBaseStats[HEALTH]; }
	public void setHealth(int health) { this.myBaseStats[HEALTH] = health; }
	public int getTotalDefense() { return this.myBaseStats[DEFENSE] + this.myEquipmentStats[1]; }
	public int getTotalAttack() { return this.myBaseStats[ATTACK] + this.myEquipmentStats[2]; }
	public int getTotalAccuracy() { return this.myBaseStats[ACCURACY] + this.myEquipmentStats[3]; }
	public int getTotalSpeed() { return this.myBaseStats[SPEED] + this.myEquipmentStats[4]; }
	public void setTotalSpeed(int speed) { this.myBaseStats[SPEED] = (speed - this.myEquipmentStats[4]); }
	public String getName() { return this.myName; }
	public boolean defensiveStance() { return this.myDefensiveStance; }
	public void setDefensiveStance(boolean defensive_stance) { this.myDefensiveStance = defensive_stance; }
	public int[] getBaseStats() { return this.myBaseStats; }
	public int[] getEquipmentStats() { return this.myEquipmentStats; }
	public Weapon[] getWeapons() { return this.myWeapons; }
	public Equipment[] getEquipment() { return this.myEquipment; }
	public int getReach() { if (myWeapons[0].getWeaponType() == Weapon.WeaponType.BOW) return 2; else return 1; }
}






