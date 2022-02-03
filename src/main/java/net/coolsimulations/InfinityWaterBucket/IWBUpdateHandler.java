package net.coolsimulations.InfinityWaterBucket;

import java.net.URL;
import java.util.Scanner;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.versions.mcp.MCPVersion;

public class IWBUpdateHandler {
	
	private static String latestVersion;
	private static String latestVersionInfo;
	public static boolean isOld = false;
	public static TranslationTextComponent updateInfo = null;
	public static StringTextComponent updateVersionInfo = null;
	
	public static void init() {
		
		try {
            URL url = new URL("https://coolsimulations.net/mcmods/infinity-water-bucket/versionchecker115.txt");
            Scanner s = new Scanner(url.openStream());
            latestVersion = s.next();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		try {
			URL url = new URL("https://coolsimulations.net/mcmods/infinity-water-bucket/updateinfo115.txt");
			Scanner s = new Scanner(url.openStream());
			latestVersionInfo = s.nextLine();
			s.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if(latestVersion != null) {
			
			if(latestVersion.equals("ended")) {
				
				isOld = true;
				
				StringTextComponent iwb = new StringTextComponent(IWBReference.MOD_NAME);
				iwb.getStyle().setColor(TextFormatting.BLUE);
				
				StringTextComponent MCVersion = new StringTextComponent(MCPVersion.getMCVersion());
				MCVersion.getStyle().setColor(TextFormatting.BLUE);
				
				updateInfo = new TranslationTextComponent("iwb.update.display3", new Object[] {iwb, MCVersion});
				updateInfo.getStyle().setColor(TextFormatting.YELLOW);
				
				updateInfo.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("iwb.update.display2")));
				updateInfo.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/infinity-water-bucket"));
				
			}
			
			if(!latestVersion.equals(IWBReference.VERSION) && !latestVersion.equals("ended")) {
				
				isOld = true;
				
				StringTextComponent iwb = new StringTextComponent(IWBReference.MOD_NAME);
				iwb.getStyle().setColor(TextFormatting.BLUE);
				
				StringTextComponent version = new StringTextComponent(latestVersion);
				version.getStyle().setColor(TextFormatting.BLUE);
				
				updateInfo = new TranslationTextComponent("iwb.update.display1", new Object[] {iwb, version});
				updateInfo.getStyle().setColor(TextFormatting.YELLOW);
				
				if(latestVersionInfo != null) {

					updateVersionInfo = new StringTextComponent(latestVersionInfo);
					updateVersionInfo.getStyle().setColor(TextFormatting.DARK_AQUA);
					updateVersionInfo.getStyle().setBold(true);
					
					updateVersionInfo.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("iwb.update.display2")));
					updateVersionInfo.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/infinity-water-bucket"));
				}
				
			}
			
		}
	}

}