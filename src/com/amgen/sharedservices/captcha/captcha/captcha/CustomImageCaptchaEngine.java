/**
 *  @author abhinay 
 * This class generates customized captcha images 
 *  
 */

package com.amgen.sharedservices.captcha.captcha.captcha;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.amgen.sharedservices.captcha.CustomGimpyFactory;
import com.amgen.sharedservices.captcha.CustomListImageCaptchaEngine;
import com.jhlabs.image.BlurFilter;
import com.jhlabs.image.WaterFilter;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedAndShearedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.BaffleTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.sound.soundconfigurator.FreeTTSSoundConfigurator;
import com.octo.captcha.component.sound.soundconfigurator.SoundConfigurator;
import com.octo.captcha.component.sound.wordtosound.FreeTTSWordToSound;
import com.octo.captcha.component.sound.wordtosound.WordToSound;


public class CustomImageCaptchaEngine extends CustomListImageCaptchaEngine {
	private static final String fileName="properties//captcha"; 
	
	public CustomImageCaptchaEngine(Color color,String complexity,String deformation, String captchaText,String caseSensitivity) {
		super(color,complexity,deformation,captchaText,caseSensitivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buildInitialFactories(Color color,String complexity,String deformation,String captchaText,String caseSensitivity) {
		// TODO Auto-generated method stub 

		//captcha background filters
		WaterFilter water = new WaterFilter();
	    water.setAmplitude(1);
	    water.setAntialias(true);
	    water.setPhase(10);
	    water.setWavelength(70);
	    
	    BlurFilter blur=new BlurFilter();
	    blur.setBlur(8);
	    ImageDeformation backDeform=new ImageDeformationByFilters(new ImageFilter[0]);
		ImageDeformation textDeform=new ImageDeformationByFilters(new ImageFilter[0]);
		ImageDeformation postDeform=new ImageDeformationByFilters(new ImageFilter[0]);
	    
		
	    if(complexity.equals(("high").toLowerCase())){
	    	backDeform=new ImageDeformationByFilters(new ImageFilter[]{blur});
			textDeform=new ImageDeformationByFilters(new ImageFilter[0]);
			postDeform=new ImageDeformationByFilters(new ImageFilter[]{water});
	    }
		
	    RandomRangeColorGenerator randomWordColorGenerator = new RandomRangeColorGenerator(new int[] { 10, 30 }, new int[] { 10, 40 }, new int[] { 10, 12 });
	    RandomRangeColorGenerator randomBaffleColorGenerator = new RandomRangeColorGenerator(new int[] { 70, 120 }, new int[] { 50, 120 }, new int[] { 90, 150 }, new int[] { 80, 200 });
		TextPaster textPaster = 
				new DecoratedRandomTextPaster(Integer.valueOf(6), Integer.valueOf(7),randomWordColorGenerator,new TextDecorator[]{new BaffleTextDecorator(Integer.valueOf(10), randomBaffleColorGenerator)});

		//captcha background generator
		BackgroundGenerator backgroundGenerator ;
		if(complexity.equals(("high").toLowerCase())){
			backgroundGenerator=new FunkyBackgroundGenerator(200, 100, randomBaffleColorGenerator);
			textPaster.pasteText(backgroundGenerator.getBackground(),new AttributedString(captchaText));
		}
		else if(complexity.equals(("medium").toLowerCase())){
			 backgroundGenerator=new GradientBackgroundGenerator(200, 100, Color.CYAN, Color.GREEN);
			 textPaster.pasteText(backgroundGenerator.getBackground(),new AttributedString(captchaText));
		}
		else{
			backgroundGenerator = 
					new UniColorBackgroundGenerator(200, 100, color);
			textPaster.pasteText(backgroundGenerator.getBackground(),new AttributedString(captchaText));
		}
	    
		
		//word to image creation
		Font[] fontsList =
				new Font[] { new Font("Arial", Font.TYPE1_FONT, 10), new Font("Arial", 0, 14), new Font("Vardana", 0, 17), };
		FontGenerator fontGenerator =
				new TwistedAndShearedRandomFontGenerator(new Integer(20), new Integer(50));
		WordToImage wordToImage=
				new ComposedWordToImage(fontGenerator, backgroundGenerator, textPaster);
		WordToImage deformedWordToImage=new DeformedComposedWordToImage(fontGenerator, backgroundGenerator, textPaster, backDeform, textDeform, postDeform);
		
		boolean caseSensitive=true;
		if(caseSensitivity.equals(("enabled").toLowerCase())){
			caseSensitive=true;
		}
		else{
			caseSensitive=false;
		}
		
		
		//creates new factory and add them up to factory list
		if(deformation.equals(("true").toLowerCase())){
			this.addFactory(new CustomGimpyFactory(deformedWordToImage,captchaText,caseSensitive));
		}
		else if(deformation.equals(("false").toLowerCase())){
			this.addFactory(new CustomGimpyFactory(wordToImage,captchaText,caseSensitive));
		}
		else{
			this.addFactory(new CustomGimpyFactory(wordToImage,captchaText,caseSensitive));
		}
	
		generateSoundCaptcha(captchaText);	
	}

	private void generateSoundCaptcha(String captchaText) {
		// TODO Auto-generated method stub
		//sound captcha generation
		try{
		    String voiceClasses = System.getProperty("freetts.voices"); 
            if (voiceClasses == null) {
              System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
              voiceClasses = System.getProperty("freetts.voices");
            }
            
        
            ResourceBundle rc=ResourceBundle.getBundle(fileName);
            String soundCaptchaDirPath=rc.getString("soundCaptchaDir");
//            String soundCaptchaDirPath="./User_Files/soundCaptchas/";
			SoundConfigurator soundConfig = new FreeTTSSoundConfigurator("kevin16","com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory", 1.0f, 100, 100);
	        WordToSound word2sound = new FreeTTSWordToSound(soundConfig,6,15);
	    	AudioInputStream audio=word2sound.getSound(captchaText.replace("","  ").trim());
	        AudioSystem.write(audio,AudioFileFormat.Type.WAVE, new File(soundCaptchaDirPath+captchaText+".wav"));
        }
        catch(IOException e){
        	e.printStackTrace();
        }
        catch(RuntimeException e){
        	e.printStackTrace();
        }
	}

}
