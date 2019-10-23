package com.local.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Francisco Cuevas
 * 
 */
public class Battle extends JPanel {
	private static Soldier[][] myAttackersFormation;
	private static Soldier[][] myDefendersFormation;
	private static Board myBoard;
	private JButton myRestartButton;
	private JButton myResignButton;
	private JButton myAttackButton;
	private JButton myDefendButton;
	private JLabel myMessage;
	
	/**
	 * Nested applet class that can be used to run VideoGame as an applet. Size
	 * of the applet should be 350-by-250
	 */
	public static class Applet extends JApplet {
		public void init() {
			try {
				setContentPane(new Battle(myAttackersFormation, myDefendersFormation));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	} // end Applet class
	
	/**
	 * The constructor creates the Board (which in turn creates and manages the
	 * buttons and message label), adds all the components, and sets the bounds
	 * of the components. A null layout is used. (This is the only thing that is
	 * done in the main VideoGame class.)
	 * 
	 * @throws IOException
	 */
	public Battle(Soldier[][] attackers, Soldier[][] defenders) throws IOException {
		// Setup the global formations
		myAttackersFormation = attackers;
		myDefendersFormation = defenders;

		// Setup the Container details
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(428, 318));
		setBackground(new Color(128, 128, 128));
		
		// Setup the labels
		myMessage = new JLabel("Loading the battle...", JLabel.CENTER);
		myMessage.setFont(new Font("Serif", Font.BOLD, 14));
		myMessage.setForeground(Color.black);
		
		// Setup the buttons
		myResignButton = new JButton("Resign");
		myRestartButton = new JButton("Restart Battle");
		myAttackButton = new JButton("Attack");
		myDefendButton = new JButton("Defend");
		myResignButton.setEnabled(true);
		myRestartButton.setEnabled(false);
		myAttackButton.setEnabled(false);
		myDefendButton.setEnabled(false);

		// Note: The constructor for the board also creates the buttons and label
		myBoard = new Board(attackers, defenders);

		// Setup the panels and add them to the canvas
		// North below
		JPanel northPanel = new JPanel();
		northPanel.add(myRestartButton);
		northPanel.add(myResignButton);
		add(northPanel, BorderLayout.NORTH);
		// West below
		add(myAttackButton, BorderLayout.WEST);
		// Center below
		add(myBoard, BorderLayout.CENTER);
		// East below
		add(myDefendButton, BorderLayout.EAST);
		// South below
		JPanel southPanel = new JPanel();
		southPanel.add(myMessage);
		add(southPanel, BorderLayout.SOUTH);
		
		myMessage.setText("Let the battle begin!");
	} // end Battle() constructor
	
