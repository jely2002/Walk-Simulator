package entities;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private boolean first_person = false;
	
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch = 15;
	private float yaw;
	private float roll;
	
	private Player player;

	public Camera(Player player){
		this.player = player;
	}
	
	private void checkInputs(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F2)){
			if(first_person){
				first_person = false;
				distanceFromPlayer = 50;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F1)){
			if(!first_person){
				first_person = true;
			}
		}
	}
	
	public void move(){
		checkInputs();
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
		yaw %= 360;
	}

	public void invertPitch(){
		this.pitch = -pitch;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	public float getDistanceFromPlayer() {
		return distanceFromPlayer;
	}

	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticDistance + 10;
	}

	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch + 4)));
	}

	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch + 4)));
	}
	
	private void calculateZoom(){
		float zoomLevel = Mouse.getDWheel() *0.1f;
		distanceFromPlayer -= zoomLevel;
		if(first_person){
			distanceFromPlayer = 0;
			return;
		} else {
			if (distanceFromPlayer < 5) {
				distanceFromPlayer = 5;	
			} else if (distanceFromPlayer > 85) {
				distanceFromPlayer = 85;
			}
		}
	}
	
	private void calculateAngleAroundPlayer(){
		if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)){
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
	
	private void calculatePitch(){
		if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)){
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
			if (pitch < 0) {
				pitch = 0;
			} else if (pitch > 90) {
				pitch = 90;
			}
		}
	}

}
