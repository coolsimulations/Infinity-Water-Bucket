package net.coolsimulations.InfinityWaterBucket;

import java.net.URL;
import java.util.Scanner;

import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

public class IWBUpdateHandler {
	
	private static String latestVersion;
	private static String latestVersionInfo;
	public static boolean isOld = false;
	public static ChatComponentTranslation updateInfo = null;
	public static ChatComponentText updateVersionInfo = null;
	
	public static void init() {
		
		try {
            URL url = new URL("https://coolsimulations.net/mcmods/infinity-water-bucket/versionchecker18.txt");
            Scanner s = new Scanner(url.openStream());
            latestVersion = s.next();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		try {
			URL url = new URL("https://coolsimulations.net/mcmods/infinity-water-bucket/updateinfo18.txt");
			Scanner s = new Scanner(url.openStream());
			latestVersionInfo = s.nextLine();
			s.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if(latestVersion != null) {
			
			if(latestVersion.equals("ended")) {
				
				isOld = true;
				
				ChatComponentText iwb = new ChatComponentText(IWBReference.MOD_NAME);
				iwb.getChatStyle().setColor(EnumChatFormatting.BLUE);
				
				ChatComponentText MCVersion = new ChatComponentText(MinecraftForge.MC_VERSION);
				MCVersion.getChatStyle().setColor(EnumChatFormatting.BLUE);
				
				updateInfo = new ChatComponentTranslation("iwb.update.display3", new Object[] {iwb, MCVersion});
				updateInfo.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				
				updateInfo.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentTranslation("iwb.update.display2")));
				updateInfo.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/infinity-water-bucket"));
				
			}
			
			if(!latestVersion.equals(IWBReference.VERSION) && !latestVersion.equals("ended")) {
				
				isOld = true;
				
				ChatComponentText iwb = new ChatComponentText(IWBReference.MOD_NAME);
				iwb.getChatStyle().setColor(EnumChatFormatting.BLUE);
				
				ChatComponentText version = new ChatComponentText(latestVersion);
				version.getChatStyle().setColor(EnumChatFormatting.BLUE);
				
				updateInfo = new ChatComponentTranslation("iwb.update.display1", new Object[] {iwb, version});
				updateInfo.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				
				if(latestVersionInfo != null) {

					updateVersionInfo = new ChatComponentText(latestVersionInfo);
					updateVersionInfo.getChatStyle().setColor(EnumChatFormatting.DARK_AQUA);
					updateVersionInfo.getChatStyle().setBold(true);
					
					updateVersionInfo.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentTranslation("iwb.update.display2")));
					updateVersionInfo.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/infinity-water-bucket"));
				}
				
			}
			
		}
	}

}