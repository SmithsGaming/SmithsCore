package com.SmithsModding.SmithsCore.Util.Client;

import com.SmithsModding.SmithsCore.Util.Client.Color.*;
import com.SmithsModding.SmithsCore.Util.Client.GUI.*;

/**
 * Created by Marc on 06.12.2015.
 */
public class Textures {

    public static class Gui {
        private static String GUITEXTUREPATH = "armory:textures/gui/";
        private static String COMPONENTTEXTUREPATH = GUITEXTUREPATH + "Components/";

        public static class Basic {
            public static CustomResource INFOICON = new CustomResource("Gui.Basic.Ledgers.InfoIon", "armory:Gui-Icons/16x Info icon", Colors.DEFAULT);
            private static String BASICTEXTUREPATH = GUITEXTUREPATH + "Basic/";
            public static CustomResource LEDGERLEFT = new CustomResource("Gui.Basic.Ledgers.Left", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT);

            public static class Slots {
                public static CustomResource DEFAULT = new CustomResource("Gui.Basic.Slots.Default", BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 0, 0, 18, 18);
            }

            public static class Border {
                public static CustomResource CENTER = new CustomResource("Gui.Basic.Border.Center", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT, 4, 4, 248, 248);
                public static CustomResource STRAIGHTBORDERLIGHT = new CustomResource("Gui.Basic.Border.Border.Ligth", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT, 3, 0, 250, 3);
                public static CustomResource STRAIGHTBORDERDARK = new CustomResource("Gui.Basic.Border.Border.Dark", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT, 3, 253, 250, 3);
                public static CustomResource INWARTSCORNERLIGHT = new CustomResource("Gui.Basic.Border.Corner.Inwarts.Ligth", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT, 0, 0, 3, 3);
                public static CustomResource INWARTSCORNERDARK = new CustomResource("Gui.Basic.Border.Corner.Inwarts.Dark", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT, 252, 252, 4, 4);
                private static String BORDERTEXTUREPATH = BASICTEXTUREPATH + "Border/";
                public static CustomResource OUTWARTSCORNER = new CustomResource("Gui.Basic.Border.Corner.Outwarts", BORDERTEXTUREPATH + "OutwartsCornerBig.png", Colors.DEFAULT, 0, 0, 3, 3);
            }

            public static class Components {
                public static CustomResource ARROWEMPTY = new CustomResource("Gui.Basic.Components.Arrow.Empty", COMPONENTTEXTUREPATH + "ProgressBars.png", Colors.DEFAULT, 0, 0, 22, 16);
                public static CustomResource ARROWFULL = new CustomResource("Gui.Basic.Components.Arrow.Full", COMPONENTTEXTUREPATH + "ProgressBars.png", Colors.DEFAULT, 22, 0, 22, 16);
                public static CustomResource FLAMEEMPTY = new CustomResource("Gui.Basic.Components.Flame.Empty", COMPONENTTEXTUREPATH + "ProgressBars.png", Colors.DEFAULT, 44, 0, 16, 16);
                public static CustomResource FLAMEFULL = new CustomResource("Gui.Basic.Components.Flame.Full", COMPONENTTEXTUREPATH + "ProgressBars.png", Colors.DEFAULT, 60, 0, 16, 16);

                public static class Button {
                    public static final CustomResource DOWNARROW = new CustomResource("Gui.Basic.Components.Button.DownArrow", COMPONENTTEXTUREPATH + "Buttons.png", Colors.DEFAULT, 0, 0, 5, 7);
                    public static final CustomResource UPARROW = new CustomResource("Gui.Basic.Components.Button.UpArrow", COMPONENTTEXTUREPATH + "Buttons.png", Colors.DEFAULT, 5, 0, 5, 7);
                    public static final CustomResource SCROLLBAR = new CustomResource("Gui.Basic.Components.Button.ScrollButton", COMPONENTTEXTUREPATH + "Buttons.png", Colors.DEFAULT, 10, 0, 5, 7);
                    protected static String WIDGETFILEPATH = BASICTEXTUREPATH + "buttons.png";

