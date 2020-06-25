package trataHistorialUbicaciones;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.json.Json;

public class WriteJSON{

	public FileWriter csvFile;
	public String nombreCSV;
	
	public WriteJSON(String nombreJSON) {
		
		nombreCSV = nombreJSON.replace(".json", ".csv");
		
	}
	
	public void open() throws IOException {
		
		this.csvFile = new FileWriter(nombreCSV);
		
	}
	
	public void write(List<String> ubiJSON) throws IOException {
		

		for (String l : ubiJSON) {
			//System.out.println(l);
			try {
				csvFile.write(l+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void close() throws IOException {
		csvFile.close();
	}
	
	
}


