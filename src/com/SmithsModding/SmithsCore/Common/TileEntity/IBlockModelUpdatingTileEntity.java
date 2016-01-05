package com.SmithsModding.SmithsCore.Common.TileEntity;


/**
 * Created by Marc on 01.01.2016.
 */
public interface IBlockModelUpdatingTileEntity {

    void queBlockModelUpdateOnClients ();

    boolean shouldUpdateBlock ();

    void onUpdateBlock ();
}
