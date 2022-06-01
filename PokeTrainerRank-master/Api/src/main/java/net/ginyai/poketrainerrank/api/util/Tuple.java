package net.ginyai.poketrainerrank.api.util;

public class Tuple<A, B> {
    private A a;
    private B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Get the first Object in the Tuple
     */
    public A getFirst()
    {
        return this.a;
    }

    /**
     * Get the second Object in the Tuple
     */
    public B getSecond()
    {
        return this.b;
    }
}