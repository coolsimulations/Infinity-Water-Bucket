package net.coolsimulations.InfinityWaterBucket;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = IWBReference.MOD_ID, name = IWBReference.MOD_NAME, version = IWBReference.VERSION, acceptedMinecraftVersions = IWBReference.ACCEPTED_VERSIONS, dependencies = IWBReference.DEPENDENCIES, acceptableRemoteVersions = "*")
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
				
				UserListOpsEntry op = (UserListOpsEntry)player.mcServer.getConfigurationManager().getOppedPlayers().getEntry(player.getGameProfile());
				
				if(op != null && op.func_152644_a() == player.mcServer.getOpPermissionLevel()) {
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