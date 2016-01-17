package com.smithsmodding.smithscore.client.Handlers.GUI;

import net.minecraft.block.state.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

/**
 * Created by Marc on 03.01.2016.
 */
public class RenderGameOverlayEventHandler {

    @SubscribeEvent
    public void handleEvent (RenderGameOverlayEvent.Text event) {
        if (Minecraft.getMinecraft().objectMouseOver != null && Minecraft.getMinecraft().objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && Minecraft.getMinecraft().objectMouseOver.getBlockPos() != null) {
            BlockPos blockpos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
            IBlockState iblockstate = Minecraft.getMinecraft().theWorld.getBlockState(blockpos);

            if (iblockstate.getBlock() instanceof ICustomDebugInformationBlock)
                ( (ICustomDebugInformationBlock) iblockstate.getBlock() ).handleDebugInformation(event, Minecraft.getMinecraft().theWorld, blockpos);
        }
    }
}
