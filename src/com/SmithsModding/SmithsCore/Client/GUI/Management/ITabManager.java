package com.SmithsModding.SmithsCore.Client.GUI.Management;

import com.SmithsModding.SmithsCore.Client.GUI.Host.IGUIBasedTabHost;
import com.SmithsModding.SmithsCore.Client.GUI.Tabs.Core.IGUITab;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by marcf on 1/15/2016.
 */
public interface ITabManager
{

    IGUIBasedTabHost getHost();

    IGUITab getCurrentTab();

    LinkedHashMap<String, IGUITab> getTabs();


}
