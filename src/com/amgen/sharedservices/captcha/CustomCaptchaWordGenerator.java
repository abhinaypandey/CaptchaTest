package com.amgen.sharedservices.captcha;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

import com.octo.captcha.component.word.wordgenerator.WordGenerator;

public class CustomCaptchaWordGenerator implements WordGenerator{
	private final static Integer DEFAULT_LENGTH=5;
	
	private char[] possiblesChars;
	private Random myRandom = new SecureRandom();

	public CustomCaptchaWordGenerator() {

    }
	
	
	@Override
	public String getWord(Integer len) {
		Integer length=setWord(len);
		
		// TODO Auto-generated method stub
		 StringBuffer word = new StringBuffer(length.intValue());
	        for (int i = 0; i < length.intValue(); i++) {
	            word.append(possiblesChars[myRandom.nextInt(possiblesChars.length)]);
	        }
		 return word.toString();
	}

	@Override
	public String getWord(Integer length, Locale arg1) {
		// TODO Auto-generated method stub
		return getWord(length);
	}
	
	public Integer setWord(Integer length){
		if(length.equals(0)){
			return DEFAULT_LENGTH;
		}
		else{
			return length;
		}
	}
	
	public void setChars(String acceptableChars){
		possiblesChars=acceptableChars.toCharArray();
	}

}
