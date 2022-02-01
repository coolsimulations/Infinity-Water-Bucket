package net.coolsimulations.InfinityWaterBucket;

import java.net.URL;
import java.util.Scanner;

import net.minecraft.class_1981;
import net.minecraft.class_1985;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class IWBUpdateHandler {
	
	private static String latestVersion;
	private static String latestVersionInfo;
	public static boolean isOld = false;
	public static TranslatableText updateInfo = null;
	public static LiteralText updateVersionInfo = null;
	
	public static void init() {
		
		try {
            URL url = new URL("http://coolsimulations.net/mcmods/infinity-water-bucket-fabric/versionchecker17.txt");
            Scanner s = new Scanner(url.openStream());
            latestVersion = s.next();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		try {
			URL url = new URL("http://coolsimulations.net/mcmods/infinity-water-bucket-fabric/updateinfo17.txt");
			Scanner s = new Scanner(url.openStream());
			latestVersionInfo = s.nextLine();
			s.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if(latestVersion != null) {
			
			if(latestVersion.equals("ended")) {
				
				isOld = true;
				
				LiteralText iwb = new LiteralText(IWBReference.MOD_NAME);
				iwb.getStyle().setFormatting(Formatting.BLUE);
				
				LiteralText MCVersion = new LiteralText("1.7.10");
				MCVersion.getStyle().setFormatting(Formatting.BLUE);
				
				updateInfo = new TranslatableText(InfinityWaterBucket.langTranslations("iwb.update.display3"), new Object[] {iwb, MCVersion});
				updateInfo.getStyle().setFormatting(Formatting.YELLOW);
				
				updateInfo.getStyle().setHoverEvent(new HoverEvent(class_1985.field_8488, new TranslatableText(InfinityWaterBucket.langTranslations("iwb.update.display2"))));
				updateInfo.getStyle().setClickEvent(new ClickEvent(class_1981.field_8476, "https://curseforge.com/minecraft/mc-mods/infinity-water-bucket"));
				
			}
			
			if(!latestVersion.equals(IWBReference.VERSION) && !latestVersion.equals("ended")) {
				
				isOld = true;
				
				LiteralText iwb = new LiteralText(IWBReference.MOD_NAME);
				iwb.getStyle().setFormatting(Formatting.BLUE);
				
				LiteralText version = new LiteralText(latestVersion);
				version.getStyle().setFormatting(Formatting.BLUE);
				
				updateInfo = new TranslatableText(InfinityWaterBucket.langTranslations("iwb.update.display1"), new Object[] {iwb, version});
				updateInfo.getStyle().setFormatting(Formatting.YELLOW);

				updateInfo.getStyle().setHoverEvent(new HoverEvent(class_1985.field_8488, new TranslatableText(InfinityWaterBucket.langTranslations("iwb.update.display2"))));
				updateInfo.getStyle().setClickEvent(new ClickEvent(class_1981.field_8476, "https://curseforge.com/minecraft/mc-mods/infinity-water-bucket"));
				
				if(latestVersionInfo != null) {

					updateVersionInfo = new LiteralText(latestVersionInfo);
					updateVersionInfo.getStyle().setFormatting(Formatting.DARK_AQUA);
					updateVersionInfo.getStyle().setBold(true);
					
					updateVersionInfo.getStyle().setHoverEvent(new HoverEvent(class_1985.field_8488, new TranslatableText(InfinityWaterBucket.langTranslations("iwb.update.display2"))));
					updateVersionInfo.getStyle().setClickEvent(new ClickEvent(class_1981.field_8476, "https://curseforge.com/minecraft/mc-mods/infinity-water-bucket"));
				}
				
			}
			
		}
	}

}