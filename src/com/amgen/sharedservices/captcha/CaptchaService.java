/**
 *  @author abhinay
 * Implements customized captcha 
 *  
 */

package com.amgen.sharedservices.captcha;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class CaptchaService extends TagSupport implements Serializable {
	/**
	 * fetches passed options in captcha jstl tag 
	 */
	protected PageContext pageContext;
	private static final long serialVersionUID = 1L;
	private int captchaChars;
	private String bgColor;
	private String complexity;
	private String sessionId;
	private String deformation;
	private String background;
	private String soundCaptcha;
	private String caseSensitivity;
	
	public CaptchaService() {
		// TODO Auto-generated constructor stub
		
	}
	
	public PageContext getPageContext() {
		return pageContext;
	}


	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}


	public int getCaptchaChars() {
		return captchaChars;
	}


	public void setCaptchaChars(int captchaChars) {
		this.captchaChars = captchaChars;
	}

	
	public String getBgColor() {
		return bgColor;
	}


	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getDeformation() {
		return deformation;
	}

	public void setDeformation(String deformation) {
		this.deformation = deformation;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getSoundCaptcha() {
		return soundCaptcha;
	}

	public void setSoundCaptcha(String soundCaptcha) {
		this.soundCaptcha = soundCaptcha;
	}

	public void setSessionId(String sessionId){
		this.sessionId=sessionId;
	}
	
	public String getSessionId(){
		return(sessionId);
	}

	
	public String getComplexity() {
		return complexity;
	}

	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}
	
	public String getCaseSensitivity() {
		return caseSensitivity;
	}

	public void setCaseSensitivity(String caseSensitivity) {
		this.caseSensitivity = caseSensitivity;
	}


	/* This method performs fetching of jstl tag options
	 * 
	 * */
	public int doStartTag() throws JspException { 
	    JspWriter out=pageContext.getOut();
	    try{ 
	        
	        try {
				StringBuffer sb = new StringBuffer();
				if(soundCaptcha.equals(("disabled").toLowerCase())){
					sb.append("<div class=\"captcha_div\" style=\"width:300px; height:130px;display:block;\">");
					sb.append("<div class=\"captcha_image_div\" style=\"width:220px;height:140px;display:inline-block;vertical-align:top;\">");
					sb.append("<iframe frameborder=0 height=140 width=230 border id=\"captcha_iframe\"");
					sb.append("src=");
					sb.append("/captchatest/ImageCaptchaServlet?captchaChars="+captchaChars+"&&"+"color="+bgColor+"&&"+"deformation="+deformation+"&&"+"background="+background+"&&"+"complexity="+complexity+"&&"+"caseSensitivity="+caseSensitivity+"></iframe>");
					sb.append("</div>");
					sb.append("<div class=\"captcha_controls_div\" style=\"width:60px;height:140px;display:inline-block;margin-top:10px;\">");
					sb.append("<img id=\"reload_btn\" src=\"/captchatest/captcha_backgrounds/ReloadIcon.gif\" alt=\"ReloadIcon\" style=\"padding-right:5px;\"></img>");
					sb.append("</div>");
					sb.append("</div>");
					out.write(sb.toString());
				}
				else if(soundCaptcha.equals(("enabled").toLowerCase())){
					sb.append("<div class=\"captcha_div\" style=\"width:300px; height:130px;display:block;\">");
					sb.append("<div class=\"captcha_image_div\" style=\"width:220px;height:140px;display:inline-block;vertical-align:top;\">");
					sb.append("<iframe frameborder=0 height=140 width=230 border id=\"captcha_iframe\"");
					sb.append("src=");
					sb.append("/captchatest/ImageCaptchaServlet?captchaChars="+captchaChars+"&&"+"color="+bgColor+"&&"+"deformation="+deformation+"&&"+"background="+background+"&&"+"complexity="+complexity+"&&"+"caseSensitivity="+caseSensitivity+"></iframe>");
					sb.append("</div>");
					sb.append("<div class=\"captcha_controls_div\" style=\"width:60px;height:140px;display:inline-block;margin-top:10px;\">");
					sb.append("<img id=\"reload_btn\" src=\"/captchatest/captcha_backgrounds/ReloadIcon.gif\" alt=\"ReloadIcon\" style=\"padding-right:5px;\"></img>");
					sb.append("<img id=\"sound_btn\" onClick=\"generateSound('/captchatest/SoundCaptchaServlet');\" src=\"/captchatest/captcha_backgrounds/SoundIcon.gif\" alt=\"SoundIcon\"></img>");
					sb.append("<span id=\"dummyspan\"></span></div>");
					sb.append("</div>");
					out.write(sb.toString());
				}
				

			} catch (IOException e) {
				e.printStackTrace();
			}
	    }catch(Exception e){
	    	e.printStackTrace();
	    }  
	      
	    return SKIP_BODY;  
	}
	 
	
	
}
