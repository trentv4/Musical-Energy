package net.trentv.musicalenergy;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.trentv.musicalenergy.common.CommonProxy;
import net.trentv.musicalenergy.common.MusicalObjects;

@Mod(modid = MusicalEnergy.MODID, version = MusicalEnergy.VERSION)
public class MusicalEnergy
{
	@SidedProxy(clientSide = "net.trentv.musicalenergy.client.ClientProxy", serverSide = "net.trentv.musicalenergy.server.ServerProxy")
	public static CommonProxy proxy;

	public static final String MODID = "musicalenergy";
	public static final String VERSION = "1.0";

	public static Logger logger;

	/*
	 * Pitch: instruments which give you access to 'notes' (elements) which can
	 * combine together into spells for offense, building, and mining. Think
	 * Magicka spells but with 
	 */

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();

		MusicalObjects.init();
		proxy.registerEventHandlers();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		logger.info(MODID + " initialized");
	}
}