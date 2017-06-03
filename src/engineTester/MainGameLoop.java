package engineTester;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
import entities.Food;
import entities.Light;
import entities.Player;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import gameMechanics.HungerBar;
import gameMechanics.RespawnPlayer;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticleTexture;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;
import webIntegration.variableSender;

public class MainGameLoop {

	public static void main(String[] args) {

		boolean wasPressed = false;

		//Init START
		System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		TextMaster.init(loader);
		
		//Create lists START
		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> normalMapEntities = new ArrayList<Entity>();
		//Create lists END
		
		//Player START
		RawModel PlayerModel = OBJLoader.loadObjModel("person", loader);
		TexturedModel PlayerTexturedModel = new TexturedModel(PlayerModel, new ModelTexture(loader.loadPlayerTexture("skin")));
		Random random4 = new Random(7643567);
		float x4 = random4.nextFloat() * 1000;
		float z4 = random4.nextFloat() * -1000;
		Player player = new Player(PlayerTexturedModel, new Vector3f(x4, 30, z4), 0, 0, 0, 1);
		entities.add(player);
		//Player END		
		
		//Camera START
		Camera camera = new Camera(player); 
		//Camera END
		
		MasterRenderer renderer = new MasterRenderer(loader, camera);
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		//Init END

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



		//Terrains START		
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
 
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
 
        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
        List<Terrain> terrains = new ArrayList<Terrain>();
        terrains.add(terrain);
		//Terrains END


		//Texture atlases START
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);
		ModelTexture plantTextureAtlas = new ModelTexture(loader.loadTexture("diffuse"));
		plantTextureAtlas.setNumberOfRows(2);
		//Texture atlases END



