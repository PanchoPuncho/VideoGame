package com.local.app;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/** Imports **/

/**
 * The runner for the video game.
 * @author Francisco Cuevas
 **/
public class App {
	public enum StatType {
		HEALTH, DEFENSE, STRENGTH, ACCURACY, DODGE, SPEED
	}

	public static void main(String[] args) throws IOException {
		// Make a team
		Squad squad = new Squad();
		
		// Image hammerImage = ImageIO.read(new File("Images/DwarfPaladin_1.png"));
		// hammerImage = hammerImage.getScaledInstance(45, -45, 0);
		// Image axeImage = ImageIO.read(new File("Images/DwarfPaladin_2.png"));
		// axeImage = axeImage.getScaledInstance(45, 45, 0);

		// Add team members
		Soldier hammerPaladin = new Soldier("Hammer Paladin");
		Soldier axePaladin = new Soldier("Axe Paladin");
		squad.addSoldier(hammerPaladin);
		squad.addSoldier(axePaladin);
		
		// Create a clumsy helmet
		Equipment helmet = new Equipment("Soldier's Clumsy Helmet", Equipment.ArmorType.HELMET, 10, 10, 10, 10, 10);
		helmet.setDefense(3);
		helmet.setAccuracy(-1);
		
		// Create clumsy shoulderguards
		Equipment shoulderguards = new Equipment("Soldier's Clumsy Shoulderguards", Equipment.ArmorType.SHOULDERGUARDS, 10, 10, 10, 10, 10);
		shoulderguards.setDefense(2);
		shoulderguards.setAccuracy(-1);
		
		// Create a basic hammer
		Weapon hammer = new Weapon("Wooden Hammer", Weapon.WeaponType.AXE_ONE, 10, 10, 10, 10, 10);
		hammer.setAttack(5);
		
		// Create a basic axe
		Weapon axe = new Weapon("Wooden Axe", Weapon.WeaponType.AXE_TWO, 10, 10, 10, 10, 10);
		axe.setAttack(5);
		
		// Add the equipment to the characters
		hammerPaladin.changeEquipment(helmet);
		hammerPaladin.changeEquipment(shoulderguards);
		hammerPaladin.changeWeapon(hammer, true);
		axePaladin.changeEquipment(helmet);
		axePaladin.changeWeapon(axe, true);
		
		System.out.println(squad);
		
		hammerPaladin.attack(axePaladin);
		
		System.out.println("\n\n\n" + squad);

		// Checkers checkers = new Checkers();
		// checkers.main(args);
	}
}