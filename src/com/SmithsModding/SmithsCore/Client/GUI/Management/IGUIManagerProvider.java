package com.smithsmodding.smithscore.client.GUI.Management;

/**
 * Created by Orion
 * Created on 01.12.2015
 * 18:14
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IGUIManagerProvider
{

    /**
     * Function to get the IGUIManager.
     *
     * @return Returns the current GUIManager.
     */
    IGUIManager getManager();

    /**
     * Function to set the IGUIManager
     *
     * @param newManager THe new IGUIManager.
     */
    void setManager(IGUIManager newManager);
}