	private static Squad createAttackers() throws IOException {
		// Create the attacking squad
		Squad attackers = new Squad();
		
		// Constructor: ARMOR_TYPE, NAME, PRICE, DEFENSE, ATTACK, ACCURACY, SPEED
		Equipment head = new Equipment("Head", Equipment.ArmorType.HELMET, 0, 0, 0, 0, 0);
		Equipment shoulders = new Equipment("Shoulders", Equipment.ArmorType.SHOULDERGUARDS, 0, 0, 0, 0, 0);
		Equipment body = new Equipment("Body", Equipment.ArmorType.BODY_ARMOR, 0, 0, 0, 0, 0);
		Equipment hands = new Equipment("Hands", Equipment.ArmorType.GAUNTLETS, 0, 0, 0, 0, 0);
		Equipment feet = new Equipment("Feet", Equipment.ArmorType.BOOTS, 0, 0, 0, 0, 0);
		Equipment clumsyClothHelmet = new Equipment("Clumsy Cloth Helmet", Equipment.ArmorType.HELMET, 10, 2, 0, -1, -1);
		Equipment clumsyClothShoulderguards = new Equipment("Clumsy Cloth Shoulderguards", Equipment.ArmorType.SHOULDERGUARDS, 10, 2, 0, -1, -1);

		// Constructor: WEAPON_TYPE, TWO_HANDED, NAME, PRICE, DEFENSE, ATTACK,
		// ACCURACY, SPEED
		Weapon fist = new Weapon("Fist",Weapon.WeaponType.FIST, 0, 0, 0, 0, 0);
		Weapon woodenSword = new Weapon("Wooden Sword", Weapon.WeaponType.SWORD_ONE, 10, 0, 3, 0, 0);
		Weapon woodenAxe = new Weapon("Wooden Axe", Weapon.WeaponType.AXE_ONE, 10, 0, 3, 0, 0);
		Weapon woodenLance = new Weapon("Wooden Lance", Weapon.WeaponType.LANCE_ONE, 10, 0, 3, 0, 0);
		Weapon brokenBow = new Weapon("Broken Bow", Weapon.WeaponType.BOW, 10, 0, 3, 0, 0);
		Weapon gladiatorBlade = new Weapon("Gladiator Blade", Weapon.WeaponType.SWORD_TWO, 1000, 0, 50, 0, 0);
		
		// Constructor: NAME, MAIN, WEAK, HEAD, SHOULDER, BODY, GAUNTLETS, BOOTS
		Soldier aragorn = new Soldier("Aragorn", gladiatorBlade, fist, 
										clumsyClothHelmet, clumsyClothShoulderguards, body, hands, feet);
		attackers.addSoldier(aragorn);
		attackers.changeFormation(aragorn, 0, 2);
		
		// Create the soldier and add equipment and weapons
		Soldier legolas = new Soldier("Legolas", brokenBow, fist, head, shoulders, body, hands, feet);
		attackers.addSoldier(legolas);
		attackers.changeFormation(legolas, 1, 2);
		
		Soldier newAragorn = new Soldier(aragorn);
		attackers.addSoldier(newAragorn);
		attackers.changeFormation(newAragorn, 1, 1);
		Soldier newLegolas = new Soldier(legolas);
		attackers.addSoldier(newLegolas);
		attackers.changeFormation(newLegolas, 0, 1);
		
		return attackers;
	} // end createAttackers()
	
	private static Squad createDefenders() throws IOException {
		// Create the defending squad
		Squad defenders = new Squad();
		
		// Constructor: ARMOR_TYPE, NAME, PRICE, DEFENSE, ATTACK, ACCURACY, SPEED
		Equipment head = new Equipment("Head", Equipment.ArmorType.HELMET, 0, 0, 0, 0, 0);
		Equipment shoulders = new Equipment("Shoulders", Equipment.ArmorType.SHOULDERGUARDS, 0, 0, 0, 0, 0);
		Equipment body = new Equipment("Body", Equipment.ArmorType.BODY_ARMOR, 0, 0, 0, 0, 0);
		Equipment hands = new Equipment("Hands", Equipment.ArmorType.GAUNTLETS, 0, 0, 0, 0, 0);
		Equipment feet = new Equipment("Feet", Equipment.ArmorType.BOOTS, 0, 0, 0, 0, 0);
		Equipment clumsyClothHelmet = new Equipment("Clumsy Cloth Helmet", Equipment.ArmorType.HELMET, 10, 2, 0, -1, -1);
		Equipment clumsyClothShoulderguards = new Equipment("Clumsy Cloth Shoulderguards", Equipment.ArmorType.SHOULDERGUARDS, 10, 2, 0, -1, -1);

		// Constructor: WEAPON_TYPE, TWO_HANDED, NAME, PRICE, DEFENSE, ATTACK,
		// ACCURACY, SPEED
		Weapon fist = new Weapon("Fist",Weapon.WeaponType.FIST, 0, 0, 0, 0, 0);
		Weapon woodenSword = new Weapon("Wooden Sword", Weapon.WeaponType.SWORD_ONE, 10, 0, 3, 0, 0);
		Weapon woodenAxe = new Weapon("Wooden Axe", Weapon.WeaponType.AXE_ONE, 10, 0, 3, 0, 0);
		Weapon woodenLance = new Weapon("Wooden Lance", Weapon.WeaponType.LANCE_ONE, 10, 0, 3, 0, 0);
		Weapon brokenBow = new Weapon("Broken Bow", Weapon.WeaponType.BOW, 10, 0, 3, 0, 0);
		Weapon gladiatorBlade = new Weapon("Gladiator Blade", Weapon.WeaponType.SWORD_TWO, 1000, 0, 50, 0, 0);
		
		// Create the first soldier and add image, equipment, and weapons
		Soldier swordPaladin = new Soldier("Sword Paladin", woodenSword, fist, 
							clumsyClothHelmet, shoulders, body, hands, feet);
		defenders.addSoldier(swordPaladin);
		defenders.changeFormation(swordPaladin, 0, 2);
		
		// Create the second soldier and add image, equipment, and weapons
		Soldier axePaladin = new Soldier("Axe Paladin", woodenAxe, fist, 
							clumsyClothHelmet, clumsyClothShoulderguards, body, hands, feet);
		defenders.addSoldier(axePaladin);
		defenders.changeFormation(axePaladin, 0, 1);
		
		// Create the third soldier and add image, equipment, and weapons
		Soldier lancePaladin = new Soldier("Lance Paladin", woodenLance, fist, 
				clumsyClothHelmet, clumsyClothShoulderguards, body, hands, feet);
		defenders.addSoldier(lancePaladin);
		defenders.changeFormation(lancePaladin, 0, 3);
		
		// Create the fourth soldier and add image, equipment, and weapons
		Soldier archer = new Soldier("Archer", brokenBow, fist,
				head, shoulders, body, hands, feet);
		defenders.addSoldier(archer);
		defenders.changeFormation(archer, 1, 1);
		
		return defenders;
	} // end createDefenders()
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// Setup the two formations
		Squad attackers = createAttackers();
		Squad defenders = createDefenders();

