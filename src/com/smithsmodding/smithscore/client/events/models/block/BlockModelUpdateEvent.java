package com.smithsmodding.smithscore.client.events.models.block;

import com.smithsmodding.smithscore.common.events.network.StandardNetworkableEvent;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import com.smithsmodding.smithscore.network.event.EventNetworkManager;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Marc on 01.01.2016.
 */
public class BlockModelUpdateEvent extends StandardNetworkableEvent {

    Coordinate3D blockPosition;
    int dimensionID;

    public BlockModelUpdateEvent () {
    }

    public BlockModelUpdateEvent (@Nonnull TileEntitySmithsCore tileEntitySmithsCore) {
        blockPosition = tileEntitySmithsCore.getLocation();
        dimensionID = tileEntitySmithsCore.getWorld().provider.getDimension();
    }

    /**
     * Function used by the instance created on the receiving side to reset its state from the sending side stored by it
     * in the Buffer before it is being fired on the NetworkRelayBus.
     *
     * @param pMessageBuffer The ByteBuffer from the IMessage used to Synchronize the implementing events.
     */
    @Override
    public void readFromMessageBuffer (@Nonnull ByteBuf pMessageBuffer) {
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
    public void writeToMessageBuffer (@Nonnull ByteBuf pMessageBuffer) {
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
     * Function used by the EventHandler to retrieve an IMessage that describes this events. This IMessage is then send
     * to the server or the client depending on the running side.
     *
     * A warning: You will have to register the IMessage and its handler to the EventNetworkManager.getInstance()
     * yourself
     *
     * @param side The side this event is synced TO!
     *
     * @return An Instance of an IMessage class that describes this events.
     */
    @Nullable
    @Override
    public IMessage getCommunicationMessage (@Nonnull Side side) {
        if (side == Side.SERVER)
            return null;

        return super.getCommunicationMessage(side);
    }

    public Coordinate3D getBlockPosition () {
        return blockPosition;
    }

}
