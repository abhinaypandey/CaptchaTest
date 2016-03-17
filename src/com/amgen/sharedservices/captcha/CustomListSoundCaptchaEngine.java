package com.amgen.sharedservices.captcha;

import com.octo.captcha.component.word.wordgenerator.WordGenerator;


public abstract class CustomListSoundCaptchaEngine extends com.octo.captcha.engine.sound.SoundCaptchaEngine {
	protected WordGenerator wordGen;
	
	public CustomListSoundCaptchaEngine(WordGenerator wordGen) {
		super();
		this.wordGen=wordGen;
		// TODO Auto-generated constructor stub
	}
	
	protected abstract void buildInitialFactories(WordGenerator wordGen);
	
	
	
}
