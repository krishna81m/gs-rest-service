package com;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/*
 * Breakpoint helper, stops based on a shared state
 *  STOP variable
 *
 * Everything in here should be chainable
 *  to allow adding to breakpoints
 */
public final class DEBUG {

    /*
     * global state controlling if we should
     *   stop anywhere
     */
    public static volatile boolean STOP = false;

    public static volatile List<Object> REFS = new ArrayList<>();

    /**
     * add object references when conditions meet
     * for debugging later
     */
    public static boolean ADD_REF(Object obj) {
        return ADD_REF(obj, () -> true);
    }

    public static boolean ADD_REF(Object obj, Supplier<Boolean> condition) {
        if (condition.get()) {
            REFS.add(obj);
            return true;
        }
        return false;
    }

    /*
     * STOPs on meeting condition
     *  also RETURNS if we should STOP
     *
     * This should be set when a main condition is satisfied
     *      and can be done as part of a breakpoint as well
     */
    public static boolean STOP(Supplier<Boolean> condition) {
        if (condition.get()) {
            STOP = true;
            return true;
        }
        return false;
    }

    public static boolean STOP() {
        return STOP(() -> true);
    }

}
