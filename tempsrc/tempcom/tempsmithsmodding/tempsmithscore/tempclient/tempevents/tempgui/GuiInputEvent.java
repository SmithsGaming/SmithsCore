package com.smithsmodding.smithscore.client.events.gui;

import com.smithsmodding.smithscore.common.events.network.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.network.*;

/**
 * Created by Marc on 26.01.2016.
 */
@Cancelable
public class GuiInputEvent extends StandardNetworkableEvent {
    InputTypes type;

    String componentID;
    String input;

    public GuiInputEvent (InputTypes types, String componentID, String input) {
        this.type = types;
        this.componentID = componentID;
        this.input = input;
    }

    public GuiInputEvent () {
    }

    public InputTypes getTypes () {
        return type;
    }

    public String getComponentID () {
        return componentID;
    }

    public String getInput () {
        return input;
    }

    /**
     * Function used by the instance created on the receiving side to reset its state from the sending side stored by it
     * in the Buffer before it is being fired on the NetworkRelayBus.
     *
     * @param pMessageBuffer The ByteBuffer from the IMessage used to Synchronize the implementing events.
     */
    @Override
    public void readFromMessageBuffer (ByteBuf pMessageBuffer) {
        type = InputTypes.values()[pMessageBuffer.readInt()];
        componentID = ByteBufUtils.readUTF8String(pMessageBuffer);
        input = ByteBufUtils.readUTF8String(pMessageBuffer);
    }

    /**
     * Function used by the instance on the sending side to write its state top the Buffer before it is send to the
     * retrieving side.
     *
     * @param pMessageBuffer The buffer from the IMessage
     */
    @Override
    public void writeToMessageBuffer (ByteBuf pMessageBuffer) {
        pMessageBuffer.writeInt(type.ordinal());
        ByteBufUtils.writeUTF8String(pMessageBuffer, componentID);
        ByteBufUtils.writeUTF8String(pMessageBuffer, input);
    }

    public enum InputTypes {
        TABCHANGED,
        BUTTONCLICKED,
        SCROLLED,
        FLUIDPRIORITIZED,
        TEXTCHANGED,
    }
}
