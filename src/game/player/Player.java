package game.player;
import game.Animus;
import game.Game;
import game.Level;
import game.Space;
import game.enums.ItemType;
import game.enums.SpaceType;

public class Player extends Animus{
	Inventory inventory;
	int inventoryIndex;
	Wounds body;
	boolean isAlive;
	int chance;
	
	public Player(int xCo, int yCo) {
		hp = 100;
		speed = 50;
		dmg = 20;
		currentLevel = 0;
		inventory = new Inventory();
		body = new Wounds();
		inventoryIndex = 0;
		aspect = SpaceType.HERO;
		x = xCo;
		y = yCo;
		isAlive = true;
		chance = 0;

	}

	@Override
	public void move(int direction) {
		chance += (int) (Math.random() * 3);
		Space[][] map = Game.getLevel(currentLevel).getDesign();

		switch(direction) {
		case 0:
			break;

			//move up
		case 1:
			if(map[y - 1][x].getSpace() != SpaceType.WALL) {
				y--;
				if(chance > 6) {
					hp--;
					chance = 0;
				}
				Game.setPlayerTurn(false); 
			} else {
				Game.addBuffer("North: you smack into a wall.");
			}
			break;

			//move right
		case 2:
			if(map[y][x + 1].getSpace() != SpaceType.WALL) {
				x++;
				if(chance > 6) {
					hp--;
					chance = 0;
				}
				Game.setPlayerTurn(false); 
			} else {
				Game.addBuffer("Only a high reef to the east.");
			}
			break;

			//move down
		case 3:
			if(map[y + 1][x].getSpace() != SpaceType.WALL) {
				y++;
				if(chance > 6) {
					hp--;
					chance = 0;
				}
				Game.setPlayerTurn(false); 
			} else {
				Game.addBuffer("There's no way south.");
			}
			break;

			//move left
		case 4:
			if(map[y][x - 1].getSpace() != SpaceType.WALL) {
				x--;
				if(chance > 6) {
					hp--;
					chance = 0;
				}
				Game.setPlayerTurn(false); 
			} else {
				Game.addBuffer("Your manifest destiny falls flat.");
			}
			break;

			//level load
		case 5:
			if(map[y][x].getSpace() == SpaceType.EXIT && currentLevel < 25) {
				currentLevel++;
				Game.playerLevel++;
				Game.getDungeon()[currentLevel] = new Level(10, x, y, currentLevel);
				hp = 100;
				Game.setPlayerTurn(false); 
				Game.addBuffer("You find a crevasse opening.");
			} else if (currentLevel == 24){
				currentLevel++;
				Game.playerLevel++;
				hp = 100;
				Game.setPlayerTurn(false);
				Game.setPlayerWin(true);
				Game.addBuffer("The water is still down here.");
			} else {
				int exit = (int) Math.random() * 2;
				switch(exit) {
				case 0:
					Game.addBuffer("You just want to go home.");
					break;
				case 1:
					Game.addBuffer("That's not the way.");
					break;
				case 2:
					Game.addBuffer("There's no exit here.");
					break;
				}
			}
			break;

			//item get
		case 6:
			if(map[y][x].getItem() != null) {
				inventory.addItem(map[y][x].getItem().getType());
				map[y][x].setItem(null);
			} else {
				Game.addBuffer("There's nothing there.");
			}

		}	

		if(hp == 0) {
			isAlive = false;
		}
		
		if(map[y][x].getSpace() == SpaceType.SHOCK) {
			Game.hero.getBody().addHit(SpaceType.EEL);
			Game.addBuffer("In fear, the eel flashes static.");
		}
	}

	public String vitals() {
		String output = "Player Vitals\n";
		output += "--------------------------\n";
		output += "Level: " + (currentLevel + 1) + "\n";
		output += "Oxygen: " + hp + "%\n";
		output += "X: " + x + " Y: " + y + "\n";
		output += "--------------------------\n";

		return output;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public boolean getAlive() {
		return isAlive;
	}

	public Wounds getBody() {
		return body;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}
}
