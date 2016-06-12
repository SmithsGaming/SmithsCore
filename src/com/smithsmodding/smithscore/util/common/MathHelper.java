package com.smithsmodding.smithscore.util.common;


import javax.vecmath.Vector3f;

/**
 * @Author Marc (Created on: 11.06.2016)
 */
public class MathHelper {

    public static float fromDegreeToRadian(float degree) {
        return (float) (degree / 180 * Math.PI);
    }

    public static Vector3f fromDegreeToRadian(Vector3f degree) {
        return new Vector3f(fromDegreeToRadian(degree.getX()), fromDegreeToRadian(degree.getY()), fromDegreeToRadian(degree.getZ()));
    }
}
