package net.coolsimulations.InfinityWaterBucket;

import java.net.URL;
import java.util.Scanner;

public class IWBUpdateHandler {
	
	private static String latestVersion;
	private static String latestVersionInfo;
	public static boolean isOld = false;
	public static String updateInfo = null;
	public static String updateVersionInfo = null;
	public static String iwb = "§9" + IWBReference.MOD_NAME + "§e";
	public static String version = "";
	
	public static void init() {
		
		try {
            URL url = new URL("http://coolsimulations.net/mcmods/infinity-water-bucket-fabric/versionchecker14.txt");
            Scanner s = new Scanner(url.openStream());
            latestVersion = s.next();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		try {
			URL url = new URL("http://coolsimulations.net/mcmods/infinity-water-bucket-fabric/updateinfo14.txt");
			Scanner s = new Scanner(url.openStream());
			latestVersionInfo = s.nextLine();
			s.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if(latestVersion != null) {
			
			if(latestVersion.equals("ended")) {
				
				isOld = true;
				
				version = "§9" + "1.4.7" + "§e";
				
				updateInfo = "§e" + InfinityWaterBucket.langTranslations("iwb.update.display3") + "§r";
				
			}
			
			if(!latestVersion.equals(IWBReference.VERSION) && !latestVersion.equals("ended")) {
				
				isOld = true;
				
				version = "§9" + latestVersion + "§e";
				
				updateInfo = "§e" + InfinityWaterBucket.langTranslations("iwb.update.display1") + "§r";
				
				if(latestVersionInfo != null) {

					updateVersionInfo = "§3" + "§l" + latestVersionInfo + "§r";
				}
				
			}
			
		}
	}

}