package com.smithsmodding.smithscore.client.gui.management;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.client.gui.GuiContainerSmithsCore;
import com.smithsmodding.smithscore.client.gui.animation.IAnimatibleGuiComponent;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedLedgerHost;
import com.smithsmodding.smithscore.client.gui.legders.core.IGUILedger;
import com.smithsmodding.smithscore.client.gui.scissoring.IScissoredGuiComponent;
import com.smithsmodding.smithscore.client.gui.state.IGUIComponentState;
import com.smithsmodding.smithscore.client.registry.ClientRegistry;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import com.smithsmodding.smithscore.util.common.positioning.Plane;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * Created by marcf on 12/3/2015.
 */
public class StandardRenderManager implements IRenderManager {

    @Nonnull
    private static ArrayList<MinecraftColor> colorStack = new ArrayList<MinecraftColor>();

    GuiScreen root;
    @Nonnull
    StandardScissorRegionManager scissorer = new StandardScissorRegionManager();

    public StandardRenderManager(GuiScreen root)
    {
        if (!(root instanceof IGUIBasedComponentHost ))
            throw new IllegalArgumentException("The given Root for this manager is not a IGUIBasedComponentHost!");

        this.root = root;
    }

    /**
     * Method should be used when rendering UI components. It is used to set the color to a new one and remember the old
     * one.
     *
     * Allows for the Enabled color to be put on the known stack, or any other color set by a component.
     *
     * @param color The color to push on.
     */
    public static void pushColorOnRenderStack (@Nonnull MinecraftColor color) {
        colorStack.add(0, color);
        color.performOpenGLColoring();
    }

    /**
     * Method should be used when rendering UI components. It is used to reset the color to the previous state instead
     * of to white.
     *
     * Allows for the Enabled color to be put on the known stack, or any other color set by a component.
     */
    public static void popColorFromRenderStack () {
        if (colorStack.size() == 0) {
            SmithsCore.getLogger().error("The color Stack is empty!");
            return;
        }

        colorStack.remove(0);

        if (colorStack.size() == 0) {
            new MinecraftColor(MinecraftColor.WHITE).performOpenGLColoring();
            return;
        }

        colorStack.get(0).performOpenGLColoring();
    }

    /**
     * Method to get the Gui this StandardRenderManager renders on.
     *
     * @return The current active gui
     */
    @Override
    public GuiScreen getRootGuiObject() {
        return root;
    }

    /**
     * Methd to get the root GuiManager this StandardRenderManager belongs to.
     *
     * @return The GuiManager of the Root gui object.
     */
    @Override
    public IGUIManager getRootGuiManager() {
        return ((IGUIBasedComponentHost) root).getRootManager();
    }

    /**
     * Method to get this RenderManagers ScissorRegionManager.
     *
     * @return This RenderManagers ScissorRegionManager.
     */
    @Nonnull
    @Override
    public IScissorRegionManager getScissorRegionManager () {
        return scissorer;
    }

