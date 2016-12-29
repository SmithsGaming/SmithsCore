package com.smithsmodding.smithscore.client.gui.components.implementations;

import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.state.*;
import com.smithsmodding.smithscore.util.client.gui.*;
import com.smithsmodding.smithscore.util.common.positioning.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.client.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by marcf on 1/17/2016.
 */
public class ComponentItemStackDisplay extends CoreComponent {

    ItemStack stack;

    public ComponentItemStackDisplay(@Nonnull String uniqueID, @Nonnull IGUIBasedComponentHost parent, @Nonnull IGUIComponentState state, @Nonnull Coordinate2D rootAnchorPixel, @Nonnull ItemStack stack) {
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

    @Nullable
    @Override
    public ArrayList<String> getToolTipContent() {
        return new ArrayList<String>(stack.getTooltip(FMLClientHandler.instance().getClientPlayerEntity(), Minecraft.getMinecraft().gameSettings.advancedItemTooltips));
    }

}
