package com.smithsmodding.smithscore.client.gui.legders.implementations;

import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.legders.core.*;
import com.smithsmodding.smithscore.client.gui.state.*;
import com.smithsmodding.smithscore.util.client.*;
import com.smithsmodding.smithscore.util.client.color.*;
import com.smithsmodding.smithscore.util.common.positioning.*;
import net.minecraft.client.*;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by Marc on 12.01.2016.
 */
public class InformationLedger extends CoreLedger {

    @Nonnull
    ArrayList<String> translatedDisplayedStrings = new ArrayList<String>();

    public InformationLedger (String uniqueID, IGUIBasedLedgerHost root, LedgerConnectionSide side, String translatedGuiOwner, MinecraftColor color, @Nonnull ArrayList<String> translatedDisplayedStrings) {
        super(uniqueID, new LedgerComponentState(), root, side, Textures.Gui.Basic.INFOICON, translatedGuiOwner, color);

        for (String line : translatedDisplayedStrings) {
            Collections.addAll(this.translatedDisplayedStrings, StringUtils.SplitString(line, closedLedgerWidth + Minecraft.getMinecraft().fontRendererObj.getStringWidth(translatedGuiOwner) - 15));

            if (translatedDisplayedStrings.indexOf(line) != translatedDisplayedStrings.size() - 1)
                this.translatedDisplayedStrings.add("");
        }
    }

    /**
     * Method used by the rendering and animation system to determine the max size of the Ledger.
     *
     * @return An int bigger then 16 plus the icon width that describes the maximum width of the Ledger when expanded.
     */
    @Override
    public int getMaxWidth () {
        return closedLedgerWidth + Minecraft.getMinecraft().fontRendererObj.getStringWidth(translatedLedgerHeader) + 8;
    }

    /**
     * Method used by the rendering and animation system to determine the max size of the Ledger.
     *
     * @return An int bigger then 16 plus the icon width that describes the maximum height of the Ledger when expanded.
     */
    @Override
    public int getMaxHeight () {
        return 121;
    }


    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerComponents (IGUIBasedComponentHost host) {
        super.registerComponents(host);

        registerNewComponent(new Contents(getID() + ".Contents", this, new CoreComponentState(), new Coordinate2D(8, closedLedgerHeight), closedLedgerWidth + Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.translatedLedgerHeader) - 8, 87));
    }

    public class Contents extends ComponentScrollableArea
    {
        public Contents (String uniqueID, IGUIBasedComponentHost parent, IGUIComponentState state, Coordinate2D rootAnchorPixel, int width, int height) {
            super(uniqueID, parent, state, rootAnchorPixel, width, height);
        }

        @Override
        public void registerContentComponents (@Nonnull ComponentContentArea host) {
            for (int i = 0; i < translatedDisplayedStrings.size(); i++) {
                String line = translatedDisplayedStrings.get(i);

                host.registerNewComponent(new ComponentLabel(getID() + ".line." + i, host, new CoreComponentState(null), new Coordinate2D(0, i * ( Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 3 )), new MinecraftColor(MinecraftColor.WHITE), Minecraft.getMinecraft().fontRendererObj, line));
            }
        }
    }
}