    /**
     * Method to render the BackGround of a Component
     *
     * @param component The Component to render.
     */
    @Override
    public void renderBackgroundComponent (@Nonnull IGUIComponent component, boolean parentEnabled) {
        ClientRegistry registry = (ClientRegistry) SmithsCore.getRegistry();

        IGUIComponentState state = component.getState();

        if (!state.isVisible())
            return;

        component.update(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent(), registry.getPartialTickTime());

        GlStateManager.pushMatrix();

        GlStateManager.translate(component.getLocalCoordinate().getXComponent(), component.getLocalCoordinate().getYComponent(), 0F);

        if (component instanceof IAnimatibleGuiComponent)
            ( (IAnimatibleGuiComponent) component ).performAnimation(registry.getPartialTickTime());

        if (component instanceof IGUIBasedLedgerHost) {
            IGUIBasedLedgerHost ledgerHost = (IGUIBasedLedgerHost) component;

            for (IGUILedger ledger : ledgerHost.getLedgerManager().getLeftLedgers().values()) {
                this.renderBackgroundComponent(ledger, false);
            }

            for (IGUILedger ledger : ledgerHost.getLedgerManager().getRightLedgers().values()) {
                this.renderBackgroundComponent(ledger, false);
            }
        }

        if (!state.isEnabled()) {
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            pushColorOnRenderStack(new MinecraftColor(MinecraftColor.darkGray));
        }

        if (!( component instanceof GuiContainerSmithsCore ))
            component.drawBackground(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent());

        if (!state.isEnabled()) {
            popColorFromRenderStack();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
        }

        if (component instanceof IScissoredGuiComponent && ( (IScissoredGuiComponent) component ).shouldScissor())
            if (!scissorer.setScissorRegionTo(((IScissoredGuiComponent) component).getGlobalScissorLocation()))
            {
                GlStateManager.popMatrix();
                return;
            }

        if (component instanceof IGUIBasedComponentHost) {
            for (IGUIComponent subComponent : ( (IGUIBasedComponentHost) component ).getAllComponents().values()) {
                this.renderBackgroundComponent(subComponent, state.isEnabled());
            }
        }

        if (component instanceof IScissoredGuiComponent && ( (IScissoredGuiComponent) component ).shouldScissor())
            scissorer.popCurrentScissorRegion();

        GlStateManager.popMatrix();




/*        ClientRegistry registry = (ClientRegistry) SmithsCore.getRegistry();

        IGUIComponentState state = component.getState();

        if (!state.isVisible())
            return;

        component.update(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent(), registry.getPartialTickTime());

        GlStateManager.pushMatrix();

        GlStateManager.translate(component.getLocalCoordinate().getXComponent(), component.getLocalCoordinate().getYComponent(), 0F);

        if (component instanceof IAnimatibleGuiComponent)
            ( (IAnimatibleGuiComponent) component ).performAnimation(registry.getPartialTickTime());

        if (component instanceof IGUIBasedLedgerHost) {
            IGUIBasedLedgerHost ledgerHost = (IGUIBasedLedgerHost) component;

            for (IGUILedger ledger : ledgerHost.getLedgerManager().getLeftLedgers().values()) {
                this.renderBackgroundComponent(ledger, false);
            }

            for (IGUILedger ledger : ledgerHost.getLedgerManager().getRightLedgers().values()) {
                this.renderBackgroundComponent(ledger, false);
            }
        }

        if (!state.isEnabled()) {
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            pushColorOnRenderStack(new MinecraftColor(MinecraftColor.darkGray));
        }

        if (!( component instanceof GuiContainerSmithsCore ))
            component.drawBackground(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent());

        if (!state.isEnabled()) {
            popColorFromRenderStack();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
        }

        if (component instanceof IScissoredGuiComponent && ( (IScissoredGuiComponent) component ).shouldScissor())
            GuiHelper.enableScissor(( (IScissoredGuiComponent) component ).getGlobalScissorLocation());

        if (component instanceof IGUIBasedComponentHost) {
            for (IGUIComponent subComponent : ( (IGUIBasedComponentHost) component ).getAllComponents().values()) {
                if (component instanceof IScissoredGuiComponent && ( (IScissoredGuiComponent) component ).shouldScissor() && subComponent instanceof IScissoredGuiComponent && ( (IScissoredGuiComponent) subComponent ).shouldScissor())
                    GuiHelper.disableScissor();

                this.renderBackgroundComponent(subComponent, state.isEnabled());

                if (component instanceof IScissoredGuiComponent && ( (IScissoredGuiComponent) component ).shouldScissor() && subComponent instanceof IScissoredGuiComponent && ( (IScissoredGuiComponent) subComponent ).shouldScissor())
                    GuiHelper.enableScissor(( (IScissoredGuiComponent) component ).getGlobalScissorLocation());
            }
        }

        if (component instanceof IScissoredGuiComponent && ( (IScissoredGuiComponent) component ).shouldScissor())
            GuiHelper.disableScissor();

        GlStateManager.popMatrix();*/
    }

