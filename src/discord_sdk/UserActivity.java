package discord_sdk;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import robatortas.code.files.project.settings.Globals;

/*
 * CREDITS TO: JnCrMx
 * REPO LINK: https://github.com/JnCrMx/discord-game-sdk4j
 */
public class UserActivity {
	
	public UserActivity() {
		System.out.println("\nStarting Discord's SDK\n");
		
		File discordLibrary = null;
		try {
			discordLibrary = DownloadNativeLibrary.downloadDiscordLibrary();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(discordLibrary == null)
		{
			System.err.println("Error downloading Discord SDK.");
			System.exit(-1);
		}
		// Initialize the Core
		Core.init(discordLibrary);

		// Set parameters for the Core
		try(CreateParams params = new CreateParams())
		{
			params.setClientID(1045527123466657812L);
			params.setFlags(CreateParams.getDefaultFlags());
			
			// Create the Core
			try(Core core = new Core(params))
			{
				// Create the Activity
				try(Activity activity = new Activity())
				{
					activity.setDetails(Globals.VERSION);
//					activity.setState("Concentrated");

					// Setting a start time causes an "elapsed" field to appear
					activity.timestamps().setStart(Instant.now());

					// Make a "cool" image show up
					activity.assets().setLargeImage("big");
//					activity.assets().setSmallImage("small");

					// Setting a join secret and a party ID causes an "Ask to Join" button to appear
//					activity.party().setID("Party!");
//					activity.secrets().setJoinSecret("Join!");

					// Finally, update the current activity to our activity
					core.activityManager().updateActivity(activity);
				}

				// Run callbacks forever
				// Just keeps running the sdk y'know?
				while(true)
				{
					core.runCallbacks();
					try
					{
						// Sleep a bit to save CPU
						Thread.sleep(16);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
}