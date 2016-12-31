package renderEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.PNGDecoder;

public class IconLoader {
	public static ByteBuffer createByteBuffer(String file){
        FileInputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        PNGDecoder decoder = null;
		try {
			decoder = new PNGDecoder(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
        try {
			decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.RGBA);
		} catch (IOException e) {
			e.printStackTrace();
		}
        buffer.flip();
		return buffer;
		
	}
}