		// Setup the window content
		Battle content = new Battle(attackers.getFormation(), defenders.getFormation());
		
		// Setup the window
		JFrame window = new JFrame("Battle");
		window.setContentPane(content);
		window.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation((screenSize.width - window.getWidth()) / 2, (screenSize.height - window.getHeight()) / 2);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
		
		myBoard.prepareNextTurn();
	} // end main()

	/**
	 * This panel displays a 160-by-160 checkerboard pattern with a 2-pixel
	 * black border. It is assumed that the size of the canvas is set to exactly
	 * 164-by-164 pixels. This class does the work of letting the users play
	 * checkers, and it displays the checkerboard.
	 */
	private class Board extends JPanel implements ActionListener, MouseListener {
		private Soldier[][] boardCopy;
		private Soldier[][] board;
		private Soldier currentAttacker;
		private Soldier currentDefender;
		private Image goodArcher;
		private Image goodPaladin;
		private Image badArcher;
		private Image badPaladin;
		private boolean userTurn;

		/**
		 * Constructor. Create the buttons and label. Listens for mouse clicks
		 * and for clicks on the buttons. Create the board and start the first
		 * game.
		 * @throws IOException 
		 */
		Board(Soldier[][] attackers, Soldier[][] defenders) throws IOException {
			addMouseListener(this);
			userTurn = false;
			
			// Setup the Buttons
			myResignButton.addActionListener(this);
			myRestartButton.addActionListener(this);
			myAttackButton.addActionListener(this);
			myDefendButton.addActionListener(this);
			
			// Initialize the Images
			goodArcher = ImageIO.read(new File("Images/goodArcher.png"));
			goodPaladin = ImageIO.read(new File("Images/goodPaladin.png"));
			badArcher = ImageIO.read(new File("Images/badArcher.png"));
			badPaladin = ImageIO.read(new File("Images/badPaladin.png"));
			goodArcher = goodArcher.getScaledInstance(50, 50, 0);
			goodPaladin = goodPaladin.getScaledInstance(50, 50, 0);
			badArcher = badArcher.getScaledInstance(50, 50, 0);
			badPaladin = badPaladin.getScaledInstance(50, 50, 0);
			
			// Setup the board formations
			boardCopy = new Soldier[5][5];
			board = setupBoard(attackers, defenders);
		} // end Board() constructor
		
