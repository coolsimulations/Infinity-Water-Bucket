package net.coolsimulations.InfinityWaterBucket;

import java.net.URL;
import java.util.Scanner;

import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class IWBUpdateHandler {
	
	private static String latestVersion;
	private static String latestVersionInfo;
	public static boolean isOld = false;
	public static TranslationTextComponent updateInfo = null;
	public static TextComponent updateVersionInfo = null;
	
	public static void init() {
		
		try {
            URL url = new URL("https://coolsimulations.net/mcmods/infinity-water-bucket/versionchecker116.txt");
            Scanner s = new Scanner(url.openStream());
            latestVersion = s.next();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		try {
			URL url = new URL("https://coolsimulations.net/mcmods/infinity-water-bucket/updateinfo116.txt");
			Scanner s = new Scanner(url.openStream());
			latestVersionInfo = s.nextLine();
			s.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if(latestVersion != null) {
			
			if(latestVersion.equals("ended")) {
				
				isOld = true;
				
				TextComponent iwb = new StringTextComponent(IWBReference.MOD_NAME);
				iwb.withStyle(TextFormatting.BLUE);
				
				TextComponent MCVersion = new StringTextComponent(SharedConstants.getCurrentVersion().getName());
				MCVersion.withStyle(TextFormatting.BLUE);
				
				updateInfo = new TranslationTextComponent("iwb.update.display3", new Object[] {iwb, MCVersion});
				updateInfo.withStyle(TextFormatting.YELLOW);
				
			}
			
			if(!latestVersion.equals(IWBReference.VERSION) && !latestVersion.equals("ended")) {
				
				isOld = true;
				
				TextComponent iwb = new StringTextComponent(IWBReference.MOD_NAME);
				iwb.withStyle(TextFormatting.BLUE);
				
				TextComponent version = new StringTextComponent(latestVersion);
				version.withStyle(TextFormatting.BLUE);
				
				updateInfo = new TranslationTextComponent("iwb.update.display1", new Object[] {iwb, version});
				updateInfo.withStyle(TextFormatting.YELLOW);
				
				if(latestVersionInfo != null) {

					updateVersionInfo = new StringTextComponent(latestVersionInfo);
					updateVersionInfo.withStyle(TextFormatting.DARK_AQUA);
					updateVersionInfo.withStyle(TextFormatting.BOLD);
				}
				
			}
			
		}
	}

}