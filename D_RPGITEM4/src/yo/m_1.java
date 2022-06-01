/*
 * Decompiled with CFR 0_133.
 */
package yo;

import yo.q_1;
import yo.v_0;

public class m_1<E>
extends q_1<E> {
    protected final v_0 d;

    public m_1(v_0<E> hash) {
        super(hash);
        this.d = hash;
    }

    @Override
    protected E a(int index) {
        Object obj = this.d.b[index];
        if (obj == v_0.d || obj == v_0.c) {
            return null;
        }
        return (E)obj;
    }
}