		private void toggleButtons(boolean activate) {
			myAttackButton.setEnabled(activate);
			myDefendButton.setEnabled(activate);
		} // end toggleButtons()

		public void prepareNextTurn() {
			userTurn = false;			
			toggleButtons(false);
			
			int currentRow = -1;
			int currentCol = -1;
			int speed = 0;
			
			// Find the next soldier
			for (int row = 0; row < 5; row++) {
				for (int col = 0; col < 5; col++) {
					if (board[row][col] != null && board[row][col].getHealth() > 0 && board[row][col].getTotalSpeed() >= speed) {
						speed = board[row][col].getTotalSpeed();
						currentRow = row;
						currentCol = col;
					}
				}
			}
						
			// Save soldier
			currentDefender = null;
			currentAttacker = board[currentRow][currentCol];
			currentAttacker.setDefensiveStance(false);
			if (currentRow < 2)
				updateMessage(currentAttacker.getName(), true);
			else
				updateMessage(currentAttacker.getName(), false);
			
			// Update the soldiers' speed
			for (int row = 0; row < 5; row++) {
				for (int col = 0; col < 5; col++) {
					if (board[row][col] != null && board[row][col].getHealth() > 0) {
						if (row == currentRow && col == currentCol) {
							board[row][col].setTotalSpeed(0);
						} else {
							board[row][col].setTotalSpeed(board[row][col].getTotalSpeed() + boardCopy[row][col].getTotalSpeed());
						}
					}
				}
			}

			if (currentRow < 2) {
				executeEnemyTurn();
			} else {
				executeAllyTurn();
			}
		} // end prepareNextTurn()
		
		private void updateMessage(String message, boolean goToSleep) {
			myMessage.setText(message);
			myMessage.paintImmediately(myMessage.getVisibleRect());
			paintImmediately(0, 0, getSize().width, getSize().height);
			if (goToSleep)
				sleep(0);
		} // end updateMessage()

		private void executeEnemyTurn() {
			boolean attacked = false;
			
			// Make enemy attack first ally it encounters
			for (int row = 3; row < 5; row++) {
				for (int col = 0; col < 5; col++) {
					if (board[row][col] != null && board[row][col].getHealth() > 0) {
						currentDefender = board[row][col];
						doEnemyAttack();
						attacked = true;
						break;
					}
				}
				if (attacked) {
					break;
				}
			}
		} // end executeEnemyTurn()
		
		private void executeAllyTurn() {
			myDefendButton.setEnabled(true);
			userTurn = true;
		} // end executeAllyTurn()
		
