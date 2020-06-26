package trataHistorialUbicaciones;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import trataHistorialUbicaciones.ReadJSON;
import trataHistorialUbicaciones.WriteJSON;


public class Main {

	public static void main(String[] args) throws IOException {
		String[] columnas = new String[2];
		//Cabeceras a mostrar
		if(args.length < 2) {
			columnas[0] = "latitudeE7";
			columnas[1] = "longitudeE7";

		}else {
			columnas = args[1].split(",");
		}
		
		Path path ;
		if(args.length < 1) {
			path = Paths.get(System.getProperty("user.dir"));
		}else {
			path = Paths.get(args[0]);
		}
		//Comprobar si la ruta especificada es válida
		
		//Si la ruta no existe, se utiliza la ruta actual
		if (!Files.exists(path)) {
			path = Paths.get(System.getProperty("user.dir"));
		}

		//Poblar una lista con todos los archivos JSON encontrados
		File directorio = new File(path.toString());
		

		for (File file : directorio.listFiles()){
			
			//Comprobamos que el archivo no es una carpeta
			if (!file.isDirectory()) {
				String extension = "";
				
				//Obtener extension
				int i = file.toString().lastIndexOf('.');
				if (i > 0) {
				    extension =  file.toString().substring(i+1);
				}
				
				//Comprobar si se trata de un archivo json
				if (extension.equalsIgnoreCase("JSON")) {
					System.out.println("Archivo seleccionado: " + file.getName());
					ReadJSON reader = new ReadJSON(file, columnas);
					reader.open();
				    if (!reader.read()) {
				    	reader.close();
				    } else {
				    	reader.close();
				    	
				    	WriteJSON writer = new WriteJSON(path + "\\" + file.getName()); 
				    	writer.open();
				    	writer.write(reader.getLectura());
				    	writer.close();
				    	
				    }
				}
			}
			
		}
		
	}
}
