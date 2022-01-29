package net.coolsimulations.InfinityWaterBucket;

import java.net.URL;
import java.util.Scanner;

import net.minecraft.class_1687;
import net.minecraft.util.Formatting;

public class IWBUpdateHandler {
	
	private static String latestVersion;
	private static String latestVersionInfo;
	public static boolean isOld = false;
	public static class_1687 updateInfo = null;
	public static class_1687 updateVersionInfo = null;
	
	public static void init() {
		
		try {
            URL url = new URL("http://coolsimulations.net/mcmods/infinity-water-bucket-fabric/versionchecker16.txt");
            Scanner s = new Scanner(url.openStream());
            latestVersion = s.next();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		try {
			URL url = new URL("http://coolsimulations.net/mcmods/infinity-water-bucket-fabric/updateinfo16.txt");
			Scanner s = new Scanner(url.openStream());
			latestVersionInfo = s.nextLine();
			s.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if(latestVersion != null) {
			
			if(latestVersion.equals("ended")) {
				
				isOld = true;
				
				class_1687 iwb = class_1687.method_6028(Formatting.BLUE + IWBReference.MOD_NAME + Formatting.YELLOW);
				iwb.method_6009(Formatting.BLUE);
				
				class_1687 MCVersion = class_1687.method_6028(Formatting.BLUE + "1.6.4" + Formatting.YELLOW);
				
				updateInfo = class_1687.method_6020(InfinityWaterBucket.langTranslations("iwb.update.display3"), new Object[] {iwb});
				updateInfo.method_6009(Formatting.YELLOW);
				
			}
			
			if(!latestVersion.equals(IWBReference.VERSION) && !latestVersion.equals("ended")) {
				
				isOld = true;
				
				class_1687 iwb = class_1687.method_6028(Formatting.BLUE + IWBReference.MOD_NAME + Formatting.YELLOW);
				
				class_1687 version = class_1687.method_6028(Formatting.BLUE + latestVersion + Formatting.YELLOW);
				
				updateInfo = class_1687.method_6020(InfinityWaterBucket.langTranslations("iwb.update.display1"), new Object[] {iwb, version});
				updateInfo.method_6009(Formatting.YELLOW);
				
				if(latestVersionInfo != null) {

					updateVersionInfo = class_1687.method_6028(latestVersionInfo);
					updateVersionInfo.method_6009(Formatting.DARK_AQUA);
					updateVersionInfo.method_6011(true);
				}
				
			}
			
		}
	}

}