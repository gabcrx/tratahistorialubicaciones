package trataHistorialUbicaciones;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.JsonObject;
import javax.json.JsonReaderFactory;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.stream.*;
import javax.json.stream.JsonParser.Event;


public class ReadJSON {

	public File file;
	public FileInputStream fip;
	public JsonParser parser;
	private List<String> lectura;
	private String[] columnas;
		
	public ReadJSON(File file, String[] columnas){
		
		this.file = file;
		this.columnas = columnas;
		
	}
	
	public void open() throws FileNotFoundException {
		
		fip = new FileInputStream(file);
		parser = Json.createParser(fip);
		
	}
	
	
	
	public boolean read() {

			String eventName = "";
		
			while(parser.hasNext()) {
				Event event = parser.next();

				//System.out.println(event.toString());
				//System.out.println(parser.getLocation());

				if (event == JsonParser.Event.KEY_NAME) {
					//parser.getObject();
					eventName = parser.getString();
					
				}
				if (event == JsonParser.Event.START_ARRAY) {
					
					if (!eventName.equals("locations")) {
						continue;
					}
					try {
						lectura = parser.getArrayStream()
									.map(n -> {  
										JsonObject j = n.asJsonObject();
										
										String s = "";
										int cnt = 0;
										for (String c : columnas) {
											
											if (!j.containsKey(c)) {
												continue;
											};
											
											if (cnt == 0) {
												s = j.get(c).toString();
											}else {
											s = s + "," + j.get(c).toString();
											}
											cnt++;
										}
										
										/*String s = j.get("latitudeE7").toString() + "," +
												j.get("longitudeE7").toString() ;*/
										return s;    
										})
									.collect(Collectors.toList());
					} catch (Exception e) {
						e.getStackTrace();
						return false;
					}
				}
			}
			

		return true;

	}
	
	public void close() {
		parser.close();
	}

	public List<String> getLectura() {
		return lectura;
	}

	public void setLectura(List<String> lectura) {
		this.lectura = lectura;
	}
	
	
}
