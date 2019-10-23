package com.local.app;

import java.util.Arrays;

/** Imports **/

/**
 * Squad class containing all the characters.
 * @author Francisco Cuevas
 **/
public class Squad {
	private static final int SQUAD_SIZE = 5;
	private static final int ROW_SIZE = 2;
	private static final int COL_SIZE = 5;
	private Soldier[] mySquad;
	private Soldier[][] myFormation;

	public Squad () {
		this.mySquad = new Soldier[SQUAD_SIZE];
		this.myFormation = new Soldier[ROW_SIZE][COL_SIZE];
	}

	public int addSoldier(Soldier soldier) {
		int toReturn = 0;
		
		boolean addedToSquad = false;

		// Attempt to add the soldier to the squad
		for (int i = 0; i < SQUAD_SIZE; i++) {
			if (mySquad[i] == null) {
				mySquad[i] = soldier;
				addedToSquad = true;
				break;
			} else if (mySquad[i] == soldier) {
				toReturn = 1;
				break;
			}
		}

		// Ensure soldier was added to squad successfully
		if (toReturn == 0 &! addedToSquad) {
			toReturn = 2;
		}
		
		// Attempt to add the soldier to the formation
		if (toReturn == 0) {
			boolean addedToFormation = false;
			
			for (int row = 0; row < ROW_SIZE; row++) {
				for (int col = 0; col < COL_SIZE; col++) {
					if (myFormation[row][col] == null) {
						myFormation[row][col] = soldier;
						addedToFormation = true;
						break;
					}
				}
				if (addedToFormation) {
					break;
				}
			}
			
			if (!addedToFormation) {
				toReturn = 3;
			}
		}
		
		return toReturn;
	}
	
	public int changeFormation(Soldier soldier, int row, int col) {
		int toReturn = 0;
		
		boolean soldierFound = false;
				
		for (int boardRow = 0; boardRow < ROW_SIZE; boardRow++) {
			for (int boardCol = 0; boardCol < COL_SIZE; boardCol++) {
				if (myFormation[boardRow][boardCol] == soldier) {
					myFormation[boardRow][boardCol] = null;
					soldierFound = true;
				}
			}
		}
		
		if (soldierFound) {
			myFormation[row][col] = soldier;
		} else {
			toReturn = 1;
		}
		
		return toReturn;
	}

	public String toString() {
		String output = "\t\t\tSquad\n";

		for (int i = 0; i < this.mySquad.length; i++) {
			output += this.mySquad[i] + "\n";
		}

		return output;
	}

	/**
	 *	Getters and setters
	 **/
	public Soldier[] getSquad() { return this.mySquad; }
	public Soldier[][] getFormation() { return this.myFormation; }
	public void setSquad(Soldier[] squad) { this.mySquad = squad; }
}