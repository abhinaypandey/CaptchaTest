package com.amgen.sharedservices.captcha;

import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
	private static PropertiesLoader instance=null;
	protected PropertiesLoader(){
		
	}
	
	
	protected String getDirectory(){
		String dir=null;
		ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
		Properties prop=new Properties();
		try{
			prop.load(classLoader.getResourceAsStream("/captcha.properties"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return dir;
	}

}
