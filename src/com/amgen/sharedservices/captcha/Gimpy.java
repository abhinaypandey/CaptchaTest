package com.amgen.sharedservices.captcha;

import com.octo.captcha.image.ImageCaptcha;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Gimpy
  extends ImageCaptcha
  implements Serializable
{
  private String response;
  private boolean caseSensitive = true;
  
  Gimpy(String paramString1, BufferedImage paramBufferedImage, String paramString2, boolean paramBoolean)
  {
    super(paramString1, paramBufferedImage);
    response = paramString2;
    caseSensitive = paramBoolean;
  }
  
  Gimpy(String paramString1, BufferedImage paramBufferedImage, String paramString2)
  {
    this(paramString1, paramBufferedImage, paramString2, true);
  }
  
  public final Boolean validateResponse(Object paramObject)
  {
    return (null != paramObject) && ((paramObject instanceof String)) ? validateResponse((String)paramObject) : Boolean.FALSE;
  }
  
  private final Boolean validateResponse(String paramString)
  {
    return Boolean.valueOf(caseSensitive ? paramString.equals(response) : paramString.equalsIgnoreCase(response));
  }
}
