package com.mygeopay.core.coins.families;

/**
 * @author Geopay.me
 *
 * This family contains Algorithm: Grøstl
 */
final public class GroestlFamily implements CoinFamily {
    private final static CoinFamily instance = new GroestlFamily();
    public static synchronized CoinFamily get() {
        return instance;
    }

    @Override
    public String toString() {
        return "groestl";
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof GroestlFamily && toString().equals(obj.toString());
    }
}