		private void sleep(int milliseconds) {
			try {
			    Thread.sleep(milliseconds); // 1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		} // end sleep()

		private Soldier[][] setupBoard(Soldier[][] attackers, Soldier[][] defenders) {
			Soldier[][] board = new Soldier[5][5];

			for (int row = 0; row < 5; row++) {
				for (int col = 0; col < 5; col++) {
					if (row == 0) {
						board[row][col] = defenders[1][col];
					} else if (row == 1) {
						board[row][col] = defenders[0][col];
					} else if (row == 3 || row == 4) {
						board[row][col] = attackers[row - 3][col];
					}
					if (board[row][col] != null) {
						boardCopy[row][col] = new Soldier(board[row][col]);
					}
				}
			}
						
			return board;
		} // end setupBoard

		/**
		 * Respond to user's click on one of the two buttons.
		 */
		public void actionPerformed(ActionEvent event) {
			Object src = event.getSource();
			if (src == myRestartButton)
				doRestartBattle();
			else if (src == myResignButton)
				doResignBattle();
			else if (src == myAttackButton)
				doAttack();
			else if (src == myDefendButton)
				doDefend();
		} // end actionPerformed()

		/**
		 * Restart the battle
		 */
		private void doRestartBattle() {
			updateMessage("Restarting battle...", false);

			for (int row = 0; row < 5; row++) {
				for (int col = 0; col < 5; col++) {
					if (board[row][col] != null) {
						board[row][col] = new Soldier(boardCopy[row][col]);
					}
				}
			}

			myRestartButton.setEnabled(false);
			myResignButton.setEnabled(true);
			
			updateMessage("Battle restarted.", true);
			prepareNextTurn();
		} // end doRestartBattle

		/**
		 * Current player resigns. Game ends. Opponent wins.
		 */
		private void doResignBattle() {
			updateMessage("You have lost the battle!", true);
			System.exit(0);
		} // end doResignBattle
		
		private void doEnemyAttack() {			
			int result = currentAttacker.attack(currentDefender);
			
			if (result == 0) {
				updateMessage(currentAttacker.getName() + " missed!", true);
				myRestartButton.setEnabled(true);
				prepareNextTurn();
			} else if (result > 0) {
				updateMessage(currentDefender.getName() + " lost " + result + " health points!", true);
				myRestartButton.setEnabled(true);
				prepareNextTurn();
			} else if (result < 0) {
				updateMessage("Oh no! Ally " + currentDefender.getName() + " has been defeated!", true);
				
				boolean alliesAlive = false;

				// Check if allies defeated
				for (int row = 3; row < 5; row++) {
					for (int col = 0; col < 5; col++) {
						if (board[row][col] != null && board[row][col].getHealth() > 0) {
							alliesAlive = true;
							break;
						}
					}
					if (alliesAlive == true) {
						break;
					}
				}
				
				if (!alliesAlive) {
					toggleButtons(false);
					updateMessage("You have lost the battle! Restart?", true);
				} else {
					myRestartButton.setEnabled(true);
					prepareNextTurn();
				}
			}
		} // end doEnemyAttack()
		
		private void doAttack() {
			int result = currentAttacker.attack(currentDefender);
			
			if (result == 0) {
				updateMessage(currentAttacker.getName() + " missed!", true);
				myRestartButton.setEnabled(true);
				prepareNextTurn();
			} else if (result > 0) {
				updateMessage("Hit " + currentDefender.getName() + " for " + result + " health points!", true);
				myRestartButton.setEnabled(true);
				prepareNextTurn();
			} else if (result < 0) {
				updateMessage(currentDefender.getName() + " has been defeated!", true);
				
				boolean enemiesAlive = false;

				// Check if enemies defeated
				for (int row = 0; row < 2; row++) {
					for (int col = 0; col < 5; col++) {
						if (board[row][col] != null && board[row][col].getHealth() > 0) {
							enemiesAlive = true;
							break;
						}
					}
					if (enemiesAlive == true) {
						break;
					}
				}
				
				if (!enemiesAlive) {
					toggleButtons(false);
					updateMessage("You have won the battle!", true);
				} else {
					myRestartButton.setEnabled(true);
					prepareNextTurn();
				}
			}
		} // end doAttack()
		
		private void doDefend() {
			currentAttacker.setDefensiveStance(true);
			
			int defense = currentAttacker.getTotalDefense()*2;

			updateMessage(currentAttacker.getName() + "'s entered defensive stance! Defense: " + defense, true);
			
			myRestartButton.setEnabled(true);
			prepareNextTurn();
		} // end doDefend()

		/**
		 * This is called by mousePressed() when a player clicks on the square
		 * in the specified row and col. It has already been checked that a game
		 * is, in fact, in progress.
		 */
		private void doClickSquare(int row, int col) {
			Soldier soldier = board[row][col];
			
			if (soldier != null && soldier.getHealth() > 0) {
				if (row < 2) {
					currentDefender = soldier;
				}

				int defense = soldier.getTotalDefense();
				if (soldier.defensiveStance() == true) {
					defense *= 2;
				}

				updateMessage(soldier.getName() + 
						" - Health: " + soldier.getHealth() + 
						" Attack: " + soldier.getTotalAttack() +
						" Defense: " + defense + 
						" Accuracy: " + soldier.getTotalAccuracy() + "%", false);
			} else {
				updateMessage("Attack your enemy or enter defensive mode.", false);
			}
		} // end doClickSquare()
		
		/**
		 * Draw checkerboard pattern in gray and lightGray. Draw the checkers.
		 * If a game is in progress, hilite the legal moves.
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);	
			
			// Draw a two-pixel black border around the edges of the canvas.
			g.setColor(Color.black);
			g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
			g.drawRect(1, 1, getSize().width - 3, getSize().height - 3);
			
			for (int row = 0; row < 5; row++) {
				for (int col = 0; col < 5; col++) {
					if (board[row][col] != null && board[row][col].getHealth() > 0) {
						// If this slot has a soldier with health
						
						// Draw the soldier's health
						double healthPercentage = (double)board[row][col].getHealth() / (double)boardCopy[row][col].getHealth();
						if (healthPercentage == 1) {
							g.setColor(Color.GREEN);
							g.fillRect(2 + col * 50,  2 + row * 50,  50,  50);
						} else {
							g.setColor(Color.RED);
							g.fillRect(2 + col * 50,  2 + row * 50,  50, (int)((1-healthPercentage)*50));
							g.setColor(Color.GREEN);
							g.fillRect(2 + col * 50, (2 + row * 50)+(int)((1-healthPercentage)*50), 50, (int)(healthPercentage*50));
						}
						
						// Add the soldier's image
						if (row < 3) {
							if (board[row][col].getWeapons()[0].getWeaponType() == Weapon.WeaponType.BOW) {
								g.drawImage(badArcher,  5 + (col*50), 5 + (row*50), this);
							} else {
								g.drawImage(badPaladin,  5 + (col*50), 5 + (row*50), this);
							}
						} else {
							if (board[row][col].getWeapons()[0].getWeaponType() == Weapon.WeaponType.BOW) {
								g.drawImage(goodArcher,  5 + (col*50), 5 + (row*50), this);
							} else {
								g.drawImage(goodPaladin,  5 + (col*50), 5 + (row*50), this);
							}
						}
						
						// Mark who's turn it is
						if (board[row][col] == currentAttacker) {
							boolean validDefenderSelected = false;
							
							// Paint the blue on the current attacker
							g.setColor(Color.BLUE);
							g.drawRect(2 + col * 50, 2 + row * 50, 50, 50);
							g.drawRect(3 + col * 50, 3 + row * 50, 48, 48);
							g.drawRect(4 + col * 50,  4 + row * 50, 46, 46);
							
							// Figure out who is vulnerable
							if ( (row == 3 && currentAttacker.getReach() == 1) || (row == 4 && currentAttacker.getReach() == 2) ) {								
								// Iterate through columns, left to right
								for (int column = 0; column < 5; column++) {
									// If soldier in front row, paint vulnerability.
									// Else, check if another soldier is behind.
									if (board[1][column] != null && board[1][column].getHealth() > 0) {
										g.setColor(Color.RED);
										g.drawRect(2 + column * 50,  2 + 1 * 50, 50, 50);
										
										// If this is the selected defender
										if (currentDefender == board[1][column]) {
											validDefenderSelected = true;
											g.drawRect(3 + column * 50, 3 + 1 * 50, 48, 48);
											g.setColor(Color.BLACK);
											g.drawRect(4 + column * 50,  4 + 1 * 50, 46, 46);
										}
									} else if (board[0][column] != null && board[0][column].getHealth() > 0) {
										g.setColor(Color.RED);
										g.drawRect(2 + column * 50,  2 + 0 * 50, 50, 50);
										
										// If this is the selected defender
										if (currentDefender == board[0][column]) {
											validDefenderSelected = true;
											g.drawRect(3 + column * 50, 3 + 0 * 50, 48, 48);
											g.setColor(Color.BLACK);
											g.drawRect(4 + column * 50,  4 + 0 * 50, 46, 46);
										}
									}
								}
							} else if (row == 3 && currentAttacker.getReach() == 2) {								
								// Iterate through columns, left to right
								for (int rowumn = 0; rowumn < 2; rowumn++) {
									for (int column = 0; column < 5; column++) {
										if (board[rowumn][column] != null && board[rowumn][column].getHealth() > 0) {
											g.setColor(Color.RED);
											g.drawRect(2 + column * 50,  2 + rowumn * 50, 50, 50);
											
											// If this is the selected defender
											if (currentDefender != null && currentDefender == board[rowumn][column]) {
												validDefenderSelected = true;
												g.drawRect(3 + column * 50, 3 + rowumn * 50, 48, 48);
												g.setColor(Color.BLACK);
												g.drawRect(4 + column * 50,  4 + rowumn * 50, 46, 46);
											}
										}
									}
								}
							} else if (row == 4 && currentAttacker.getReach() == 1) {								
								if (board[3][col] == null || board[3][col].getHealth() < 1) {
									// Iterate through columns, left to right
									for (int column = 0; column < 5; column++) {
										// If soldier in front row, paint vulnerability.
										// Else, check if another soldier is behind.
										if (board[1][column] != null && board[1][column].getHealth() > 0) {
											g.setColor(Color.RED);
											g.drawRect(2 + column * 50,  2 + 1 * 50, 50, 50);
											
											// If this is the selected defender
											if (currentDefender == board[1][column]) {
												validDefenderSelected = true;
												g.drawRect(3 + column * 50, 3 + 1 * 50, 48, 48);
												g.setColor(Color.BLACK);
												g.drawRect(4 + column * 50,  4 + 1 * 50, 46, 46);
											}
										} else if (board[0][column] != null && board[0][column].getHealth() > 0) {
											g.setColor(Color.RED);
											g.drawRect(2 + column * 50,  2 + 0 * 50, 50, 50);
											
											// If this is the selected defender
											if (currentDefender == board[0][column]) {
												validDefenderSelected = true;
												g.drawRect(3 + column * 50, 3 + 0 * 50, 48, 48);
												g.setColor(Color.BLACK);
												g.drawRect(4 + column * 50,  4 + 0 * 50, 46, 46);
											}
										}
									}
								}
							}
							
							// If valid defender selected, activate attack button
							if (validDefenderSelected) {
								myAttackButton.setEnabled(true);
							} else {
								myAttackButton.setEnabled(false);
							}
						} else {
							g.setColor(Color.BLACK);
							g.drawRect(2 + col * 50, 2 + row * 50, 50, 50);
						}
					} else {
						// If this slot has no soldier with health

						if (row == 2) {
							g.setColor(Color.GRAY);
						} else if (row % 2 == col % 2) {
							g.setColor(Color.LIGHT_GRAY);
						} else {
							g.setColor(Color.DARK_GRAY);
						}
						g.fillRect(2 + col * 50, 2 + row * 50, 50, 50);
						if (row != 2) {
							g.setColor(Color.BLACK);
							g.drawRect(2 + col * 50, 2 + row * 50, 50, 50);
						}
					}
				}
			}
		} // end paintComponent()

		/**
		 * Respond to a user click on the board. If no game is in progress, show
		 * an error message. Otherwise, find the row and column that the user
		 * clicked and call doClickSquare() to handle it.
		 */
		public void mousePressed(MouseEvent evt) {
		}

		public void mouseReleased(MouseEvent evt) {
		}

		public void mouseClicked(MouseEvent event) {
			int col = (event.getX() - 2) / 50;
			int row = (event.getY() - 2) / 50;
			if (col >= 0 && col < 5 && row >= 0 && row < 5 && userTurn == true) {
				doClickSquare(row, col);
			}
		}

		public void mouseEntered(MouseEvent evt) {
		}

		public void mouseExited(MouseEvent evt) {
		}
	} // end Board class
}
