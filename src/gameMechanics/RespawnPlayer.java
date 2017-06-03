package gameMechanics;

import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.util.vector.Vector3f;

import entities.Player;

public class RespawnPlayer {
	
	private static boolean hasSetPos = false;
	private static int time = 0;
	
	public static void respawn(Player player){
		player.setRespawning(true);
		player.setCurrentSpeed(0);
		player.setCurrentTurnSpeed(0);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
			    checkTime(new Vector3f(1000, -5.2519526f, -1350), player);
			  }
			}, 1000);
	}
	
	private static void setPos(Vector3f pos, Player player){
		if(!hasSetPos){
		player.setPosition(new Vector3f(1000, -6.2519526f, -1350));
		}
		hasSetPos = true;
	}
	
	private static void checkTime(Vector3f pos, Player player){
		if(time >= 5){
		hasSetPos = false;
		player.setRespawning(false);
	    setPos(new Vector3f(1000, -5.2519526f, -1350), player);
	    player.setDistanceTraveled(0);
	    time = 0;
		} else {
			time += 1;
		}
	}

}
