package kube.utils;

import java.io.File;
import java.io.InputStream;

import javafx.scene.image.Image;

public class ResourcesUtil {
	
	private String resourcesPath;
	
	private Object classWorker;
	
	public ResourcesUtil(String path, Object obj) throws Exception
	{
		
		File f = new File(path);
		if (!f.exists() || !f.isDirectory()) 
		{
			// Create dir
			System.out.println("ResourceUtil : create resource folder...");
			 if (!f.mkdir())
				 throw new Exception("Can't create resource folder !");
		
		}
		System.out.println("ResourceUtil : folder Ok");
		resourcesPath = path;
		classWorker = obj;
	}
	
	
	public InputStream getResourceAsStream(String s) 
	{
		return getClass().getResourceAsStream(resourcesPath + s);
	}
	
	public Image getResourceImage(String s) 
	{
		return new Image(resourcesPath + s);
	}
	
	public String getFullPath(String s) 
	{
		return resourcesPath + s;
	}
	
	public String getLocalResourcePath(String s)
	{
		return classWorker.getClass().getResource(resourcesPath + s).toString();
	}
	
}
