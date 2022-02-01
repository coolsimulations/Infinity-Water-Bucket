package net.coolsimulations.InfinityWaterBucket;

import java.net.URL;
import java.util.Scanner;

import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class IWBUpdateHandler {
	
	private static String latestVersion;
	private static String latestVersionInfo;
	public static boolean isOld = false;
	public static TranslatableComponent updateInfo = null;
	public static TextComponent updateVersionInfo = null;
	
	public static void init() {
		
		try {
            URL url = new URL("https://coolsimulations.net/mcmods/infinity-water-bucket-fabric/versionchecker118.txt");
            Scanner s = new Scanner(url.openStream());
            latestVersion = s.next();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		try {
			URL url = new URL("https://coolsimulations.net/mcmods/infinity-water-bucket-fabric/updateinfo118.txt");
			Scanner s = new Scanner(url.openStream());
			latestVersionInfo = s.nextLine();
			s.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if(latestVersion != null) {
			
			if(latestVersion.equals("ended")) {
				
				isOld = true;
				
				TextComponent iwb = new TextComponent(IWBReference.MOD_NAME);
				iwb.withStyle(ChatFormatting.BLUE);
				
				TextComponent MCVersion = new TextComponent(SharedConstants.getCurrentVersion().getName());
				MCVersion.withStyle(ChatFormatting.BLUE);
				
				updateInfo = new TranslatableComponent(InfinityWaterBucket.langTranslations("iwb.update.display3"), new Object[] {iwb, MCVersion});
				updateInfo.withStyle(ChatFormatting.YELLOW);
				
			}
			
			if(!latestVersion.equals(IWBReference.VERSION) && !latestVersion.equals("ended")) {
				
				isOld = true;
				
				TextComponent iwb = new TextComponent(IWBReference.MOD_NAME);
				iwb.withStyle(ChatFormatting.BLUE);
				
				TextComponent version = new TextComponent(latestVersion);
				version.withStyle(ChatFormatting.BLUE);
				
				updateInfo = new TranslatableComponent(InfinityWaterBucket.langTranslations("iwb.update.display1"), new Object[] {iwb, version});
				updateInfo.withStyle(ChatFormatting.YELLOW);
				
				if(latestVersionInfo != null) {

					updateVersionInfo = new TextComponent(latestVersionInfo);
					updateVersionInfo.withStyle(ChatFormatting.DARK_AQUA);
					updateVersionInfo.withStyle(ChatFormatting.BOLD);
				}
				
			}
			
		}
	}

}