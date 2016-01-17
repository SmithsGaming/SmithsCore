package com.smithsmodding.smithscore.client.GUI.Components.Implementations;

import com.smithsmodding.smithscore.client.GUI.Host.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.GUI.State.IGUIComponentState;
import com.smithsmodding.smithscore.util.Client.GUI.GuiHelper;
import com.smithsmodding.smithscore.util.Common.Postioning.Coordinate2D;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.util.ArrayList;

/**
 * Created by marcf on 1/17/2016.
 */
public class ComponentItemStackDisplay extends CoreComponent {

    ItemStack stack;

    public ComponentItemStackDisplay(String uniqueID, IGUIBasedComponentHost parent, IGUIComponentState state, Coordinate2D rootAnchorPixel, ItemStack stack) {
        super(uniqueID, parent, state, rootAnchorPixel, 16, 16);

        this.stack = stack;
    }

    @Override
    public void update(int mouseX, int mouseY, float partialTickTime) {
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {
        GuiHelper.drawItemStack(stack, 0,0);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
    }

    @Override
    public ArrayList<String> getToolTipContent() {
        return new ArrayList<String>(stack.getTooltip(FMLClientHandler.instance().getClientPlayerEntity(), Minecraft.getMinecraft().gameSettings.advancedItemTooltips));
    }

}