                    public static class Disabled {
                        public static CustomResource CORNERLEFTTOP = new CustomResource("Gui.Basic.Components.Button.Disabled.Corners.LeftTop", WIDGETFILEPATH, 1, 61, 1, 1);
                        public static CustomResource CORNERRIGHTTOP = new CustomResource("Gui.Basic.Components.Button.Disabled.Corners.RightTop", WIDGETFILEPATH, 198, 61, 1, 1);
                        public static CustomResource CORNERLEFTBOTTOM = new CustomResource("Gui.Basic.Components.Button.Disabled.Corners.LeftBottom", WIDGETFILEPATH, 1, 77, 1, 2);
                        public static CustomResource CORNERRIGHTBOTTOM = new CustomResource("Gui.Basic.Components.Button.Disabled.Corners.RightBottom", WIDGETFILEPATH, 198, 77, 1, 2);

                        public static CustomResource SIDETOP = new CustomResource("Gui.Basic.Components.Button.Disabled.Side.Top", WIDGETFILEPATH, 1, 61, 196, 1);
                        public static CustomResource SIDELEFT = new CustomResource("Gui.Basic.Components.Button.Disabled.Side.Left", WIDGETFILEPATH, 1, 62, 1, 15);
                        public static CustomResource SIDERIGHT = new CustomResource("Gui.Basic.Components.Button.Disabled.Side.Right", WIDGETFILEPATH, 198, 62, 1, 15);
                        public static CustomResource SIDEBOTTOM = new CustomResource("Gui.Basic.Components.Button.Disabled.Side.Bottom", WIDGETFILEPATH, 1, 77, 196, 2);

                        public static CustomResource CENTER = new CustomResource("Gui.Basic.Components.Button.Disabled.Center", WIDGETFILEPATH, 2, 62, 196, 15);

                        public static MultiComponentTexture TEXTURE = new MultiComponentTexture(new TextureComponent(CENTER), new TextureComponent[]{new TextureComponent(CORNERLEFTTOP), new TextureComponent(CORNERRIGHTTOP), new TextureComponent(CORNERRIGHTBOTTOM), new TextureComponent(CORNERLEFTBOTTOM)}, new TextureComponent[]{new TextureComponent(SIDETOP), new TextureComponent(SIDERIGHT), new TextureComponent(SIDEBOTTOM), new TextureComponent(SIDELEFT)});
                    }

                    public static class Standard {
                        public static CustomResource CORNERLEFTTOP = new CustomResource("Gui.Basic.Components.Button.Standard.Corners.LeftTop", WIDGETFILEPATH, 1, 21, 1, 1);
                        public static CustomResource CORNERRIGHTTOP = new CustomResource("Gui.Basic.Components.Button.Standard.Corners.RightTop", WIDGETFILEPATH, 198, 21, 1, 1);
                        public static CustomResource CORNERLEFTBOTTOM = new CustomResource("Gui.Basic.Components.Button.Standard.Corners.LeftBottom", WIDGETFILEPATH, 1, 37, 1, 2);
                        public static CustomResource CORNERRIGHTBOTTOM = new CustomResource("Gui.Basic.Components.Button.Standard.Corners.RightBottom", WIDGETFILEPATH, 198, 37, 1, 2);

                        public static CustomResource SIDETOP = new CustomResource("Gui.Basic.Components.Button.Standard.Side.Top", WIDGETFILEPATH, 1, 21, 196, 1);
                        public static CustomResource SIDELEFT = new CustomResource("Gui.Basic.Components.Button.Standard.Side.Left", WIDGETFILEPATH, 1, 22, 1, 15);
                        public static CustomResource SIDERIGHT = new CustomResource("Gui.Basic.Components.Button.Standard.Side.Right", WIDGETFILEPATH, 198, 22, 1, 15);
                        public static CustomResource SIDEBOTTOM = new CustomResource("Gui.Basic.Components.Button.Standard.Side.Bottom", WIDGETFILEPATH, 1, 37, 196, 2);

