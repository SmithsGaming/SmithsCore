package com.SmithsModding.SmithsCore.Client.GUI.Host;

import com.SmithsModding.SmithsCore.Client.GUI.Management.IGUIManager;
import com.SmithsModding.SmithsCore.Client.GUI.Management.IGUIManagerProvider;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.Coordinate2D;

/**
 * Created by Orion
 * Created on 01.12.2015
 * 18:13
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IGUIBasedComponentHost extends IGUIManagerProvider
{

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    void registerComponents(IGUIBasedComponentHost host);
}
