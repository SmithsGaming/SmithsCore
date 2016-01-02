package com.SmithsModding.SmithsCore.Client.Events.Models.Block;

import com.SmithsModding.SmithsCore.Common.Events.Network.*;
import com.SmithsModding.SmithsCore.Common.TileEntity.*;
import com.SmithsModding.SmithsCore.Network.Event.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by Marc on 01.01.2016.
 */
public class BlockModelUpdateEvent extends StandardNetworkableEvent {

    Coordinate3D blockPosition;
    int dimensionID;

    public BlockModelUpdateEvent () {
    }

    public BlockModelUpdateEvent (TileEntitySmithsCore tileEntitySmithsCore) {
        blockPosition = tileEntitySmithsCore.getLocation();
        dimensionID = tileEntitySmithsCore.getWorld().provider.getDimensionId();
    }

    /**
     * Function used by the instance created on the receiving side to reset its state from the sending side stored by it
     * in the Buffer before it is being fired on the NetworkRelayBus.
     *
     * @param pMessageBuffer The ByteBuffer from the IMessage used to Synchronize the implementing Events.
     */
    @Override
    public void readFromMessageBuffer (ByteBuf pMessageBuffer) {
        blockPosition = Coordinate3D.fromBytes(pMessageBuffer);
        dimensionID = pMessageBuffer.readInt();
    }

    /**
     * Function used by the instance on the sending side to write its state top the Buffer before it is send to the
     * retrieving side.
     *
     * @param pMessageBuffer The buffer from the IMessage
     */
    @Override
    public void writeToMessageBuffer (ByteBuf pMessageBuffer) {
        blockPosition.toBytes(pMessageBuffer);
        pMessageBuffer.writeInt(dimensionID);
    }

    /**
     * Method called by the EventHandler to indicate this event that it should sent it self from teh server to the
     * client side.
     */
    @Override
    public void handleServerToClientSide () {
        EventNetworkManager.getInstance().sendToAllAround(getCommunicationMessage(Side.CLIENT), new NetworkRegistry.TargetPoint(dimensionID, blockPosition.getXComponent(), blockPosition.getYComponent(), blockPosition.getZComponent(), 128));
    }

    /**
     * Function used by the EventHandler to retrieve an IMessage that describes this Events. This IMessage is then send
     * to the server or the client depending on the running side.
     * <p/>
     * A warning: You will have to register the IMessage and its handler to the EventNetworkManager.getInstance()
     * yourself
     *
     * @param side The side this event is synced TO!
     *
     * @return An Instance of an IMessage class that describes this Events.
     */
    @Override
    public IMessage getCommunicationMessage (Side side) {
        if (side == Side.SERVER)
            return null;

        return super.getCommunicationMessage(side);
    }

    public Coordinate3D getBlockPosition () {
        return blockPosition;
    }

}
