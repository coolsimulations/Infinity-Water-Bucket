package net.coolsimulations.InfinityWaterBucket;

import java.net.URL;
import java.util.Scanner;

import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.common.MinecraftForge;

public class IWBUpdateHandler {
	
	private static String latestVersion;
	private static String latestVersionInfo;
	public static boolean isOld = false;
	public static TextComponentTranslation updateInfo = null;
	public static TextComponentString updateVersionInfo = null;
	
	public static void init() {
		
		try {
            URL url = new URL("https://coolsimulations.net/mcmods/infinity-water-bucket/versionchecker19.txt");
            Scanner s = new Scanner(url.openStream());
            latestVersion = s.next();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		try {
			URL url = new URL("https://coolsimulations.net/mcmods/infinity-water-bucket/updateinfo19.txt");
			Scanner s = new Scanner(url.openStream());
			latestVersionInfo = s.nextLine();
			s.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if(latestVersion != null) {
			
			if(latestVersion.equals("ended")) {
				
				isOld = true;
				
				TextComponentString iwb = new TextComponentString(IWBReference.MOD_NAME);
				iwb.getStyle().setColor(TextFormatting.BLUE);
				
				TextComponentString MCVersion = new TextComponentString(MinecraftForge.MC_VERSION);
				MCVersion.getStyle().setColor(TextFormatting.BLUE);
				
				updateInfo = new TextComponentTranslation("iwb.update.display3", new Object[] {iwb, MCVersion});
				updateInfo.getStyle().setColor(TextFormatting.YELLOW);
				
				updateInfo.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("iwb.update.display2")));
				updateInfo.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/infinity-water-bucket"));
				
			}
			
			if(!latestVersion.equals(IWBReference.VERSION) && !latestVersion.equals("ended")) {
				
				isOld = true;
				
				TextComponentString iwb = new TextComponentString(IWBReference.MOD_NAME);
				iwb.getStyle().setColor(TextFormatting.BLUE);
				
				TextComponentString version = new TextComponentString(latestVersion);
				version.getStyle().setColor(TextFormatting.BLUE);
				
				updateInfo = new TextComponentTranslation("iwb.update.display1", new Object[] {iwb, version});
				updateInfo.getStyle().setColor(TextFormatting.YELLOW);
				
				if(latestVersionInfo != null) {

					updateVersionInfo = new TextComponentString(latestVersionInfo);
					updateVersionInfo.getStyle().setColor(TextFormatting.DARK_AQUA);
					updateVersionInfo.getStyle().setBold(true);
					
					updateVersionInfo.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("iwb.update.display2")));
					updateVersionInfo.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/infinity-water-bucket"));
				}
				
			}
			
		}
	}

}