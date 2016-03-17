/**
 * @author abhinay
 * builds captcha factory 
 */
package com.amgen.sharedservices.captcha;

import java.awt.Color;
import java.util.Arrays;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;

public abstract class CustomListImageCaptchaEngine extends com.octo.captcha.engine.image.ImageCaptchaEngine {
	protected static Color color;

	public CustomListImageCaptchaEngine(Color color,String complexity,String deformation,String captchaText,String caseSensitivity){
		buildInitialFactories(color,complexity,deformation,captchaText,caseSensitivity);
		checkFactoriesSize();
	}

	protected abstract void buildInitialFactories(Color color,String complexity,String deformation,String captchaText,String caseSensitivity);
	
	public boolean addFactory(
            com.octo.captcha.image.ImageCaptchaFactory factory) {
        return factory != null && this.factories.add(factory);
    }
	
	public void addFactories(
            com.octo.captcha.image.ImageCaptchaFactory[] factories) {
        checkNotNullOrEmpty(factories);
        this.factories.addAll(Arrays.asList(factories));
    }

    private void checkFactoriesSize() {
        if (factories.size() == 0)
            throw new CaptchaException(
                    "This gimpy has no factories. Please initialize it "
                            + "properly with the buildInitialFactory() called by "
                            + "the constructor or the addFactory() mehtod later!");
    }
}
