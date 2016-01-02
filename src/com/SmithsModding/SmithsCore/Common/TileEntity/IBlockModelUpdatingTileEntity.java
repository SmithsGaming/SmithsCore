package com.SmithsModding.SmithsCore.Common.TileEntity;


/**
 * Created by Marc on 01.01.2016.
 */
public interface IBlockModelUpdatingTileEntity {

    boolean shouldUpdateBlock ();

    void onUpdateBlock ();
}
