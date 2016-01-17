package com.smithsmodding.smithscore.client.GUI.Scissoring;

/**
 * Created by Marc on 10.01.2016.
 */
public interface IScissoredGuiComponent extends IGUIBasedComponentHost {

    boolean shouldScissor ();

    Plane getGlobalScissorLocation ();
}

