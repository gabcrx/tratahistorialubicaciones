package trataHistorialUbicaciones;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import trataHistorialUbicaciones.ReadJSON;
import trataHistorialUbicaciones.WriteJSON;


public class Main {

	public static void main(String[] args) throws IOException {
		
		//Comprobar si la ruta especificada es válida
		Path path = Paths.get(args[0]);
		System.out.println(path);
		
		System.out.println(System.getProperty("user.dir"));
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
					ReadJSON reader = new ReadJSON(file);
					reader.open();
				    if (!reader.read()) {
				    	reader.close();
				    } else {
				    	reader.close();
				    	
				    	WriteJSON writer = new WriteJSON(file.getName()); 
				    	writer.open();
				    	writer.write(reader.getLectura());
				    	writer.close();
				    	
				    }
				}
			}
			
		}
		
	}
}
