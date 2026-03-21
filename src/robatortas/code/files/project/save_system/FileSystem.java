package robatortas.code.files.project.save_system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import robatortas.code.files.core.console.Console;
import robatortas.code.files.core.utils.ParsingUtils;

/**<NEWLINE>
 * This class manages the creation, deletion and editing of 
 * the save files in the game. Allowing to modify by json keys or
 * to overwrite the complete file if you want.
 * <br>
 * All the files are gonna be stored as Jsons, its a nice syntax.
 * <br>
 * The paths are gonna be relative, since there is no purpose to be writing files outside
 * of the game lol, this ain't a virus.
 * <br>
 * FileSystem works like a state machine, where you need to create a new
 * FileSystem for you to be able to edit the file with the functions.
 */
public class FileSystem {
	
	// Since everything is json, might aswell.
	public static String fileExt = ".json";
	
	public String filePath, fileName;
	private File file;
	
	private JsonObject object;
	
	private ParsingUtils parsingUtils = new ParsingUtils();
	
	public FileSystem(String filePath) {
		this.filePath = filePath;
		Path pPath = Paths.get(filePath);
		this.fileName = Paths.get(filePath).getFileName().toString();
		this.file = new File(Paths.get(filePath).toString());
		
		object = parsingUtils.createObject(file.getPath());
		
		if (!exists()) {
            Console.logError("File not found: " + file.getPath());
            return;
        }        
        if (object == null) {
            Console.logError("Failed to parse json or its an empty file.");
            return;
        }
	}
	
	public void createFile(String fileName, String path) {
		Path pPath = Paths.get(path + fileName + fileExt);
		try {
			if(!Files.exists(pPath)) {
				Files.createFile(pPath);
				Console.log(fileName + fileExt + " created at: " + path);
			} else {
				Console.logError(fileName + fileExt + " already exists at: " + path + " !");
			}
		} catch (IOException e) {
			e.printStackTrace();
			Console.logError("UNKOWN IOEXCEPTION WHILE CREATING FILE");
		}
	}
	
	private boolean exists() {
        return file.exists();
    }
	
	// NO FILE EXTENSION NEEDED
	public File getFile(String fileName, String path) throws IOException {
		Path pPath = Paths.get(path + fileName + fileExt);
		if(Files.exists(pPath)) {
			return new File(pPath.toString());
		}
		throw new IOException("Unable to get the file: " + fileName + fileExt);
	}
	
	// This one deletes with string path
	public void deleteFile() {
        if (exists()) {
            if (file.delete()) Console.log("Deleted: " + file.getName());
        } else Console.logError("File does not exist: " + file.getPath());
    }
	
	public void changeFileName(String newName) {
		Path pPath = Paths.get(file.getPath());
		String oldName = file.getName() + fileExt;
		File newNameFile = new File(newName + fileExt);
		if(Files.exists(pPath)) {
			new File(pPath.toString()).renameTo(newNameFile);
			Console.log(oldName + " has been renamed to: " + newNameFile.getName());
		} else Console.logError(pPath.getFileName() + fileExt + "does not exist");
	}
	
	// Overwrites the selected file with the contents of the string parameter
//	public void overWriteFile(String contents) {
//		Path pPath = Paths.get(file.getPath());
//		if(Files.exists(pPath)) {
//			try {
//				BufferedReader reader = new BufferedReader(new FileReader(file));
//				String line;
//				while((line = reader.readLine()) != null) {
//					
//				}
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	
	// Need to make this either generic, or create a function for every type
	public void changeKeyValue(String key, Object value) {
        // For all instances of
        if (value instanceof String) {
            object.addProperty(key, (String) value);
        } else if (value instanceof Number) {
            object.addProperty(key, (Number) value);
        } else if (value instanceof Boolean) {
            object.addProperty(key, (Boolean) value);
        }
        
        try (FileWriter writer = new FileWriter(file)) {
        	Gson gson = new GsonBuilder().setPrettyPrinting().create();
        	gson.toJson(object, writer);
        } catch (Exception e) {
            Console.logError("Error accessing key '" + key + "': " + e.getMessage());
        }
    }
	
	// This function reads the key value and applies it to the desired game variable
	// To set the positions of the player from the file, etc.
	public <T> Object  getKeyValue(String key, Object varToBeChanged) {
		Object obj = null;
		try (FileReader reader = new FileReader(file)) {
        	if (varToBeChanged instanceof String) {
        		obj = object.get(key).getAsString();
            } else if (varToBeChanged instanceof Integer) {
            	obj = object.get(key).getAsInt();
            } else if(varToBeChanged instanceof Double) {
            	obj = object.get(key).getAsDouble();
            } else if (varToBeChanged instanceof Boolean) {
            	obj = object.get(key).getAsBoolean();
            }
        	return obj;
        } catch (Exception e) {
            Console.logError("Error accessing key '" + key + "': " + e.getMessage());
        }
		return obj;
	}
}