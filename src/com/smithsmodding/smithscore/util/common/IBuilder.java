package com.smithsmodding.smithscore.util.common;

/**
 * Created by marcf on 1/21/2017.
 */
public interface IBuilder<T> {

    /**
     * Method to complete the building process of T
     *
     * @return A completed instance of T
     */
    T build();
}
