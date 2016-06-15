package com.smithsmodding.smithscore.client.gui.management;

/**
 * Created by Orion
 * Created on 01.12.2015
 * 18:14
 *
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
