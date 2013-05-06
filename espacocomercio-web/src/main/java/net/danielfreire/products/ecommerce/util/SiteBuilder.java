package net.danielfreire.products.ecommerce.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.danielfreire.util.ConvertTools;
import net.danielfreire.util.ValidateTools;

public class SiteBuilder {
	
	private transient final Map<String,String> map = new HashMap<String, String>();
	private static final String RESER_TO_FACE_CF = "<!--ReservedToFacebookConf-->";
	private static final String RESER_TO_FACE = "<!--ReservedToFacebook-->";
	private static final String FACEBOOK_CONF = "${facebook-conf}";
	private static final String FACEBOOK_LIKE = "${facebook-like}";
	private static final String FB_ROOT = "<div id=\"fb-root\"></div><script>(function(d, s, id) {var js, fjs = d.getElementsByTagName(s)[0];if (d.getElementById(id)) return;js = d.createElement(s); js.id = id;js.src = \"//connect.facebook.net/en_US/all.js#xfbml=1\"; fjs.parentNode.insertBefore(js, fjs);}(document, 'script', 'facebook-jssdk'));</script>";
	
	public SiteBuilder setTitleSite(final String titleOther, final String titleNew) {
		if (titleOther!=null) {
			map.put(titleOther, titleNew);
		}
		return this;
	}
	
	public SiteBuilder setDescriptionSite(final String descOther, final String descNew) {
		if (descOther!=null) {
			map.put(descOther.length()>=150 ? descOther.substring(0, 150) : descOther, descNew.length()>=150 ? descNew.substring(0, 150) : descNew);
		}
		return this;
	}
	
	public SiteBuilder setFacebook(final String faceOther, final String faceNew) {
		if ("create".equals(faceOther) && ValidateTools.getInstancia().isNullEmpty(faceNew)) {
			map.put(FACEBOOK_LIKE, RESER_TO_FACE);
			map.put(FACEBOOK_CONF, RESER_TO_FACE_CF);
		} else if ("create".equals(faceOther) && ValidateTools.getInstancia().isNotnull(faceNew)) {
			map.put(FACEBOOK_LIKE, getFbLike(faceNew));
			map.put(FACEBOOK_CONF, FB_ROOT);
		} else if (ValidateTools.getInstancia().isNullEmpty(faceOther) && ValidateTools.getInstancia().isNotnull(faceNew)) {
			map.put(RESER_TO_FACE, getFbLike(faceNew));
			map.put(RESER_TO_FACE_CF, FB_ROOT);
		} else if (ValidateTools.getInstancia().isNotnull(faceOther) && ValidateTools.getInstancia().isNotnull(faceNew)) {
			map.put(faceOther, faceNew);
		} else if (ValidateTools.getInstancia().isNotnull(faceOther) && ValidateTools.getInstancia().isNullEmpty(faceNew)) {
			map.put(getFbLike(faceOther), RESER_TO_FACE);
			map.put(FB_ROOT, RESER_TO_FACE_CF);
		}
		return this;
	}

	public SiteBuilder setBannerAlt(final String altOther, final String altNew) {
		if (altOther!=null) {
			map.put(altOther, altNew);
		}
		return this;
	}
	
	public SiteBuilder setPathHome(final String pathOther, final String pathNew) {
		if (pathOther!=null) {
			map.put(pathOther, pathNew);
		}
		return this;
	}
	
	public SiteBuilder setSiteIdCripto(final String siteIdOther, final String siteIdNew) {
		if (siteIdOther!=null) {
			map.put(siteIdOther, siteIdNew);
		}
		return this;
	}
	
	public SiteBuilder setContext(final String contextOther, final String contextNew) {
		if (contextOther!=null) {
			map.put(contextOther, contextNew);
		}
		return this;
	}
	
	public SiteBuilder setLogo(final String logoOther, final String logoNew) {
		if (logoOther!=null) {
			map.put(logoOther, logoNew);
		}
		return this;
	}
	
	private String getFbLike(final String urlFb) {
		return "<div class=\"fb-like\" data-href=\""+urlFb+"\" data-send=\"true\" data-layout=\"button_count\" data-show-faces=\"true\"></div><br><br>";
	}

	public void build(final String dirTo) throws java.lang.Exception {
		ConvertTools.getInstance().replaceFile(map, new File(dirTo+"/index.html"));
		ConvertTools.getInstance().replaceFile(map, new File(dirTo+"/js/functions.js"));
		ConvertTools.getInstance().replaceFile(map, new File(dirTo+"/category/index.html"));
		ConvertTools.getInstance().replaceFile(map, new File(dirTo+"/client/index.html"));
		ConvertTools.getInstance().replaceFile(map, new File(dirTo+"/mycart/index.html"));
		ConvertTools.getInstance().replaceFile(map, new File(dirTo+"/product/index.html"));
	}
}
