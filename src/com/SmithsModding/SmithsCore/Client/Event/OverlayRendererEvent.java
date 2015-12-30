package com.SmithsModding.SmithsCore.Client.Event;

import com.SmithsModding.SmithsCore.Common.Event.*;
import net.minecraft.client.gui.*;
import net.minecraft.item.*;

/**
 * Created by Marc on 30.12.2015.
 */
public class OverlayRendererEvent extends SmithsCoreEvent {
    private Phase phase;
    private FontRenderer renderer;
    private ItemStack stack;
    private int xPos;
    private int yPos;
    private String text;

    public OverlayRendererEvent (FontRenderer renderer, ItemStack stack, int xPos, int yPos, String text, Phase phase) {
        this.renderer = renderer;
        this.stack = stack;
        this.xPos = xPos;
        this.yPos = yPos;
        this.text = text;
        this.phase = phase;
    }

    public static void onPreRender (FontRenderer fr, ItemStack stack, int xPosition, int yPosition, String text) {
        OverlayRendererEvent event = new OverlayRendererEvent(fr, stack, xPosition, yPosition, text, Phase.PRE);

        event.PostClient();
    }

    public static void onPostRender (FontRenderer fr, ItemStack stack, int xPosition, int yPosition, String text) {
        OverlayRendererEvent event = new OverlayRendererEvent(fr, stack, xPosition, yPosition, text, Phase.POST);

        event.PostClient();
    }

    public enum Phase {
        PRE,
        POST
    }
}
