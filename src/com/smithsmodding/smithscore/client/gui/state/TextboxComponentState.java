package com.smithsmodding.smithscore.client.gui.state;

import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/**
 * @Author Marc (Created on: 14.06.2016)
 */
public class TextboxComponentState extends CoreComponentState {
    private final int id = 0;
    private FontRenderer fontRendererInstance = Minecraft.getMinecraft().fontRendererObj;
    private String text;

    public TextboxComponentState(IGUIComponent component) {
        super(component);
    }

    public int getId() {
        return id;
    }

    public FontRenderer getFontRendererInstance() {
        return fontRendererInstance;
    }

    public void setFontRendererInstance(FontRenderer fontRendererInstance) {
        this.fontRendererInstance = fontRendererInstance;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
