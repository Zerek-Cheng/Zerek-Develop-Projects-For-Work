package noppes.npcs;

import noppes.npcs.entity.*;
import noppes.npcs.controllers.*;
import net.minecraft.nbt.*;
import java.util.*;

public class VersionCompatibility
{
    public static int ModRev;
    
    public static void CheckNpcCompatibility(final EntityNPCInterface npc, final NBTTagCompound compound) {
        if (npc.npcVersion == VersionCompatibility.ModRev) {
            return;
        }
        if (npc.npcVersion < 12) {
            CompatabilityFix(compound, npc.advanced.writeToNBT(new NBTTagCompound()));
            CompatabilityFix(compound, npc.ai.writeToNBT(new NBTTagCompound()));
            CompatabilityFix(compound, npc.stats.writeToNBT(new NBTTagCompound()));
            CompatabilityFix(compound, npc.display.writeToNBT(new NBTTagCompound()));
            CompatabilityFix(compound, npc.inventory.writeEntityToNBT(new NBTTagCompound()));
        }
        if (npc.npcVersion < 5) {
            String texture = compound.getString("Texture");
            texture = texture.replace("/mob/customnpcs/", "customnpcs:textures/entity/");
            texture = texture.replace("/mob/", "customnpcs:textures/entity/");
            compound.setString("Texture", texture);
        }
        if (npc.npcVersion < 6 && compound.getTag("NpcInteractLines") instanceof NBTTagList) {
            final List<String> interactLines = NBTTags.getStringList(compound.getTagList("NpcInteractLines", 10));
            Lines lines = new Lines();
            for (int i = 0; i < interactLines.size(); ++i) {
                final Line line = new Line();
                line.text = (String)interactLines.toArray()[i];
                lines.lines.put(i, line);
            }
            compound.setTag("NpcInteractLines", (NBTBase)lines.writeToNBT());
            final List<String> worldLines = NBTTags.getStringList(compound.getTagList("NpcLines", 10));
            lines = new Lines();
            for (int j = 0; j < worldLines.size(); ++j) {
                final Line line2 = new Line();
                line2.text = (String)worldLines.toArray()[j];
                lines.lines.put(j, line2);
            }
            compound.setTag("NpcLines", (NBTBase)lines.writeToNBT());
            final List<String> attackLines = NBTTags.getStringList(compound.getTagList("NpcAttackLines", 10));
            lines = new Lines();
            for (int k = 0; k < attackLines.size(); ++k) {
                final Line line3 = new Line();
                line3.text = (String)attackLines.toArray()[k];
                lines.lines.put(k, line3);
            }
            compound.setTag("NpcAttackLines", (NBTBase)lines.writeToNBT());
            final List<String> killedLines = NBTTags.getStringList(compound.getTagList("NpcKilledLines", 10));
            lines = new Lines();
            for (int l = 0; l < killedLines.size(); ++l) {
                final Line line4 = new Line();
                line4.text = (String)killedLines.toArray()[l];
                lines.lines.put(l, line4);
            }
            compound.setTag("NpcKilledLines", (NBTBase)lines.writeToNBT());
        }
        if (npc.npcVersion == 12) {
            final NBTTagList list = compound.getTagList("StartPos", 3);
            if (list.tagCount() == 3) {
                final int z = ((NBTTagInt)list.removeTag(2)).getInt();
                final int y = ((NBTTagInt)list.removeTag(1)).getInt();
                final int x = ((NBTTagInt)list.removeTag(0)).getInt();
                compound.setIntArray("StartPosNew", new int[] { x, y, z });
            }
        }
        if (npc.npcVersion == 13) {
            boolean bo = compound.getBoolean("HealthRegen");
            compound.setInteger("HealthRegen", (int)(bo ? 1 : 0));
            final NBTTagCompound comp = compound.getCompoundTag("TransformStats");
            bo = comp.getBoolean("HealthRegen");
            comp.setInteger("HealthRegen", (int)(bo ? 1 : 0));
            compound.setTag("TransformStats", (NBTBase)comp);
        }
        npc.npcVersion = VersionCompatibility.ModRev;
    }
    
    public static void CheckAvailabilityCompatibility(final ICompatibilty compatibilty, final NBTTagCompound compound) {
        if (compatibilty.getVersion() == VersionCompatibility.ModRev) {
            return;
        }
        CompatabilityFix(compound, compatibilty.writeToNBT(new NBTTagCompound()));
        compatibilty.setVersion(VersionCompatibility.ModRev);
    }
    
    private static void CompatabilityFix(final NBTTagCompound compound, final NBTTagCompound check) {
        final Collection<String> tags = (Collection<String>)check.getKeySet();
        for (final String name : tags) {
            final NBTBase nbt = check.getTag(name);
            if (!compound.hasKey(name)) {
                compound.setTag(name, nbt);
            }
            else {
                if (!(nbt instanceof NBTTagCompound) || !(compound.getTag(name) instanceof NBTTagCompound)) {
                    continue;
                }
                CompatabilityFix(compound.getCompoundTag(name), (NBTTagCompound)nbt);
            }
        }
    }
    
    static {
        VersionCompatibility.ModRev = 16;
    }
}
