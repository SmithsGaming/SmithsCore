package com.smithsmodding.smithscore.common.structures;

/**
 * Created by Orion Created on 04.07.2015 20:10
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IStructureData extends ITileEntityState {

    Object getData (IStructureComponent pRequestingComponent, String pPropertyType);

    void setData (IStructureComponent pSendingComponent, String pPropertyType, Object pData);
}
