package engineTester;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import audio.AudioMaster;
import audio.Source;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
//import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;
import webIntegration.loginForm;
import webIntegration.variableSender;

public class MainGameLoop {

	public static void main(String[] args) {

		boolean wasPressed = false;

		String uid = loginForm.getUsername();

		System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());

		DisplayManager.createDisplay();
		Loader loader = new Loader();

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> normalMapEntities = new ArrayList<Entity>();

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);

		ModelTexture plantTextureAtlas = new ModelTexture(loader.loadTexture("diffuse"));
		plantTextureAtlas.setNumberOfRows(2);
		
		TexturedModel pine = new TexturedModel(OBJLoader.loadObjModel("pine", loader), new ModelTexture(loader.loadTexture("pine")));
		TexturedModel lamp = new TexturedModel(OBJLoader.loadObjModel("lamp", loader), new ModelTexture(loader.loadTexture("lamp")));
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);
		//TexturedModel plant = new TexturedModel(OBJLoader.loadObjModel("plant", loader), plantTextureAtlas);
		TexturedModel boulder = new TexturedModel(NormalMappedObjLoader.loadOBJ("boulder", loader), new ModelTexture(loader.loadTexture("boulder")));
		boulder.getTexture().setShineDamper(5);
		boulder.getTexture().setReflectivity(0.5f);
		boulder.getTexture().setNormalMap(loader.loadTexture("boulderNormal"));
		pine.getTexture().setHasTransparency(true);
		lamp.getTexture().setUseFakeLighting(true);
		fern.getTexture().setHasTransparency(true);
		fern.getTexture().setUseFakeLighting(true);

		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
		List<Terrain> terrains = new ArrayList<Terrain>();
		terrains.add(terrain);

		RawModel PlayerModel = OBJLoader.loadObjModel("person", loader);
		TexturedModel PlayerTexturedModel = new TexturedModel(PlayerModel, new ModelTexture(loader.loadPlayerTexture("skin")));

		Player player = new Player(PlayerTexturedModel, new Vector3f(1000, 25, -1350), 0, 0, 0, 1);
		
		Random random = new Random(3862981);
		Random random2 = new Random(6438976);
		for (int i = 0; i < 900; i++) {
			if (i % 3 == 0) {
				float x = random.nextFloat() * 1800;
				float z = random.nextFloat() * -1800;
				float y = terrain.getHeightOfTerrain(x, z);
				if(y > -15){
					//entities.add(new Entity(plant, random.nextInt(4), new Vector3f(random.nextFloat() * 1800, y, random.nextFloat() * - 1800), 0,random.nextFloat() * 360, 0, 6));
					entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0,random.nextFloat() * 360, 0, 3));
				}
			}
			if (i % 2 == 0) {
				float x1 = random.nextFloat() * 1800;
				float z1 = random.nextFloat() * -1800;
				float y1 = terrain.getHeightOfTerrain(x1, z1);
				if(y1 > -20){
					entities.add(new Entity(pine, new Vector3f(x1, y1, z1), 0,  random.nextFloat() * 360, 0, random.nextFloat() * 0.8f + 5));
				}
			}
		}
		for (int i = 0; i < 120; i++){
			float x = random2.nextFloat() * 1800;
			float z = random2.nextFloat() * -1800;
			float y = terrain.getHeightOfTerrain(x, z) + 3f;
			if(y > -25 && y < -5){
				normalMapEntities.add(new Entity(boulder, new Vector3f(x, y, z),0,0,0,random2.nextFloat() * 1.2f + 2));
			}
		}


		MasterRenderer renderer = new MasterRenderer(loader);

		Camera camera = new Camera(player);  

		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light(new Vector3f(0, 10000, 7000), new Vector3f(1, 1, 1));
		lights.add(sun);
		//lights.add(new Light(new Vector3f(984, 20, -1100), new Vector3f(2, 2, 2), new Vector3f(1,0.002f,0.002f)));

		//MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);

		entities.add(new Entity(lamp, new Vector3f(984,-6,-1350),0,100,0,2));

		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiTexture logo = new GuiTexture(loader.loadTexture("walksimulator"), new Vector2f(-0.8f, 0.93f), new Vector2f(0.20f, 0.0625f));
		GuiTexture underwater = new GuiTexture(loader.loadTexture("underwater"), new Vector2f(-0f, -1f), new Vector2f(1.2f, 2.2f));
		GuiRenderer guiRenderer = new GuiRenderer(loader);

		// ======== AUDIO ========
		AudioMaster.init();
		AudioMaster.setListenerData(0, 0, 0);

		int backgroundambientbuffer = AudioMaster.loadSound("res/audio/background-ambient.wav");
		Source backgroundambient = new Source();
		backgroundambient.play(backgroundambientbuffer);
		backgroundambient.setVolume(0.08f);
		backgroundambient.setLooping(true);

		int footstepsbuffer = AudioMaster.loadSound("res/audio/footsteps.wav");
		Source footsteps = new Source();
		footsteps.setLooping(true);
		
		int underwaterbuffer = AudioMaster.loadSound("res/audio/underwater.wav");
		Source underwaterSFX = new Source();
		underwaterSFX.setLooping(true);
		// ======= END AUDIO =======
		
		WaterFrameBuffers fbos = new WaterFrameBuffers();

		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), fbos);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water = new WaterTile(900, -900, -20);
		waters.add(water);

		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			if (!(Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_S)  || Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_D)) && wasPressed){
				wasPressed = false;
				footsteps.pause();
			} else if ((Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_S)  || Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_D)) && !wasPressed){
				wasPressed = true;
				if(footsteps.isHasBeenPaused()){
					footsteps.continuePlaying();
				} else {
					footsteps.play(footstepsbuffer);
				}
			}
			if(camera.getPosition().y <= -20 && camera.getDistanceFromPlayer() < 4.9f){
				guis.add(underwater);
				if(underwaterSFX.isHasBeenPaused()){
					underwaterSFX.continuePlaying();
				} else if (!underwaterSFX.isPlaying()) {
				underwaterSFX.play(underwaterbuffer);
				}
			} else if(camera.getPosition().y > -20){
				underwaterSFX.pause();
			}
			
			guis.add(logo);
			player.move(terrain);
			camera.move();
			
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			fbos.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			//renderer.processEntity(player);
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, -water.getHeight()+1f));
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			fbos.bindRefractionFrameBuffer();
			renderer.processEntity(player);
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, water.getHeight()+1f));
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			fbos.unbindCurrentFrameBuffer();
			
			renderer.processEntity(player);
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 0, 0, 0));
			waterRenderer.render(waters, camera, sun);
			guiRenderer.render(guis);
			guis.remove(underwater);
			guis.remove(logo);
			
			DisplayManager.updateDisplay();
		}
		fbos.cleanUp();
		backgroundambient.delete();    
		AudioMaster.cleanUp();
		waterShader.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		Float dist = player.getDistanceTraveled() * 0.12f;
		variableSender.sendVariable(uid, Float.toString(dist));
		System.exit(0);
	}

}
