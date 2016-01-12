package com.SmithsModding.SmithsCore.Client.GUI.Scissoring;

import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;

/**
 * Created by Marc on 10.01.2016.
 */
public interface IScissoredGuiComponent extends IGUIBasedComponentHost {

    boolean shouldScissor ();

    Plane getGlobalScissorLocation ();
}

