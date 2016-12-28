package com.smithsmodding.smithscore.util.common.capabilities;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;

/**
 * Author Orion (Created on: 09.10.2016)
 */
public class NullFactory<T extends Object> implements Callable<T> {
    @Nullable
    @Override
    public T call() throws Exception {
        return null;
    }
}
