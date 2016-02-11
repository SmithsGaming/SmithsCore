package com.smithsmodding.smithscore.client.gui.state;

/**
 * Created by Marc on 08.02.2016.
 */
public class ButtonComponentState extends CoreComponentState {

    private boolean isClicked;

    public ButtonComponentState () {
        super();

        isClicked = false;
    }

    public boolean isClicked () {
        return isClicked;
    }

    public void setClicked (boolean clicked) {
        isClicked = clicked;
    }
}
