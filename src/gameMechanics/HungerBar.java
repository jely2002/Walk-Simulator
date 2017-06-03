package gameMechanics;

import entities.Player;

public class HungerBar {
	
	public static int getHunger(Player player){
		return calcHunger(player.getDistanceTraveled());
	}
	
	private static int calcHunger(float dist){
		if(dist <= 300){
			return 1;
		} else if (dist <= 500){
			return 2;
		} else if (dist <= 700){
			return 3;
		} else if (dist <= 900){
			return 4;
		} else if (dist <= 1200){
			return 5;
		} else {
			return 6;
		}
	}
	

}
