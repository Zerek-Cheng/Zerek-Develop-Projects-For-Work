// 
// Decompiled by Procyon v0.5.30
// 

package yo;

public class M<E> extends q<E>
{
    protected final v d;
    
    public M(final v<E> hash) {
        super(hash);
        this.d = hash;
    }
    
    @Override
    protected E a(final int index) {
        final Object obj = this.d.b[index];
        if (obj == v.d || obj == v.c) {
            return null;
        }
        return (E)obj;
    }
}
