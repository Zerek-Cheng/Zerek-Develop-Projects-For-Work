package noppes.npcs.util;

import com.mojang.authlib.*;
import java.util.*;
import noppes.npcs.entity.*;

public class GameProfileAlt extends GameProfile
{
    private static final UUID id;
    public EntityNPCInterface npc;
    
    public GameProfileAlt() {
        super((UUID)null, "customnpc");
    }
    
    public String getName() {
        if (this.npc == null) {
            return super.getName();
        }
        return this.npc.func_70005_c_();
    }
    
    public UUID getId() {
        if (this.npc == null) {
            return GameProfileAlt.id;
        }
        return this.npc.getPersistentID();
    }
    
    static {
        id = UUID.randomUUID();
    }
}