                        public static CustomResource CENTER = new CustomResource("Gui.Basic.Components.Button.Standard.Center", WIDGETFILEPATH, 2, 22, 196, 15);

                        public static MultiComponentTexture TEXTURE = new MultiComponentTexture(new TextureComponent(CENTER), new TextureComponent[]{new TextureComponent(CORNERLEFTTOP), new TextureComponent(CORNERRIGHTTOP), new TextureComponent(CORNERRIGHTBOTTOM), new TextureComponent(CORNERLEFTBOTTOM)}, new TextureComponent[]{new TextureComponent(SIDETOP), new TextureComponent(SIDERIGHT), new TextureComponent(SIDEBOTTOM), new TextureComponent(SIDELEFT)});
                    }

                    public static class Clicked {
                        public static CustomResource CORNERLEFTTOP = new CustomResource("Gui.Basic.Components.Button.Clicked.Corners.LeftTop", WIDGETFILEPATH, 1, 41, 1, 1);
                        public static CustomResource CORNERRIGHTTOP = new CustomResource("Gui.Basic.Components.Button.Clicked.Corners.RightTop", WIDGETFILEPATH, 198, 41, 1, 1);
                        public static CustomResource CORNERLEFTBOTTOM = new CustomResource("Gui.Basic.Components.Button.Clicked.Corners.LeftBottom", WIDGETFILEPATH, 1, 57, 1, 2);
                        public static CustomResource CORNERRIGHTBOTTOM = new CustomResource("Gui.Basic.Components.Button.Clicked.Corners.RightBottom", WIDGETFILEPATH, 198, 57, 1, 2);

                        public static CustomResource SIDETOP = new CustomResource("Gui.Basic.Components.Button.Clicked.Side.Top", WIDGETFILEPATH, 1, 41, 196, 1);
                        public static CustomResource SIDELEFT = new CustomResource("Gui.Basic.Components.Button.Clicked.Side.Left", WIDGETFILEPATH, 1, 42, 1, 15);
                        public static CustomResource SIDERIGHT = new CustomResource("Gui.Basic.Components.Button.Clicked.Side.Right", WIDGETFILEPATH, 198, 42, 1, 15);
                        public static CustomResource SIDEBOTTOM = new CustomResource("Gui.Basic.Components.Button.Clicked.Side.Bottom", WIDGETFILEPATH, 1, 57, 196, 2);

                        public static CustomResource CENTER = new CustomResource("Gui.Basic.Components.Button.Clicked.Center", WIDGETFILEPATH, 2, 42, 196, 15);

                        public static MultiComponentTexture TEXTURE = new MultiComponentTexture(new TextureComponent(CENTER), new TextureComponent[]{new TextureComponent(CORNERLEFTTOP), new TextureComponent(CORNERRIGHTTOP), new TextureComponent(CORNERRIGHTBOTTOM), new TextureComponent(CORNERLEFTBOTTOM)}, new TextureComponent[]{new TextureComponent(SIDETOP), new TextureComponent(SIDERIGHT), new TextureComponent(SIDEBOTTOM), new TextureComponent(SIDELEFT)});
                    }
                }
            }

            public static class Images {
                private static String IMAGETEXTUREPATH = GUITEXTUREPATH + "Images/";
                public static CustomResource ARROWRIGHTGRAY = new CustomResource("Gui.Basic.Iamge.Arrow.Gray", IMAGETEXTUREPATH + "ArrowImage.png", Colors.DEFAULT, 0, 0, 22, 22);
                public static CustomResource ARROWRIGHTWHITE = new CustomResource("Gui.Basic.Iamge.Arrow.White", IMAGETEXTUREPATH + "ArrowImage.png", Colors.DEFAULT, 22, 0, 22, 23);
            }
        }
    }

}
