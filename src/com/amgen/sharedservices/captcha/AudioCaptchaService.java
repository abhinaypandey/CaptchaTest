package com.amgen.sharedservices.captcha;

import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.component.sound.soundconfigurator.FreeTTSSoundConfigurator;
import com.octo.captcha.component.sound.soundconfigurator.SoundConfigurator;
import com.octo.captcha.component.sound.wordtosound.FreeTTSWordToSound;
import com.octo.captcha.component.sound.wordtosound.WordToSound;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import com.octo.captcha.service.sound.SoundCaptchaService;
import com.octo.captcha.sound.gimpy.GimpySoundFactory;

public class AudioCaptchaService {
	private static SoundCaptchaService instanceSound=null;
	
	public static SoundCaptchaService getInstance(WordGenerator wordGen){
		 System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

         SoundConfigurator soundConfig = new FreeTTSSoundConfigurator("kevin16", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory", 1.0f, 100, 100);

         WordToSound word2sound = new FreeTTSWordToSound(soundConfig,6,7);

         CaptchaFactory[] factories = new CaptchaFactory[]{new GimpySoundFactory(wordGen, word2sound)};

         CaptchaEngine captchaEngine = new GenericCaptchaEngine(factories);

         instanceSound = new GenericManageableCaptchaService(captchaEngine, 180, 180000, 75000);
         return instanceSound;
	}

}
