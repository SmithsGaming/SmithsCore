package com.smithsmodding.smithscore.util.client.color;

import com.smithsmodding.smithscore.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import java.util.*;


/**
 * color Sampler to retrieve Colors from a ItemStack as well as converting a color to the Minecraft Chat
 * equivalent.
 *
 * Created by Orion
 * Created on 14.06.2015
 * 11:27
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ColorSampler {

    //1.8!!!
    //Minecraft.getMinecraft().getItemModelMesher().getRenderItem().getItemModelMesher().getItemModel(stack).getParticleTexture()

    //Cache of the Relative MinecraftColors to EnumChatFormatting.
    private static HashMap<MinecraftColor, EnumChatFormatting> iMappedColors;

    /**
     * Function used to initialize the color to formatting cache.
     * <p/>
     * It leaves black out, cause all conversions will else return Black.
     */
    private static void initializeEnumChatFromattingMinecraftColors() {
        iMappedColors = new HashMap<MinecraftColor, EnumChatFormatting>();

        for (int tIndex = 0; tIndex < 16; tIndex++) {
            if (EnumChatFormatting.values()[tIndex].name().equals("BLACK"))
                continue;

            MinecraftColor tMappedMinecraftColor = new MinecraftColor(Minecraft.getMinecraft().fontRendererObj.colorCode[tIndex]);
            SmithsCore.getLogger().info("Generated MinecraftColor Code : " + tMappedMinecraftColor.getRed() + "-" + tMappedMinecraftColor.getGreen() + "-" + tMappedMinecraftColor.getBlue() + " for the following EnumChatFormatting: " + EnumChatFormatting.values()[tIndex].name() + ".");

            iMappedColors.put(tMappedMinecraftColor, EnumChatFormatting.values()[tIndex]);
        }
    }

    /**
     * Generates a MinecraftColor based on the ItemStack given.
     *
     * It registers a PlaceHolder as IIcon by calling the the registerIcons method on the Item in the ItemStack.
     * Then it will check if the newly registered IIcon is a IconPlaceHolder, if so it will attempt to calculate an
     * Average color based on the pixels in the Image.
     *
     * If the process fails for some reason it will return White
     *
     * @param pStack The Stack to analyze.
     * @return A color based on the Pixels in the IIcon of the ItemStack or White if the process fails.
     */
    public static MinecraftColor getColorSampleFromItemStack(ItemStack pStack) {
        if (pStack.getItem().getColorFromItemStack(pStack, 0) != 16777215) {
            return new MinecraftColor(pStack.getItem().getColorFromItemStack(pStack, 0));
        } else {
            try{
                return calculateAverageMinecraftColor(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(pStack).getParticleTexture().getFrameTextureData(0));
            } catch (Exception e) {
                return new MinecraftColor(16777215);
            }
        }
    }

    /**
     * Calculates an average MinecraftColor from the given Image.
     * Iterates over all the Pixels and calculates an Average for the RGB values.
     * See Through pixels (with alpha value = 0) will be skipped.
     *
     * @param pixelData The Image to analyze
     * @return A Minecraft color that is the average RGB value of the Pixels in the Image with its Alpha value being 255
     */
    public static MinecraftColor calculateAverageMinecraftColor(int[][] pixelData) {
        long tSumR = 0, tSumG = 0, tSumB = 0;

        int tCountedPixels = 0;

        for (int tXPos = 0; tXPos < pixelData.length; tXPos++) {
            for (int tYPos = 0; tYPos < pixelData[tXPos].length; tYPos++) {
                int tRGB = pixelData[tXPos][tYPos];

                MinecraftColor tPixel = new MinecraftColor(tRGB);

                if (tPixel.getAlpha() > 0) {
                    tSumR += tPixel.getRed();
                    tSumG += tPixel.getGreen();
                    tSumB += tPixel.getBlue();

                    tCountedPixels++;
                }
            }
        }

        if (tCountedPixels == 0) {
            SmithsCore.getLogger().info("No pixels counted!");
            return new MinecraftColor(255, 255, 255);
        }

        return new MinecraftColor((int) (tSumR / tCountedPixels), (int) (tSumG / tCountedPixels), (int) (tSumB / tCountedPixels));
    }

    /**
     * Attempt to convert a given MinecraftColor to the closest EnumChatFormatting.
     * It uses the VectorCalculation system to determine which color in the EnumChatFormatting describes the given source
     * the best.
     *
     * @param pSource The Source color for the Conversion
     * @return The Converted EnumChatFormatting
     */
    public static EnumChatFormatting getChatMinecraftColorSample(MinecraftColor pSource) {
        if (iMappedColors == null)
            initializeEnumChatFromattingMinecraftColors();

        double tCurrentDistance = -1D;
        EnumChatFormatting tCurrentFormatting = null;

        for (MinecraftColor tMinecraftColor : iMappedColors.keySet()) {
            if (MinecraftColorDistance(pSource, tMinecraftColor) < tCurrentDistance) {
                tCurrentDistance = MinecraftColorDistance(pSource, tMinecraftColor);
                tCurrentFormatting = iMappedColors.get(tMinecraftColor);
            } else if (tCurrentDistance < 0) {
                tCurrentDistance = MinecraftColorDistance(pSource, tMinecraftColor);
                tCurrentFormatting = iMappedColors.get(tMinecraftColor);
            }
        }
        return tCurrentFormatting;
    }

    /**
     * Attempt to convert a given MinecraftColor to the closest EnumChatFormatting.
     * Uses Hexadecimal conversion to get a approximated EnumChatFormatting for a given SourceColor.
     *
     * Function is not as accurate as the getChatMinecraftColorSample function, yet it covers a couple corner cases in
     * which the other one fails. So it is here for completeness sake.
     *
     * @param pSource The Source color for the Conversion
     * @return The Converted EnumChatFormatting
     */
    public static EnumChatFormatting getSimpleChatMinecraftColor(MinecraftColor pSource) {
        String tFormat = "\u00a7";

        if (pSource.getRGB() == -1) {
            tFormat = tFormat + "7";
        } else {
            tFormat = tFormat + Integer.toHexString(pSource.getRGB());
        }

        for (EnumChatFormatting tFormatting : EnumChatFormatting.values())
            if (tFormatting.toString().toLowerCase().equals(tFormat.toLowerCase()))
                return tFormatting;

        return EnumChatFormatting.RESET;
    }

    /**
     * Calculates the distance between two Colors.
     *
     * It determines which of the RGB Channels is by far the strongest in the First color and then creates and abstract
     * numerical distance between the two Colors.
     *
     * I once knew why this works, but not anymore. Yet it works for the conversion from MinecraftColor to EnumChat-
     * Formatting so i am keeping it. :D
     *
     * @param pMinecraftColor1 color 1
     * @param pMinecraftColor2 color 2
     * @return The Distance in Double that describes the distance between two colors.
     */
    private static double MinecraftColorDistance(MinecraftColor pMinecraftColor1, MinecraftColor pMinecraftColor2) {
        if ((pMinecraftColor1.getRed() > pMinecraftColor1.getBlue() * 2) && (pMinecraftColor1.getRed() > pMinecraftColor1.getGreen() * 2)) {
            if ((pMinecraftColor1.getRed() > pMinecraftColor2.getRed()))
                return pMinecraftColor1.getRed() - pMinecraftColor2.getRed();

            return pMinecraftColor2.getRed() - pMinecraftColor1.getRed();
        }

        if ((pMinecraftColor1.getBlue() > pMinecraftColor1.getRed() * 2) && (pMinecraftColor1.getBlue() > pMinecraftColor1.getGreen() * 2)) {
            if ((pMinecraftColor1.getBlue() > pMinecraftColor2.getBlue()))
                return pMinecraftColor1.getBlue() - pMinecraftColor2.getBlue();

            return pMinecraftColor2.getBlue() - pMinecraftColor1.getBlue();
        }

        if ((pMinecraftColor1.getGreen() > pMinecraftColor1.getBlue() * 2) && (pMinecraftColor1.getGreen() > pMinecraftColor1.getRed() * 2)) {
            if ((pMinecraftColor1.getGreen() > pMinecraftColor2.getGreen()))
                return pMinecraftColor1.getGreen() - pMinecraftColor2.getGreen();

            return pMinecraftColor2.getGreen() - pMinecraftColor1.getGreen();
        }

        return Math.abs(pMinecraftColor1.getAngleInDegrees() - pMinecraftColor2.getAngleInDegrees());
    }
}
