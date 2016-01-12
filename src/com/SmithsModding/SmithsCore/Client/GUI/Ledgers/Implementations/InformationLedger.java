package com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Implementations;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Implementations.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Util.Client.Color.*;
import com.SmithsModding.SmithsCore.Util.Client.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import net.minecraft.client.*;

import java.util.*;

/**
 * Created by Marc on 12.01.2016.
 */
public class InformationLedger extends CoreLedger {

    ArrayList<String> translatedDisplayedStrings = new ArrayList<String>();

    public InformationLedger (String uniqueID, IGUIBasedLedgerHost root, LedgerConnectionSide side, String translatedGuiOwner, MinecraftColor color, ArrayList<String> translatedDisplayedStrings) {
        super(uniqueID, new LedgerComponentState(), root, side, Textures.Gui.Basic.INFOICON, translatedGuiOwner, color);

        for (String line : translatedDisplayedStrings) {
            Collections.addAll(this.translatedDisplayedStrings, StringUtils.SplitString(line, closedLedgerWidth + Minecraft.getMinecraft().fontRendererObj.getStringWidth(translatedGuiOwner) - 8));

            if (translatedDisplayedStrings.indexOf(line) != translatedDisplayedStrings.size() - 1)
                this.translatedDisplayedStrings.add("");
        }

        registerComponents(this);
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
        return closedLedgerHeight + 8 + ( translatedDisplayedStrings.size() * ( Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 3 ) );
    }


    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerComponents (IGUIBasedComponentHost host) {
        if (translatedDisplayedStrings == null)
            return;

        for (String line : translatedDisplayedStrings) {
            registerNewComponent(new ComponentLabel(getID() + ".line." + line, this, new CoreComponentState(null), new Coordinate2D(8, closedLedgerHeight + translatedDisplayedStrings.indexOf(line) * ( Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 3 )), new MinecraftColor(MinecraftColor.WHITE), Minecraft.getMinecraft().fontRendererObj, line));
        }
    }
}
