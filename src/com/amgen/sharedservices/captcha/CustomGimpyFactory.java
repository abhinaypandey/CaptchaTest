/**
 *  @author abhinay
 *  A custom implementation of GimpyFactory class
 *  returns generated captcha's BufferedImage 
 * */

package com.amgen.sharedservices.captcha;

import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.CaptchaQuestionHelper;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.image.ImageCaptcha;

public class CustomGimpyFactory extends com.octo.captcha.image.ImageCaptchaFactory {
	private Random myRandom = new SecureRandom();
    private WordToImage wordToImage;
    private String captchaText;
    private boolean caseSensitive=true;

    public static final String BUNDLE_QUESTION_KEY = Gimpy.class.getName();

    public CustomGimpyFactory(WordToImage word2image,String captchaText){
        this(word2image,captchaText,true);
    }

    public CustomGimpyFactory(WordToImage word2image,String captchaText,boolean caseSensitive) {
        if (word2image == null) {
            throw new CaptchaException("Invalid configuration" +
                    " for a GimpyFactory : WordToImage can't be null");
        }
        if (captchaText.equals("")) {
            throw new CaptchaException("Invalid captcha text" +
                    " for a GimpyFactory : Captcha text can't be null");
        }
        wordToImage = word2image;
        this.captchaText=captchaText;
        this.caseSensitive=caseSensitive;

    }

    /**
     * gimpies are ImageCaptcha
     *
     * @return the image captcha with default locale
     */
    public ImageCaptcha getImageCaptcha() {
        return getImageCaptcha(Locale.getDefault());
    }

    public WordToImage getWordToImage() {
        return wordToImage;
    }

    public String getCaptchaText() {
        return captchaText;
    }

    /**
     * gimpies are ImageCaptcha
     *
     * @return a pixCaptcha with the question :"spell the word"
     */
    public ImageCaptcha getImageCaptcha(Locale locale) {

        //captchaText passed from CustomImageCaptchaEngine
    	BufferedImage image = null;
        String captchaText=getCaptchaText();
        try {
            image = getWordToImage().getImage(getCaptchaText());
        } catch (Throwable e) {
            throw new CaptchaException(e);
        }
 
        ImageCaptcha captcha = new Gimpy("Please type the word appearing in the picture", image,captchaText, caseSensitive);
        return captcha;
    }

    
	protected Integer getRandomLength() {
        Integer wordLength;
        int range = getWordToImage().getMaxAcceptedWordLength() -
                getWordToImage().getMinAcceptedWordLength();
        int randomRange = range != 0 ? myRandom.nextInt(range + 1) : 0;
        wordLength = new Integer(randomRange +
                getWordToImage().getMinAcceptedWordLength());
        return wordLength;
    }



}
