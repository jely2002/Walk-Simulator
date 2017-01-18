package webIntegration;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {
	
	public static String fileReader(){
        List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("cache.txt"), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
        for (String line : lines) {
            return line;
        }
		return null;
	}

}
