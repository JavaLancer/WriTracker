package com.qbit.Assignment;

import com.qbit.Dialogs.SimpleBrowser;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.swing.*;

public class TwitterController {

    public static Twitter TWITTER_INSTANCE = new TwitterFactory().getInstance();
    private static RequestToken requestToken = null;

    public static void connectToTwitter() {
        TWITTER_INSTANCE = new TwitterFactory().getInstance();
        TWITTER_INSTANCE.setOAuthConsumer("gfLcjIGfRenvVWNyxyQnooShC", "bHoR3N8Wa8DjPm44oz21RuK44Se4lnFcu4a6pxZfecRdtg5u1m");
        try {
            requestToken = TWITTER_INSTANCE.getOAuthRequestToken();
        } catch (TwitterException e) {
            // Log something
            return;
        }

		final String authorizationURL = requestToken.getAuthenticationURL();

		SimpleBrowser fbBrowser = new SimpleBrowser();
		fbBrowser.loadURL(authorizationURL);
		fbBrowser.setVisible(true);
	}

    public static void getAccessToken(String code) {
        if (SimpleBrowser.AUTH_CODE != null || !SimpleBrowser.AUTH_CODE.equals("")) {
            try {
                AccessToken accessToken = TWITTER_INSTANCE.getOAuthAccessToken(requestToken, code);
                TWITTER_INSTANCE.setOAuthAccessToken(new AccessToken(accessToken.getToken(), accessToken.getTokenSecret()));
                WriDemo.saveAccessToken(TWITTER_INSTANCE.getOAuthAccessToken().getToken(), TWITTER_INSTANCE.getOAuthAccessToken().getTokenSecret(), "Twitter");
            } catch (TwitterException e) {
                // Log something
            }
        } else {
            JOptionPane.showMessageDialog(null, "Unable to communicate with Twitter, please try again.", "Error Connecting", JOptionPane.ERROR_MESSAGE);
        }
    }


	public static void postToTwitter(String Message) {
        try {
            TWITTER_INSTANCE.verifyCredentials();
        } catch (IllegalStateException ignore) {
            JOptionPane.showMessageDialog(null, "WriTracker is not connected to Twitter, please connect to have rewards tweeted.", "Error Tweeting Accomplishment", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (TwitterException e) {
            // Log something
        }

        if (Message.length() > 139) {
            Message = Message.substring(0,139);
        }

        StatusUpdate statusUpdate = new StatusUpdate(Message);
        try {
            TWITTER_INSTANCE.updateStatus(statusUpdate);
        } catch (TwitterException e) {
            // Log something
        }
    }

}
