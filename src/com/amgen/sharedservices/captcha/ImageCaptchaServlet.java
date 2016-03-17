/**
 *  @author abhinay
 * This servlet generates unique captcha images on each servlet request
 *  
 */

package com.amgen.sharedservices.captcha;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.utils.ImageToFile;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import com.octo.captcha.service.multitype.MultiTypeCaptchaService;
 

public class ImageCaptchaServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MultiTypeCaptchaService instance=null;
	private static HashMap<String, Color> colorMap=null;
	private static String captchaId=null;
	private static WordGenerator wordGen=null;
	private String captchaText=null;
	private static  final String fileName="properties//captcha";


	public void init(ServletConfig servletConfig) throws ServletException {
 
        super.init(servletConfig);
        colorMap=new HashMap<String,Color>();
		colorMap.put("red", new Color(255,51,51));
		colorMap.put("green", new Color(51,255,51));
		colorMap.put("blue", new Color(0,128,255));
		colorMap.put("cyan", new Color(0,255,255));
		colorMap.put("lime", new Color(0,255,0));
		colorMap.put("gray", new Color(128,128,128));
		colorMap.put("yellow", new Color(255,255,0));
		colorMap.put("white", new Color(255,255,255));
		colorMap.put("crimson", new Color(220,20,60));
		colorMap.put("violet", new Color(238,130,238));
		colorMap.put("gold", new Color(255,215,0));
		wordGen= new RandomWordGenerator("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwzxyz0123456789");
		
		
    }
 
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   // get the session id that will identify the generated captcha.
      captchaId = request.getSession().getId();
      String captchaChars=request.getParameter("captchaChars");
      String color=request.getParameter("color");
 	  String deformation=request.getParameter("deformation");
 	  String complexity= request.getParameter("complexity");
 	  String background=request.getParameter("background");
 	  String caseSensitivity=request.getParameter("caseSensitivity");
 	   
 	  captchaText=wordGen.getWord(new Integer(Integer.parseInt(captchaChars)));
 	  BufferedImage imageCaptcha=null;
 	  byte[] captchaChallengeAsJpeg = null;
 	// the output stream to render the captcha image as jpeg 
      ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
      
      System.out.println("loading resource ");
 	  ResourceBundle rc=ResourceBundle.getBundle(fileName);
 	  String imageCaptchaDir=rc.getString("imageCaptchaDir");