		//Models START
		TexturedModel pine = new TexturedModel(OBJLoader.loadObjModel("pine", loader), new ModelTexture(loader.loadTexture("pine")));
		TexturedModel lamp = new TexturedModel(OBJLoader.loadObjModel("lamp", loader), new ModelTexture(loader.loadTexture("lamp")));
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);
		TexturedModel boulder = new TexturedModel(NormalMappedObjLoader.loadOBJ("boulder", loader), new ModelTexture(loader.loadTexture("boulder")));
		boulder.getTexture().setShineDamper(5);
		boulder.getTexture().setReflectivity(0.5f);
		boulder.getTexture().setNormalMap(loader.loadTexture("boulderNormal"));
		pine.getTexture().setHasTransparency(true);
		lamp.getTexture().setUseFakeLighting(true);
		fern.getTexture().setHasTransparency(true);
		fern.getTexture().setUseFakeLighting(true);
		//Models END	



		//Create and add entities START
		Random random = new Random(3862981);
		Random random2 = new Random(6438976);
		Random random3 = new Random(4829529);
		for (int i = 0; i < 900; i++) {
			if (i % 3 == 0) {
				float x = random.nextFloat() * 1800;
				float z = random.nextFloat() * -1800;
				float y = terrain.getHeightOfTerrain(x, z);
				if(y > 5){
					//entities.add(new Entity(plant, random.nextInt(4), new Vector3f(random.nextFloat() * 1800, y, random.nextFloat() * - 1800), 0,random.nextFloat() * 360, 0, 6));
					entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0,random.nextFloat() * 360, 0, 3));
				}
			}
			if (i % 2 == 0) {
				float x1 = random.nextFloat() * 1800;
				float z1 = random.nextFloat() * -1800;
				float y1 = terrain.getHeightOfTerrain(x1, z1);
				if(y1 > 0){
					entities.add(new Entity(pine, new Vector3f(x1, y1, z1 - 5), 0,  random.nextFloat() * 360, 0, random.nextFloat() * 0.8f + 5));
				}
			}
		}
		for (int i = 0; i < 100; i++){
			float x2 = random2.nextFloat() * 1800;
			float z2 = random2.nextFloat() * -1800;
			float y2 = terrain.getHeightOfTerrain(x2, z2) + 3f;
			if(y2 > -5 && y2 < 5){
				normalMapEntities.add(new Entity(boulder, new Vector3f(x2, y2, z2),0,0,0,random2.nextFloat() * 1.2f + 2));
			}
		}
		
		float x3 = random3.nextFloat() * 1000;
		float z3 = random3.nextFloat() * -1000;
		float y3 = terrain.getHeightOfTerrain(x3, z3);
		entities.add(new Entity(lamp, new Vector3f(x3,y3,z3),0,100,0,2));
		//Create and add entities END



		//Fonts START
		FontType font = new FontType(loader.loadFontTexture("grafika"), new File("res/grafika.fnt"));
		GUIText text = new GUIText("alpha-V1.9.6", 0.8f, font, new Vector2f(-0.44f, 0.045f), 1f, true);
		text.setColour(0, 0, 0);
		//Fonts END



		//Lights START
		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light(new Vector3f(1000000, 1500000, -1000000), new Vector3f(1, 1, 1));
		lights.add(sun);
		//lights.add(new Light(new Vector3f(984, 20, -1100), new Vector3f(2, 2, 2), new Vector3f(1,0.002f,0.002f)));
		//Lights END



		//Water START
		WaterFrameBuffers fbos = new WaterFrameBuffers();

		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), fbos);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water = new WaterTile(1000, -1000, 0);
		waters.add(water);
		//Water END
		
		//Postprocessing START
		Fbo multisampleFbo = new Fbo(Display.getWidth(), Display.getHeight()); 
		Fbo outputFbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE); 
		PostProcessing.init(loader); 
		//Postprocessing END



		//Particles START
		ParticleTexture LightParticle = new ParticleTexture(loader.loadTexture("smokeParticle"), 4);

		ParticleSystem LightSystem = new ParticleSystem(LightParticle, x3, y3, z3 + 0.1f, 1, 1.6f);
		//Particles END



		//Gui's START
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiTexture logo = new GuiTexture(loader.loadTexture("walksimulator"), new Vector2f(-0.8f, 0.93f), new Vector2f(0.20f, 0.0625f));
		GuiTexture bar1 = new GuiTexture(loader.loadTexture("bar1"), new Vector2f(0.02f, -0.85f), new Vector2f(0.125f, 0.075f));
		GuiTexture bar2 = new GuiTexture(loader.loadTexture("bar2"), new Vector2f(0.02f, -0.85f), new Vector2f(0.125f, 0.075f));
		GuiTexture bar3 = new GuiTexture(loader.loadTexture("bar3"), new Vector2f(0.02f, -0.85f), new Vector2f(0.125f, 0.075f));
		GuiTexture bar4 = new GuiTexture(loader.loadTexture("bar4"), new Vector2f(0.02f, -0.85f), new Vector2f(0.125f, 0.075f));
		GuiTexture bar5 = new GuiTexture(loader.loadTexture("bar5"), new Vector2f(0.02f, -0.85f), new Vector2f(0.125f, 0.075f));
		GuiTexture bar6 = new GuiTexture(loader.loadTexture("bar6"), new Vector2f(0.02f, -0.85f), new Vector2f(0.125f, 0.075f));
		
		GuiTexture shadowMap = new GuiTexture(renderer.getShadowMapTexture(), new Vector2f(0.5f, 0.5f), new Vector2f(0.5f, 0.5f));
	    //guis.add(shadowMap);
		
		GuiTexture died_starve = new GuiTexture(loader.loadTexture("died_starve"), new Vector2f(-0f, -1f), new Vector2f(1.2f, 2.2f));
		GuiTexture underwater = new GuiTexture(loader.loadTexture("underwater"), new Vector2f(-0f, -1f), new Vector2f(1.2f, 2.2f));
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		//Gui's END





		//==================== WHILE LOOP BELOW ===================
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			//Footsteps START
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
			//Footsteps END

			//Underwater START
			if(camera.getPosition().y <= 0){
				guis.add(underwater);
				if(underwaterSFX.isHasBeenPaused()){
					underwaterSFX.continuePlaying();
				} else if (!underwaterSFX.isPlaying()) {
					underwaterSFX.play(underwaterbuffer);
				}
			} else if(camera.getPosition().y > 0){
				underwaterSFX.pause();
			}
			//Underwater END



			//Hunger bar START
			if(HungerBar.getHunger(player) == 1){
				guis.add(bar1);
			} else if (HungerBar.getHunger(player) == 2){
				guis.add(bar2);
			} else if (HungerBar.getHunger(player) == 3){
				guis.add(bar3);
			} else if (HungerBar.getHunger(player) == 4){
				guis.add(bar4);
			} else if (HungerBar.getHunger(player) == 5){
				guis.add(bar5);
			} else if (HungerBar.getHunger(player) == 6){
				guis.add(bar6);
				guis.add(died_starve);
				RespawnPlayer.respawn(player);
			} else {
				guis.add(bar6);
			}
			//Hunger bar END


			guis.add(logo);
			//Underwater END

			//Player movement START
			player.move(terrain);
			camera.move();
			//Player movement END

			//Particles START
			LightSystem.generateParticles(new Vector3f(984,16,-1350));

			ParticleMaster.update(camera);
			
			//Shadows START
			renderer.renderShadowMap(entities, sun, player);
			//Shadows END

			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			//Particles END

			//Water START
			fbos.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.processEntity(player);
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, -water.getHeight()+4f));
			camera.getPosition().y += distance;
			camera.invertPitch();

			fbos.bindRefractionFrameBuffer();
			renderer.processEntity(player);
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, water.getHeight()+4f));
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			fbos.unbindCurrentFrameBuffer();
			//Water END

			//Render to display START
            
			//Render to postprocessing fbo START && Scene rendering
			multisampleFbo.bindFrameBuffer(); 
			renderer.processEntity(player);
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 0, 0, 0));
			waterRenderer.render(waters, camera, sun);
			ParticleMaster.renderParticles(camera);
			multisampleFbo.unbindFrameBuffer(); 
			multisampleFbo.resolveToFbo(outputFbo);
			PostProcessing.doPostProcessing(outputFbo.getColourTexture()); 
			//Postprocessing render END && Scene rendering
			
			guiRenderer.render(guis);
			TextMaster.render();
			guis.remove(underwater);
			guis.remove(logo);
			guis.remove(bar1);
			guis.remove(bar2);
			guis.remove(bar3);
			guis.remove(bar4);
			guis.remove(bar5);
			guis.remove(bar6);
			guis.remove(died_starve);

			DisplayManager.updateDisplay();
			//Render to display END
		}
		//==================== END WHILE LOOP ===================




		//Clean up START
		PostProcessing.cleanUp(); 
		outputFbo.cleanUp();
		multisampleFbo.cleanUp(); 
		ParticleMaster.cleanUp();
		TextMaster.cleanUp();
		fbos.cleanUp();
		backgroundambient.delete();    
		AudioMaster.cleanUp();
		waterShader.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		//Clean up END



		//Web integration START
		Float dist = player.getDistanceTraveled() * 0.12f;
		String uid = webIntegration.FileReader.fileReader();
		variableSender.sendVariable(uid, Float.toString(dist));
		//Web integration END


	}

}
