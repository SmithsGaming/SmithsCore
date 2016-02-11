package com.smithsmodding.smithscore.client.gui.components.implementations;

import com.smithsmodding.smithscore.*;
import com.smithsmodding.smithscore.client.events.gui.*;
import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.management.*;
import com.smithsmodding.smithscore.client.gui.state.*;
import com.smithsmodding.smithscore.client.registry.*;
import com.smithsmodding.smithscore.util.client.*;
import com.smithsmodding.smithscore.util.client.color.*;
import com.smithsmodding.smithscore.util.client.gui.*;
import com.smithsmodding.smithscore.util.common.positioning.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;

/**
 * Created by Marc on 08.02.2016.
 */
public class ComponentButton extends CoreComponent{

    Object contents;

    boolean triggeredLastUpdate;
    boolean continuesTriggering;

    public ComponentButton (String uniqueID, IGUIBasedComponentHost parent, Coordinate2D rootAnchorPixel, int width, int height, boolean continuesTriggering, Object contents) {
        super(uniqueID, parent, new ButtonComponentState(), rootAnchorPixel, width, height);

        this.contents = contents;
        this.continuesTriggering = continuesTriggering;

        if (!(contents instanceof  String) || !(contents instanceof  CustomResource) || !(contents instanceof ItemStack))
            throw new IllegalArgumentException("The given Contents are not Supported, either pass a String for text, a CustomResource for Icons, or an ItemStack for stacks.");
    }

    @Override
    public void update (int mouseX, int mouseY, float partialTickTime) {
        if (((ButtonComponentState) getState()).isClicked())
        {
            if (org.lwjgl.input.Mouse.isButtonDown(0))
            {
                ((ButtonComponentState) getState()).setClicked(true);
            }
            else
            {
                ((ButtonComponentState) getState()).setClicked(false);
                triggeredLastUpdate = false;
            }
        }

        if (((ButtonComponentState) getState()).isClicked() && triggeredLastUpdate && continuesTriggering)
        {
            SmithsCore.getRegistry().getClientBus().post(new GuiInputEvent(GuiInputEvent.InputTypes.BUTTONCLICKED, getID(), "Left-ReTrigger"));
        }

        if (((ButtonComponentState) getState()).isClicked() && !triggeredLastUpdate)
        {
            SmithsCore.getRegistry().getClientBus().post(new GuiInputEvent(GuiInputEvent.InputTypes.BUTTONCLICKED, getID(), "Left"));
            triggeredLastUpdate = true;
        }
    }

    @Override
    public void drawBackground (int mouseX, int mouseY) {
        if (((ButtonComponentState) getState()).isClicked()) {
            GuiHelper.drawRectangleStretched(Textures.Gui.Basic.Components.Button.Clicked.TEXTURE, width, height, new Coordinate2D(0,0));
        }
        else if (!getState().isEnabled()){
            StandardRenderManager.popColorFromRenderStack();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();

            GuiHelper.drawRectangleStretched(Textures.Gui.Basic.Components.Button.Disabled.TEXTURE, width, height, new Coordinate2D(0, 0));

            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            StandardRenderManager.pushColorOnRenderStack(new MinecraftColor(MinecraftColor.darkGray));
        }
        else {
            GuiHelper.drawRectangleStretched(Textures.Gui.Basic.Components.Button.Standard.TEXTURE, width, height, new Coordinate2D(0,0));
        }

        drawContents();
    }

    private void drawContents()
    {
        if (contents instanceof CustomResource)
        {
            int contentX = (this.width - ((CustomResource) contents).getWidth()) / 2;
            int contentY = (this.height - ((CustomResource) contents).getHeight()) / 2;

            GuiHelper.drawResource((CustomResource) contents, contentX, contentY);
        }
        else if(contents instanceof String)
        {
            int contentX = (this.width - Minecraft.getMinecraft().fontRendererObj.getStringWidth((String) contents)) / 2;
            int contentY = (this.height - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT) / 2;

            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow((String) contents, contentX, contentY, (new MinecraftColor(MinecraftColor.WHITE)).getRGB());
        }
        else if(contents instanceof ItemStack)
        {
            int contentX = (this.width - 16) / 2;
            int contentY = (this.height - 16) / 2;

            GuiHelper.drawItemStack((ItemStack) contents, contentX, contentY);
        }
    }

    @Override
    public boolean handleMouseClickedInside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        if (!state.isEnabled() || !state.isVisible() || mouseButton == 0)
            return false;

        ((ButtonComponentState) getState()).setClicked(true);

        return true;
    }

    @Override
    public void drawForeground (int mouseX, int mouseY) {

    }
}
