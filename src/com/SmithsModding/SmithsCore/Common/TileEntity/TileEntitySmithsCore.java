/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.TileEntity;

import com.SmithsModding.SmithsCore.Util.Common.Postioning.Coordinate3D;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySmithsCore extends TileEntity {

    public Coordinate3D getLocation() {
        return new Coordinate3D(this.pos);
    }

    public void setLocation(Coordinate3D pNewLocation) {
        pos = pNewLocation.toBlockPos();
    }
}
