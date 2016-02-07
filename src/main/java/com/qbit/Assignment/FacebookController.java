package com.qbit.Assignment;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;

public class FacebookController {
	
	public static void PostOnFB(String appID,String appSecret, String AccessToken,String matter){
		try{
			Facebook facebook = new FacebookFactory().getInstance();
			facebook.setOAuthAppId(appID,appSecret);
			AccessToken token = new AccessToken(AccessToken);
			facebook.setOAuthAccessToken(token);
			facebook.setOAuthPermissions("user_post");
			String str = facebook.postStatusMessage(matter);
			System.out.println("Posted on FB Successfully");
		}catch(Exception c){
			System.out.println("FaceBook Post Exc:"+c.getMessage());
			c.printStackTrace();
		}
		
	}

}
