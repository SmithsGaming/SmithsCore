package com.smithsmodding.smithscore.client.gui.state;

import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentSlot;
import com.smithsmodding.smithscore.common.inventory.IItemStorage;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 22.12.2015.
 */
public class SlotComponentState extends CoreComponentState {

    private int slotIndex;
    private IInventory relevantInventory;
    private TextureAtlasSprite sprite;

    public SlotComponentState(@Nonnull ComponentSlot component, int slotIndex, @Nonnull IItemStorage inventory, TextureAtlasSprite sprite) {
        this(component, slotIndex, new IItemStorage.IInventoryWrapper(inventory), sprite);
    }

    public SlotComponentState(@Nonnull ComponentSlot component, @Nonnull Slot slot, @Nonnull IItemStorage inventory, TextureAtlasSprite sprite) {
        this(component, slot.getSlotIndex(), inventory, sprite);
    }

    public SlotComponentState (@Nonnull ComponentSlot component, int slotIndex, IInventory inventory, TextureAtlasSprite sprite) {
        super(component);

        this.slotIndex = slotIndex;
        this.relevantInventory = inventory;
        this.sprite = sprite;
    }

    public SlotComponentState(@Nonnull ComponentSlot component, @Nonnull Slot slot, IInventory inventory, TextureAtlasSprite sprite) {
        this(component, slot.getSlotIndex(), inventory, sprite);
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