//      String imageCaptchaDir="./User_Files/imageCaptchas/";
 	  File dir=new File(imageCaptchaDir);
 	  File[] files=dir.listFiles();
 	  
 	  int totalFiles;
 	  if((totalFiles=countFiles(dir))>=10){
 		  Random rand=new Random();
 		  int randNum=rand.nextInt(totalFiles);
 		  System.out.println("random"+randNum);
 		  File randCaptcha=files[randNum];
 		  FileInputStream in=new FileInputStream(randCaptcha.getCanonicalPath());		
		  imageCaptcha=ImageIO.read(in);
		  ImageIO.write(imageCaptcha, "jpg", jpegOutputStream);
	      captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
 	  }
 	  
 	  else{
 		  
 		 if(files.length!=0){
 	 		  String captchaFile=checkFile(files,captchaText);
 	 		  if(captchaFile!=null){
 	 			 FileInputStream in=new FileInputStream(captchaFile);		
 	 			 imageCaptcha=ImageIO.read(in);
 	 			 ImageIO.write(imageCaptcha, "jpg", jpegOutputStream);
 	 			 captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
 	 		  }
 	 		  
 	 		  else{
 	 			  
 				   if(color.equals("")|| background.equals(("false").toLowerCase()))
 						   color="white";
 				   Color bgColor=colorMap.get(color);
 					// call the MultiTypeCaptchaService getChallenge method
 				   instance = new GenericManageableCaptchaService(new FastHashMapCaptchaStore(),new CustomImageCaptchaEngine(bgColor,complexity,deformation,captchaText,caseSensitivity), 180,100000,75000);
 				   imageCaptcha =instance.getImageChallengeForID(captchaId,request.getLocale());
 				    
 				  try { 
 			 		 // a jpeg encoder
 					 ImageIO.write(imageCaptcha, "jpg", jpegOutputStream);
 			         captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
 			         FileOutputStream fout=new FileOutputStream(new File(imageCaptchaDir+captchaText+".jpg"));
 			         ImageToFile.encodeJPG(fout, imageCaptcha);
 				  }catch (IllegalArgumentException e) {
 			             response.sendError(HttpServletResponse.SC_NOT_FOUND);
 			             return;
 			      }catch (CaptchaServiceException e) {
 			             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
 			             return;
 			      } 
 	 		  }
 	 	  }
 	 	  
 	 	  else{
 		 		if(color.equals("")|| background.equals(("false").toLowerCase())){
 					   color="white";
 				}
 				Color bgColor=colorMap.get(color);
 				// call the MultiTypeCaptchaService getChallenge method
 			    instance = new GenericManageableCaptchaService(new FastHashMapCaptchaStore(),new CustomImageCaptchaEngine(bgColor,complexity,deformation,captchaText,caseSensitivity), 180,100000,75000);
 			    imageCaptcha =instance.getImageChallengeForID(captchaId,request.getLocale());
 			    
 			   try { 
 		 		 // a jpeg encoder
 				 ImageIO.write(imageCaptcha, "jpg", jpegOutputStream);
 		         captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
 		         FileOutputStream fout=new FileOutputStream(new File(imageCaptchaDir+captchaText+".jpg"));
 		         ImageToFile.encodeJPG(fout, imageCaptcha);
 			  }catch (IllegalArgumentException e) {
 		             response.sendError(HttpServletResponse.SC_NOT_FOUND);
 		             return;
 		       }catch (CaptchaServiceException e) {
 		             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
 		             return;
 		       }
 	 	  }
 		  
 	  }
 	  
 	  
 	  
  
         // flush it in the response
         response.setHeader("Cache-Control", "no-store");
         response.setHeader("Pragma", "no-cache");
         response.setDateHeader("Expires", 0);
         response.setContentType("image/jpeg");
         ServletOutputStream responseOutputStream = response.getOutputStream();
         responseOutputStream.write(captchaChallengeAsJpeg);
         responseOutputStream.flush();
         responseOutputStream.close();
       
    }
    
    
	private int countFiles(File dir) {
		// TODO Auto-generated method stub
		int count=0;
		File[] files=dir.listFiles();
		for(File file:files){
			if(file.isFile()){
				count++;
			}
		}
		return count;
	}


	private String checkFile(File[] files, String captchaText) {
		// TODO Auto-generated method stub
		String captchaFile=null;
		for (File file : files) {
 	  		String fileName=file.getName().split("\\.(?=[^\\.]+$)")[0];
 				if(fileName.equals(captchaText)){
 					try {
 						captchaFile=file.getCanonicalPath();
 					} catch (IOException e) {
 						// TODO Auto-generated catch block
 						e.printStackTrace();
 					} 					
 				}
 				else{
 					continue;
 				}
 			}
		return captchaFile;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	  String cId = request.getParameter("captchaID");
    	  String inChars = request.getParameter("captcha_response");
    	

    	  boolean hasPassed = validateCaptcha(cId, inChars);
    	  request.getSession().setAttribute( "result", new Boolean(hasPassed));
   	      request.getRequestDispatcher( "process.jsp").forward( request, response );
    }
    	 
    private boolean validateCaptcha( String captchaId, String inputChars){
		boolean b = false;
		try{
		   b = instance.validateResponseForID( captchaId, inputChars );
		  }catch( CaptchaServiceException cse ){
			  
		}
	  
	  return b;

    }
}