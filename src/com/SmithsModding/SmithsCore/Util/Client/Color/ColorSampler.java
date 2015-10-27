package com.SmithsModding.SmithsCore.Util.Client.Color;

import com.SmithsModding.SmithsCore.SmithsCore;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;


/**
 * Created by Orion
 * Created on 14.06.2015
 * 11:27
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ColorSampler {

    private static HashMap<MinecraftColor, EnumChatFormatting> iMappedColors;

    private static void initializeEnumChatFromattingMinecraftColors() {
        iMappedColors = new HashMap<MinecraftColor, EnumChatFormatting>();

        for (int tIndex = 0; tIndex < 16; tIndex++) {
            if (EnumChatFormatting.values()[tIndex].name().equals("BLACK"))
                continue;

            MinecraftColor tMappedMinecraftColor = new MinecraftColor(Minecraft.getMinecraft().fontRenderer.colorCode[tIndex]);
            SmithsCore.getLogger().info("Generated MinecraftColor Code : " + tMappedMinecraftColor.getRed() + "-" + tMappedMinecraftColor.getGreen() + "-" + tMappedMinecraftColor.getBlue() + " for the following EnumChatFormatting: " + EnumChatFormatting.values()[tIndex].name() + ".");

            iMappedColors.put(tMappedMinecraftColor, EnumChatFormatting.values()[tIndex]);
        }
    }

    public static MinecraftColor getColorSampleFromItemStack(ItemStack pStack) {
        if (pStack.getItem().getColorFromItemStack(pStack, 0) != 16777215) {
            return new MinecraftColor(pStack.getItem().getColorFromItemStack(pStack, 0));
        } else {
            pStack.getItem().registerIcons(new PlaceHolderRegistrar());

            if (pStack.getItem() instanceof ItemBlock) {
                ((ItemBlock) pStack.getItem()).field_150939_a.registerBlockIcons(new BlockPlaceHolderRegistrar());
            }

            IconPlaceHolder pIcon = (IconPlaceHolder) pStack.getItem().getIcon(pStack, 0);

            try {
                MinecraftColor tSample = calculateAverageMinecraftColor(ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(pIcon.iIconLocation).getInputStream()));

                return tSample;
            } catch (IOException e) {
                return new MinecraftColor(16777215);
            }
        }
    }

    public static MinecraftColor calculateAverageMinecraftColor(BufferedImage pBuffer) {
        long tSumR = 0, tSumG = 0, tSumB = 0;

        int tCountedPixels = 0;

        for (int tXPos = 0; tXPos < pBuffer.getWidth(); tXPos++) {
            for (int tYPos = 0; tYPos < pBuffer.getHeight(); tYPos++) {
                int tRGB = pBuffer.getRGB(tXPos, tYPos);

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
