/**
 * @author abhinay
 *  Fetches latest generated sound captcha file from filesystem
 * 
 * */

package com.amgen.sharedservices.captcha.captcha.captcha;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.octo.captcha.service.CaptchaServiceException;


/**
 * Servlet implementation class SoundCaptchaServlet
 */
@WebServlet("/SoundCaptchaServlet")
public class SoundCaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String fileName="properties/captcha";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SoundCaptchaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		AudioInputStream audioCaptcha=null;
		try {
//			ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
//			Properties prop=new Properties();
//			prop.load(classLoader.getResourceAsStream("captcha.properties"));
//	        String soundCaptchaDirPath=prop.getProperty("soundCaptchaDir");
			
			ResourceBundle rc=ResourceBundle.getBundle(fileName);
            String soundCaptchaDirPath=rc.getString("soundCaptchaDir");
//            String soundCaptchaDirPath="./User_Files/soundCaptchas";
			File soundCaptchaDir=new File(soundCaptchaDirPath);
		    FileInputStream captchaFile=new FileInputStream(getLatestCaptchaFile(soundCaptchaDir));
		    audioCaptcha=AudioSystem.getAudioInputStream(new BufferedInputStream(captchaFile));
		    
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
            throw new RuntimeException(e);
        } catch (CaptchaServiceException e) {
        	e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException(e);
        }
		
		response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("audio/wav");
        ServletOutputStream out = response.getOutputStream();
        ByteArrayOutputStream soundByteOutputStream = new ByteArrayOutputStream();
        AudioSystem.write(audioCaptcha,javax.sound.sampled.AudioFileFormat.Type.WAVE,soundByteOutputStream);
        out.write(soundByteOutputStream.toByteArray());
        out.flush();
        out.close();
	}
	
	private File getLatestCaptchaFile(File fl) {
		// TODO Auto-generated method stub
		   	File[] files=fl.listFiles();
		    long lastMod = Long.MIN_VALUE;
		    File latest = null;
		    for (File file : files) {
		        if (file.lastModified() > lastMod) {
		            latest = file;
		            lastMod = file.lastModified();
		        }
		    }
		    return latest;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
