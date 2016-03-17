package com.amgen.sharedservices.captcha;

import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.component.sound.soundconfigurator.FreeTTSSoundConfigurator;
import com.octo.captcha.component.sound.soundconfigurator.SoundConfigurator;
import com.octo.captcha.component.sound.wordtosound.FreeTTSWordToSound;
import com.octo.captcha.component.sound.wordtosound.WordToSound;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.sound.gimpy.GimpySoundFactory;


public class CustomSoundCaptchaEngine extends CustomListSoundCaptchaEngine{
	protected WordGenerator wordGen;
	
	public CustomSoundCaptchaEngine(WordGenerator wordGen){
		super(wordGen);
		this.wordGen=wordGen;
	}
	
	@Override
	protected void buildInitialFactories(WordGenerator wordGen) {
		// TODO Auto-generated method stub
	      String voiceClasses = System.getProperty("freetts.voices"); // another one "freetts.voicesfile"
          if (voiceClasses == null) {
              System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
              voiceClasses = System.getProperty("freetts.voices");
          }
          
          SoundConfigurator soundConfig = new FreeTTSSoundConfigurator("kevin16","kevin16", 1.0f, 100, 100);
          WordToSound wordToSound=new FreeTTSWordToSound(soundConfig, 3, 6);
          CaptchaFactory []captchaFactory=new CaptchaFactory[]{new GimpySoundFactory(wordGen, wordToSound)};
          this.setFactories(captchaFactory);
		
	}
	

}
