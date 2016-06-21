package com.smithsmodding.smithscore.compatibility;

import com.google.common.collect.ImmutableList;
import com.smithsmodding.smithscore.client.gui.GuiContainerSmithsCore;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedLedgerHost;
import com.smithsmodding.smithscore.client.gui.legders.core.IGUILedger;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.IAdvancedGuiHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Author Orion (Created on: 21.06.2016)
 */
@JEIPlugin
public class JEIGuiCompat implements IModPlugin {

    @Override
    public void register(@Nonnull IModRegistry registry) {
        registry.addAdvancedGuiHandlers(new IAdvancedGuiHandler<GuiContainerSmithsCore>() {
            @Nonnull
            @Override
            public Class<GuiContainerSmithsCore> getGuiContainerClass() {
                return GuiContainerSmithsCore.class;
            }

            @Nullable
            @Override
            public List<Rectangle> getGuiExtraAreas(@Nonnull GuiContainerSmithsCore guiContainer) {
                return ImmutableList.copyOf(getComponentAreas(guiContainer).values());
            }
        });
    }

    public HashMap<String, Rectangle> getComponentAreas(IGUIBasedComponentHost host) {
        LinkedHashMap<String, Rectangle> components = new LinkedHashMap<>();

        if (host instanceof IGUIBasedLedgerHost) {
            for (IGUILedger ledger : ((IGUIBasedLedgerHost) host).getLedgerManager().getLeftLedgers().values()) {
                components.put(ledger.getID(), ledger.getAreaOccupiedByComponent().toRectangle());
            }

            for (IGUILedger ledger : ((IGUIBasedLedgerHost) host).getLedgerManager().getRightLedgers().values()) {
                components.put(ledger.getID(), ledger.getAreaOccupiedByComponent().toRectangle());
            }
        }

        for (IGUIComponent component : host.getAllComponents().values()) {
            components.put(component.getID(), component.getAreaOccupiedByComponent().toRectangle());
        }

        return components;
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
    }
}