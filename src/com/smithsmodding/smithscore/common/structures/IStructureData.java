package com.smithsmodding.smithscore.common.structures;

import com.smithsmodding.smithscore.common.tileentity.state.ITileEntityState;

/**
 * Created by Orion Created on 04.07.2015 20:10
 *
 * Copyrighted according to Project specific license
 */
public interface IStructureData<H extends IStructureComponent> extends ITileEntityState {

    Object getData (IStructureComponent pRequestingComponent, String pPropertyType);

    void setData (IStructureComponent pSendingComponent, String pPropertyType, Object pData);

    void onDataMergeInto(IStructureData<H> data);
}