    /**
     * Method to render the ForeGround of a Component
     *
     * @param component The Component to render
     */
    @Override
    public void renderForegroundComponent (@Nonnull IGUIComponent component, boolean parentEnabled) {
        ClientRegistry registry = (ClientRegistry) SmithsCore.getRegistry();

        IGUIComponentState state = component.getState();

        if (!state.isVisible())
            return;

        component.update(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent(), registry.getPartialTickTime());

        GlStateManager.pushMatrix();

        GlStateManager.translate(component.getLocalCoordinate().getXComponent(), component.getLocalCoordinate().getYComponent(), 0F);

        if (component instanceof IGUIBasedLedgerHost) {
            IGUIBasedLedgerHost ledgerHost = (IGUIBasedLedgerHost) component;

            for (IGUILedger ledger : ledgerHost.getLedgerManager().getLeftLedgers().values()) {
                this.renderForegroundComponent(ledger, false);
            }

            for (IGUILedger ledger : ledgerHost.getLedgerManager().getRightLedgers().values()) {
                this.renderForegroundComponent(ledger, false);
            }
        }

        if (!state.isEnabled()) {
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            pushColorOnRenderStack(new MinecraftColor(MinecraftColor.darkGray));
        }

        if (!(component instanceof GuiContainerSmithsCore))
            component.drawForeground(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent());

        if (!state.isEnabled()) {
            popColorFromRenderStack();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
        }

        if (component instanceof IScissoredGuiComponent && ((IScissoredGuiComponent) component).shouldScissor())
            if (!scissorer.setScissorRegionTo(((IScissoredGuiComponent) component).getGlobalScissorLocation())) {
                GlStateManager.popMatrix();
                return;
            }

        if (component instanceof IGUIBasedComponentHost) {
            for (IGUIComponent subComponent : ((IGUIBasedComponentHost) component).getAllComponents().values()) {
                this.renderForegroundComponent(subComponent, state.isEnabled());
            }
        }

        if (component instanceof IScissoredGuiComponent && ((IScissoredGuiComponent) component).shouldScissor())
            scissorer.popCurrentScissorRegion();

        GlStateManager.popMatrix();
    }

    /**
     * Method to render the ToolTip of the Component
     *
     * @param component The Component to render the tooltip from.
     */
    @Override
    public void renderToolTipComponent (@Nonnull IGUIComponent component, int mouseX, int mouseY) {
        Plane localPlane = new Plane(0,0, component.getSize().getWidth(), component.getSize().getHeigth());

        if ((component.getToolTipContent() != null && component.getToolTipContent().size() != 0) && (!(component instanceof IGUIBasedComponentHost)) && !localPlane.ContainsCoordinate(mouseX, mouseY))
            return;

        if (component.getToolTipContent() == null || component.getToolTipContent().size() == 0) {
            if (component instanceof IGUIBasedComponentHost) {
                for (IGUIComponent component1 : ( (IGUIBasedComponentHost) component ).getAllComponents().values()) {
                    this.renderToolTipComponent(component1, mouseX - component1.getLocalCoordinate().getXComponent(), mouseY - component1.getLocalCoordinate().getYComponent());
                }
            }

            if (component instanceof IGUIBasedLedgerHost) {
                for (IGUIComponent component1 : ( (IGUIBasedLedgerHost) component ).getLedgerManager().getLeftLedgers().values()) {
                    this.renderToolTipComponent(component1, mouseX - component1.getLocalCoordinate().getXComponent(), mouseY - component1.getLocalCoordinate().getYComponent());
                }

                for (IGUIComponent component1 : ( (IGUIBasedLedgerHost) component ).getLedgerManager().getRightLedgers().values()) {
                    this.renderToolTipComponent(component1, mouseX - component1.getLocalCoordinate().getXComponent(), mouseY - component1.getLocalCoordinate().getYComponent());
                }
            }

            return;
        }

        int globalMouseX = component.getGlobalCoordinate().getXComponent() + mouseX;
        int globalMouseY = component.getGlobalCoordinate().getYComponent() + mouseY;

        ((IGUIBasedComponentHost) root).drawHoveringText(component.getToolTipContent(), globalMouseX + 4, globalMouseY + 4, Minecraft.getMinecraft().fontRendererObj);
    }
}
