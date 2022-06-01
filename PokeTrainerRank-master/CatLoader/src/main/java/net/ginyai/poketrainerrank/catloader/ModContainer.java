package net.ginyai.poketrainerrank.catloader;

import catserver.server.command.BukkitCommandWrapper;
import com.google.common.eventbus.EventBus;
import net.ginyai.poketrainerrank.api.PokeTrainerRank;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.versioning.VersionParser;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.command.CraftBlockCommandSender;
import org.bukkit.craftbukkit.v1_12_R1.command.CraftRemoteConsoleCommandSender;
import org.bukkit.craftbukkit.v1_12_R1.command.ServerCommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;

import java.io.File;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;


@Mod(modid = "poketrainerrankcatloader", serverSideOnly = true, acceptableRemoteVersions = "*")
public class ModContainer extends DummyModContainer {

    public ModContainer() {
        super(new ModMetadata());
        getMetadata().modId = "poketrainerrankcatloader";
        getMetadata().name = "PokeTrainerRankCatLoader";
        getMetadata().requiredMods.add(VersionParser.parseVersionReference("poketrainerrankmod"));
    }

    @Override
    public Object getMod() {
        return this;
    }

    @Override
    public File getSource() {
        return null;
    }

    @Override
    public boolean matches(Object mod) {
        return this.equals(mod);
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        FMLLog.log.warn("ModContainer registerBus");
        bus.register(this);
        return true;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle toBukkitSender = lookup.findStatic(BukkitCommandWrapper.class, "toBukkitSender", MethodType.methodType(CommandSender.class, ICommandSender.class));
            Field rconListener = CraftRemoteConsoleCommandSender.class.getDeclaredField("listener");
            rconListener.setAccessible(true);
            MethodHandle getHandle = lookup.findVirtual(CraftEntity.class,"getHandle",MethodType.methodType(Entity.class));
            MethodHandle getTileEntity = lookup.findVirtual(CraftBlockCommandSender.class, "getTileEntity",MethodType.methodType(ICommandSender.class));
            PokeTrainerRank.setCommandSourceWrapper(
                    o -> {
                        try {
                            ICommandSender commandSender = (ICommandSender) o;
                            return toBukkitSender.invoke(commandSender);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                            return null;
                        }
                    },
                    o -> {
                        try {
                            CommandSender commandSender = (CommandSender) o;
                            if (commandSender instanceof CraftRemoteConsoleCommandSender) {
                                return rconListener.get(commandSender);
                            } else if (commandSender instanceof CraftBlockCommandSender) {
                                return getTileEntity.invoke((CraftBlockCommandSender) commandSender);
                            } else if (commandSender instanceof ServerCommandSender) {
                                return FMLCommonHandler.instance().getMinecraftServerInstance();
                            } else if (commandSender instanceof CraftEntity) {
                                return getHandle.invoke((CraftEntity) commandSender);
                            } else {
                                return null;
                            }
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                            return null;
                        }
                    }
            );
        } catch (Exception e) {
            FMLLog.log.error("Error", e);
        }
    }
}
