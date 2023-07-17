package com.dynatrace;

import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;

public class DynaDevExApp {

	public static void main(String[] args) throws Exception {
		String slack_bot_token = System.getenv("SLACK_BOT_TOKEN");
		String slack_signing_secret = System.getenv("SLACK_SIGNING_SECRET");
		System.out.println(slack_bot_token + " " + slack_signing_secret);
		App app = new App();
		System.out.println(app.config());
		app.command("/acc", (req, ctx) -> {
			//send message to app
			ctx.client().chatPostMessage(r -> r
					.channel(ctx.getRequestUserId())
					.text("#accomplishment: " + req.getPayload().getText())
			);
			//send ack message
			return ctx.ack("New accomplishment added to DynaDevEx: " + req.getPayload().getText());
		});
		app.command("/block", (req, ctx) -> {
			//send message to app
			ctx.client().chatPostMessage(r -> r
					.channel(ctx.getRequestUserId())
					.text("#blocker: " + req.getPayload().getText())
			);
			//send ack message
			return ctx.ack("New blocker added to DynaDevEx: " + req.getPayload().getText());
		});
		SlackAppServer server = new SlackAppServer(app);
		server.start();
	}
}
