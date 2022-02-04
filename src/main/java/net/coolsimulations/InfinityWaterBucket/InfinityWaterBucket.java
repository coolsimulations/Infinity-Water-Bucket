package net.coolsimulations.InfinityWaterBucket;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@Mod(modid = IWBReference.MOD_ID, name = IWBReference.MOD_NAME, version = IWBReference.VERSION, acceptedMinecraftVersions = IWBReference.ACCEPTED_VERSIONS, dependencies = IWBReference.DEPENDENCIES, acceptableRemoteVersions = "*", updateJSON = "https://coolsimulations.net/mcmods/infinity-water-bucket/versionchecker.json")
public class InfinityWaterBucket {
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		IWBUpdateHandler.init();
		IWBDispenserItemBehavior.init();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		if(IWBUpdateHandler.isOld == true && event.player instanceof EntityPlayerMP) {
			
			EntityPlayerMP player = (EntityPlayerMP) event.player;
			
			if(player.mcServer.isDedicatedServer()) {
				
				UserListOpsEntry op = player.mcServer.getConfigurationManager().getOppedPlayers().getEntry(player.getGameProfile());
				
				if(op != null && op.getPermissionLevel() == player.mcServer.getOpPermissionLevel()) {
					messageOutdated(player);
				}
			} else {
				messageOutdated(player);
			}
		}
	}

	private static void messageOutdated(EntityPlayerMP player) {
		player.addChatMessage(IWBUpdateHandler.updateInfo);
		if(IWBUpdateHandler.updateVersionInfo != null)
			player.addChatMessage(IWBUpdateHandler.updateVersionInfo);
	}
}