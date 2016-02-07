package com.qbit.Assignment;
import twitter4j.*;
import twitter4j.auth.AccessToken;

public class TwitterController {
	
	public static void postTwitter(String consumerKey,String consumerSecret,String accessToken,String accessTokenSecret,String Message){
		//
		   //Your Twitter App's Consumer Key
	       // String consumerKey = "XXXXXXXXXXXXXXXXXXXXX";

	        //Your Twitter App's Consumer Secret
	      //  String consumerSecret = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

	        //Your Twitter Access Token
	      //  String accessToken = "XXXXXXXX-XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

	        //Your Twitter Access Token Secret
	      //  String accessTokenSecret = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

	        //Instantiate a re-usable and thread-safe factory
	        TwitterFactory twitterFactory = new TwitterFactory();

	        //Instantiate a new Twitter instance
	        Twitter twitter = twitterFactory.getInstance();

	        //setup OAuth Consumer Credentials
	        twitter.setOAuthConsumer(consumerKey, consumerSecret);

	        //setup OAuth Access Token
	        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

	        if(Message.length() > 139)
	        	Message = Message.substring(0,139);
	        
	        //Instantiate and initialize a new twitter status update
	        StatusUpdate statusUpdate = new StatusUpdate(Message);
	       
	        try {
				Status status = twitter.updateStatus(statusUpdate);//
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//
	}

}
