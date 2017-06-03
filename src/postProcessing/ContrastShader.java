package postProcessing;

import java.io.File;

import shaders.ShaderProgram;

public class ContrastShader extends ShaderProgram {

	private static final String VERTEX_FILE = new File("shaders/contrastVertex.txt").getAbsolutePath();
	private static final String FRAGMENT_FILE = new File("shaders/contrastFragment.txt").getAbsolutePath();
	
	public ContrastShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {	
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
