package com.smithsmodding.smithscore.client.gui.state;

import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.inventory.*;

/**
 * Created by Marc on 22.12.2015.
 */
public class SlotComponentState extends CoreComponentState {

    private int slotIndex;
    private IInventory relevantInventory;
    private TextureAtlasSprite sprite;

    public SlotComponentState (ComponentSlot component, int slotIndex, IInventory inventory, TextureAtlasSprite sprite) {
        super(component);

        this.slotIndex = slotIndex;
        this.relevantInventory = inventory;
        this.sprite = sprite;
    }

    /**
     * Method used to determine if the slot requires holographic rendering.
     *
     * @return True when the SlotComponent should render the holo sprite.
     */
    public boolean requiresHoloRendering () {
        return relevantInventory.getStackInSlot(slotIndex) == null;
    }

    /**
     * Returns the sprite used to holographic render.
     *
     * @return Null when no sprite should be rendered, or a sprite that should be rendered when requiresHoloRendering()
     * is true.
     */
    public TextureAtlasSprite getHolographicSprite () {
        return sprite;
    }
}
