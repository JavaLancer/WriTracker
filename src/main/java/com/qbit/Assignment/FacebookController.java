package com.qbit.Assignment;

import com.qbit.Dialogs.SimpleBrowser;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.RawAPIResponse;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class FacebookController {
	public static Facebook FACEBOOK_INSTANCE = new FacebookFactory().getInstance();

	public static void postToFacebook(String message) {
        try {
            FACEBOOK_INSTANCE.getOAuthAccessToken();
            if (FACEBOOK_INSTANCE.getOAuthAccessToken().getToken() == null) {
                throw new IllegalStateException();
            }
        } catch (IllegalStateException ignore) {
            JOptionPane.showMessageDialog(null, "WriTracker is not connected to Facebook, please connect to have rewards posted.", "Error Posting Accomplishment", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Map<String, String> map = new HashMap<>();
		map.put("input_token", FACEBOOK_INSTANCE.getOAuthAccessToken().getToken());
		boolean isValid = false;
		try {
			RawAPIResponse response = FACEBOOK_INSTANCE.rawAPI().callGetAPI("debug_token", map);
			JSONObject accessTokenInfo = response.asJSONObject();
			JSONObject data = accessTokenInfo.getJSONObject("data");
			isValid = data.getBoolean("is_valid");
		} catch (FacebookException e) {
			// Log something
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (!isValid) {
            // TODO: auto refresh access token
            JOptionPane.showMessageDialog(null, "WriTracker is not connected to Facebook, please connect to have rewards posted.", "Error Posting Accomplishment", JOptionPane.ERROR_MESSAGE);
            return;
		}

		try {
			FACEBOOK_INSTANCE.postStatusMessage(message);
		} catch (FacebookException e) {
			// Log something
		}
	}

	public static void connectToFacebook() {
		FACEBOOK_INSTANCE = new FacebookFactory().getInstance();
		FACEBOOK_INSTANCE.setOAuthAppId("1509940699313820", "5c9700b49ea2cd74432d6b101074196f");
		FACEBOOK_INSTANCE.setOAuthPermissions("publish_actions");

		final String authorizationURL = FACEBOOK_INSTANCE.getOAuthAuthorizationURL("http://www.example.com/oauth_callback/");

		SimpleBrowser fbBrowser = new SimpleBrowser();
		fbBrowser.loadURL(authorizationURL);
		fbBrowser.setVisible(true);
	}

	public static void getAccessToken(String code) {
		if (SimpleBrowser.AUTH_CODE != null || !SimpleBrowser.AUTH_CODE.equals("")) {
			try {
				AccessToken token = FACEBOOK_INSTANCE.getOAuthAccessToken(code, "http://www.example.com/oauth_callback/");
				FACEBOOK_INSTANCE.setOAuthAccessToken(new AccessToken(token.getToken(), token.getExpires()));
			} catch (FacebookException e) {
				// Log something
			}
			WriDemo.saveAccessToken(FACEBOOK_INSTANCE.getOAuthAccessToken().getToken(), null, "Facebook");
		} else {
			JOptionPane.showMessageDialog(null, "Unable to communicate with Facebook, please try again.", "Error Connecting", JOptionPane.ERROR_MESSAGE);
		}
	}
}
