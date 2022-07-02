package com.tasty.what_time_is_it;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WhatTimeIsIt.MODID)
public class WhatTimeIsIt
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "what_time_is_it";
    // Directly reference a slf4j logger
    //private static final Logger LOGGER = LogUtils.getLogger();
    

    public static boolean isSimpleCoordinatesHudInstalled = true;

    public WhatTimeIsIt()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new HudClock());
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
    }

    //PostInitialization is called after the mod is loaded, and after the world is loaded.
    @SubscribeEvent
    public void postInit(FMLClientSetupEvent event)
    {
        // Register the model loader
        isSimpleCoordinatesHudInstalled = ModList.get().getModContainerById("simplecoordinateshud").isPresent();
    }
}
