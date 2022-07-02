package com.tasty.what_time_is_it;

import com.mojang.blaze3d.vertex.PoseStack;
//Import the minecraft stuff
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WhatTimeIsIt.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HudClock {
    public static Minecraft mc = Minecraft.getInstance();
    private static boolean isDebugScreenOpen = false;

    // OnRenderGameOverlay
    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.DEBUG)
        {
            isDebugScreenOpen = true;
        }
        else
        {
            isDebugScreenOpen = false;
        }
    }

    // OnOverlayRender is called when the game is rendering the world, and the HUD
    // is being rendered.
    @SubscribeEvent
    public void onOverlayRender(net.minecraftforge.client.event.RenderGameOverlayEvent.Pre event) {
        // If the event is for the hud, and we are in creative mode, render the clock.
        if (event.getType() == net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.TEXT) {
            if(!isDebugScreenOpen)
            {
                renderClock();
            }
        }
    }

    private static final double mCraftMinuteConversion = 1.21;

    public HudClock() {
    }

    // Render the clock to the screen.
    public static void renderClock() {

        //Get the time
        Time time = getTime();

        // Draw string to the screen.
        PoseStack poseStack = new PoseStack();

        if(WhatTimeIsIt.isSimpleCoordinatesHudInstalled)
            poseStack.translate(0, 10, 0);
        else
            poseStack.translate(0, 0, 0);

        //Always format numbers to two digits
        String timeString = String.format("%02d", time.hours) + ":" + String.format("%02d", time.minutes);

        Gui.drawString(poseStack, mc.font, timeString, 2, 2, 0xFFFFFF);

        // Draw placeholder for coordinates hud.
        //PoseStack poseStack2 = new PoseStack();
        //poseStack.translate(0, 0, 0);

        //Gui.drawString(poseStack2, mc.font, "X:? Y:? Z:?",  2, 2, 0xFFFFFF);
    }

    private static int convertHour(int mcHour) {
        int hour = mcHour;
        // Convert hours to 24-hour format.
        // when hour is zero it is 6:00 AM
        // when hour is 1 it is 7:00 AM
        // when hour is 2 it is 8:00 AM
        // when hour is 3 it is 9:00 AM
        // when hour is 4 it is 10:00 AM 
        // when hour is 5 it is 11:00 AM
        // when hour is 6 it is 12:00 AM
        // when hour is 7 it is 1:00 PM
        // when hour is 8 it is 2:00 PM
        // when hour is 9 it is 3:00 PM
        // when hour is 10 it is 4:00 PM
        // when hour is 11 it is 5:00 PM
        // when hour is 12 it is 6:00 PM
        // when hour is 13 it is 7:00 PM
        // when hour is 14 it is 8:00 PM
        // when hour is 15 it is 9:00 PM
        // when hour is 16 it is 10:00 PM
        // when hour is 17 it is 11:00 PM
        // when hour is 18 it is 12:00 AM
        // when hour is 19 it is 1:00 AM
        // when hour is 20 it is 2:00 AM
        // when hour is 21 it is 3:00 AM
        // when hour is 22 it is 4:00 AM
        // when hour is 23 it is 5:00 AM

        //There is probably a better way to do this but I don't know how
        switch(hour) {
            case 0:
                hour = 6;
                break;
            case 1:
                hour = 7;
                break;
            case 2:
                hour = 8;
                break;
            case 3:
                hour = 9;
                break;
            case 4:
                hour = 10;
                break;
            case 5:
                hour = 11;
                break;
            case 6:
                hour = 12;
                break;
            case 7:
                hour = 13;
                break;
            case 8:
                hour = 14;
                break;

            case 9:
                hour = 15;
                break;

            case 10:
                hour = 16;
                break;

            case 11:
                hour = 17;
                break;

            case 12:
                hour = 18;
                break;

            case 13:
                hour = 19;
                break;

            case 14:
                hour = 20;
                break;

            case 15:
                hour = 21;
                break;

            case 16:
                hour = 22;
                break;

            case 17:
                hour = 23;
                break;

            case 18:
                hour = 0;
                break;

            case 19:
                hour = 1;
                break;

            case 20:
                hour = 2;
                break;

            case 21:
                hour = 3;
                break;

            case 22:
                hour = 4;
                break;

            case 23:
                hour = 5;
                break;

            default:
                hour = 0;
                break;
        }

        return hour;
    }

    //Struct to store the time in hours and minutes.
    public static class Time {
        public int hours;
        public int minutes;
    }

    // Get the time in Time and return it.
    public static Time getTime() {
        Time time = new Time();
        // Get the time in ticks.
        //The first day starts at 0 ticks
        //The second day starts at 24000 ticks
        //The third day starts at 48000 ticks
        //So divide the ticks by 24000 to get the days
        long timeTicks = mc.level.dayTime();

        int days = (int) (timeTicks / 24000);
        long currentDayTicks = timeTicks - (days * 24000);

        // Get the time in hours.
        int hours = (int) (currentDayTicks / 1000);

        // Get the time in minutes.
        int minutes = (int) ((currentDayTicks - (hours * 1000)) / 20);

        hours = convertHour(hours);

        // Convert minecraftMinuter to realworld Minuter format
        minutes = (int) Math.floor(minutes * mCraftMinuteConversion);

        time.hours = hours;
        time.minutes = minutes;
        return time;
    }

}
