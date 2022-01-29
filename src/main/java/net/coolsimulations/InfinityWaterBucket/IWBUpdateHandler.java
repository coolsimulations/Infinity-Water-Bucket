package net.coolsimulations.InfinityWaterBucket;

import java.net.URL;
import java.util.Scanner;

import net.minecraft.util.Formatting;

public class IWBUpdateHandler {
	
	private static String latestVersion;
	private static String latestVersionInfo;
	public static boolean isOld = false;
	public static String updateInfo = null;
	public static String updateVersionInfo = null;
	public static String iwb = Formatting.BLUE + IWBReference.MOD_NAME + Formatting.YELLOW;
	public static String version = "";
	
	public static void init() {
		
		try {
            URL url = new URL("http://coolsimulations.net/mcmods/infinity-water-bucket-fabric/versionchecker15.txt");
            Scanner s = new Scanner(url.openStream());
            latestVersion = s.next();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		try {
			URL url = new URL("http://coolsimulations.net/mcmods/infinity-water-bucket-fabric/updateinfo15.txt");
			Scanner s = new Scanner(url.openStream());
			latestVersionInfo = s.nextLine();
			s.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if(latestVersion != null) {
			
			if(latestVersion.equals("ended")) {
				
				isOld = true;
				
				version = Formatting.BLUE + "1.5.2" + Formatting.YELLOW;
				
				updateInfo = Formatting.YELLOW + InfinityWaterBucket.langTranslations("iwb.update.display3") + Formatting.RESET;
				
			}
			
			if(!latestVersion.equals(IWBReference.VERSION) && !latestVersion.equals("ended")) {
				
				isOld = true;
				
				version = Formatting.BLUE + latestVersion + Formatting.YELLOW;
				
				updateInfo = Formatting.YELLOW + InfinityWaterBucket.langTranslations("iwb.update.display1") + Formatting.RESET;
				
				if(latestVersionInfo != null) {

					updateVersionInfo = Formatting.DARK_AQUA + "" + Formatting.BOLD + latestVersionInfo + Formatting.RESET;
				}
				
			}
			
		}
	}

}