//
// Decompiled by Procyon v0.5.30
//

package me.libraryaddict.disguise.disguisetypes;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import me.libraryaddict.disguise.LibsDisguises;
import me.libraryaddict.disguise.disguisetypes.watchers.PlayerWatcher;
import me.libraryaddict.disguise.utilities.DisguiseUtilities;
import me.libraryaddict.disguise.utilities.LibsProfileLookup;
import me.libraryaddict.disguise.utilities.ReflectionManager;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerDisguise extends TargetedDisguise {
    private LibsProfileLookup currentLookup;
    private WrappedGameProfile gameProfile;
    private String playerName;
    private String skinToUse;

    private PlayerDisguise() {
    }

    public PlayerDisguise(final Player player) {
        this(ReflectionManager.getGameProfile(player));
    }

    public PlayerDisguise(final Player player, final Player skinToUse) {
        this(ReflectionManager.getGameProfile(player), ReflectionManager.getGameProfile(skinToUse));
    }

    public PlayerDisguise(final String name) {
        this.setName(name);
        this.setSkin(name);
        this.createDisguise(DisguiseType.PLAYER);
    }

    public PlayerDisguise(final String name, final String skinToUse) {
        this.setName(name);
        this.setSkin(skinToUse);
        this.createDisguise(DisguiseType.PLAYER);
    }

    public PlayerDisguise(final WrappedGameProfile gameProfile) {
        this.setName(gameProfile.getName());
        this.gameProfile = gameProfile;
        this.createDisguise(DisguiseType.PLAYER);
    }

    public PlayerDisguise(final WrappedGameProfile gameProfile, final WrappedGameProfile skinToUse) {
        this.setName(gameProfile.getName());
        this.gameProfile = gameProfile;
        this.setSkin(skinToUse);
        this.createDisguise(DisguiseType.PLAYER);
    }

    @Override
    public PlayerDisguise addPlayer(final Player player) {
        return (PlayerDisguise) super.addPlayer(player);
    }

    @Override
    public PlayerDisguise addPlayer(final String playername) {
        return (PlayerDisguise) super.addPlayer(playername);
    }

    @Override
    public PlayerDisguise clone() {
        final PlayerDisguise disguise = new PlayerDisguise();
        disguise.playerName = this.getName();
        if (disguise.currentLookup == null && disguise.gameProfile != null) {
            disguise.skinToUse = this.getSkin();
            disguise.gameProfile = ReflectionManager.getClonedProfile(this.getGameProfile());
        } else {
            disguise.setSkin(this.getSkin());
        }
        disguise.setReplaceSounds(this.isSoundsReplaced());
        disguise.setViewSelfDisguise(this.isSelfDisguiseVisible());
        disguise.setHearSelfDisguise(this.isSelfDisguiseSoundsReplaced());
        disguise.setHideArmorFromSelf(this.isHidingArmorFromSelf());
        disguise.setHideHeldItemFromSelf(this.isHidingHeldItemFromSelf());
        disguise.setVelocitySent(this.isVelocitySent());
        disguise.setModifyBoundingBox(this.isModifyBoundingBox());
        disguise.setWatcher(this.getWatcher().clone(disguise));
        disguise.createDisguise(DisguiseType.PLAYER);
        return disguise;
    }

    public WrappedGameProfile getGameProfile() {
        if (this.gameProfile == null) {
            if (this.getSkin() != null) {
                this.gameProfile = ReflectionManager.getGameProfile(null, this.getName());
            } else {
                this.gameProfile = ReflectionManager.getGameProfileWithThisSkin(null, this.getName(), DisguiseUtilities.getProfileFromMojang(this));
            }
        }
        return this.gameProfile;
    }

    public String getName() {
        return this.playerName;
    }

    public String getSkin() {
        return this.skinToUse;
    }

    @Override
    public PlayerWatcher getWatcher() {
        return (PlayerWatcher) super.getWatcher();
    }

    @Override
    public boolean isPlayerDisguise() {
        return true;
    }

    @Override
    public PlayerDisguise removePlayer(final Player player) {
        return (PlayerDisguise) super.removePlayer(player);
    }

    @Override
    public PlayerDisguise removePlayer(final String playername) {
        return (PlayerDisguise) super.removePlayer(playername);
    }

    @Override
    public PlayerDisguise setDisguiseTarget(final TargetType newTargetType) {
        return (PlayerDisguise) super.setDisguiseTarget(newTargetType);
    }

    @Override
    public PlayerDisguise setEntity(final Entity entity) {
        return (PlayerDisguise) super.setEntity(entity);
    }

    public void setGameProfile(final WrappedGameProfile gameProfile) {
        this.gameProfile = ReflectionManager.getGameProfileWithThisSkin(null, gameProfile.getName(), gameProfile);
    }

    @Override
    public PlayerDisguise setHearSelfDisguise(final boolean hearSelfDisguise) {
        return (PlayerDisguise) super.setHearSelfDisguise(hearSelfDisguise);
    }

    @Override
    public PlayerDisguise setHideArmorFromSelf(final boolean hideArmor) {
        return (PlayerDisguise) super.setHideArmorFromSelf(hideArmor);
    }

    @Override
    public PlayerDisguise setHideHeldItemFromSelf(final boolean hideHeldItem) {
        return (PlayerDisguise) super.setHideHeldItemFromSelf(hideHeldItem);
    }

    @Override
    public PlayerDisguise setKeepDisguiseOnEntityDespawn(final boolean keepDisguise) {
        return (PlayerDisguise) super.setKeepDisguiseOnEntityDespawn(keepDisguise);
    }

    @Override
    public PlayerDisguise setKeepDisguiseOnPlayerDeath(final boolean keepDisguise) {
        return (PlayerDisguise) super.setKeepDisguiseOnPlayerDeath(keepDisguise);
    }

    @Override
    public PlayerDisguise setKeepDisguiseOnPlayerLogout(final boolean keepDisguise) {
        return (PlayerDisguise) super.setKeepDisguiseOnPlayerLogout(keepDisguise);
    }

    @Override
    public PlayerDisguise setModifyBoundingBox(final boolean modifyBox) {
        return (PlayerDisguise) super.setModifyBoundingBox(modifyBox);
    }

    private void setName(String name) {
        if (name.length() > 16) {
            name = name.substring(0, 16);
        }
        this.playerName = name;
    }

    @Override
    public PlayerDisguise setReplaceSounds(final boolean areSoundsReplaced) {
        return (PlayerDisguise) super.setReplaceSounds(areSoundsReplaced);
    }

    public PlayerDisguise setSkin(final String newSkin) {
        this.skinToUse = newSkin;
        if (newSkin == null) {
            this.currentLookup = null;
            this.gameProfile = null;
        } else {
            if (newSkin.length() > 16) {
                this.skinToUse = newSkin.substring(0, 16);
            }
            this.currentLookup = new LibsProfileLookup() {
                @Override
                public void onLookup(final WrappedGameProfile gameProfile) {
                    if (PlayerDisguise.this.currentLookup != this || gameProfile == null) {
                        return;
                    }
                    PlayerDisguise.this.setSkin(gameProfile);
                    PlayerDisguise.this.currentLookup = null;
                }
            };
            final WrappedGameProfile gameProfile = DisguiseUtilities.getProfileFromMojang(this.skinToUse, this.currentLookup, LibsDisguises.getInstance().getConfig().getBoolean("ContactMojangServers", true));
            if (gameProfile != null) {
                this.setSkin(gameProfile);
            }
        }
        return this;
    }

    public PlayerDisguise setSkin(final WrappedGameProfile gameProfile) {
        if (gameProfile == null) {
            this.gameProfile = null;
            this.skinToUse = null;
            return this;
        }
        Validate.notEmpty(gameProfile.getName(), "Name must be set");
        this.currentLookup = null;
        this.skinToUse = gameProfile.getName();
        this.gameProfile = ReflectionManager.getGameProfileWithThisSkin(null, this.getName(), gameProfile);
        if (DisguiseUtilities.isDisguiseInUse(this)) {
            DisguiseUtilities.refreshTrackers(this);
        }
        return this;
    }

    public PlayerDisguise setSkin(final WrappedGameProfile gameProfile, boolean real) {
        if (gameProfile == null) {
            this.gameProfile = null;
            this.skinToUse = null;
            return this;
        }
        Validate.notEmpty(gameProfile.getName(), "Name must be set");
        this.currentLookup = null;
        this.skinToUse = gameProfile.getName();
        this.gameProfile = this.gameProfile = ReflectionManager.getGameProfileWithThisSkin(UUID.nameUUIDFromBytes(gameProfile.getName().getBytes()), this.getName(), gameProfile);
        if (DisguiseUtilities.isDisguiseInUse(this)) {
            DisguiseUtilities.refreshTrackers(this);
        }
        return this;
    }

    @Override
    public PlayerDisguise setVelocitySent(final boolean sendVelocity) {
        return (PlayerDisguise) super.setVelocitySent(sendVelocity);
    }

    @Override
    public PlayerDisguise setViewSelfDisguise(final boolean viewSelfDisguise) {
        return (PlayerDisguise) super.setViewSelfDisguise(viewSelfDisguise);
    }

    @Override
    public PlayerDisguise setWatcher(final FlagWatcher newWatcher) {
        return (PlayerDisguise) super.setWatcher(newWatcher);
    }

    @Override
    public PlayerDisguise silentlyAddPlayer(final String playername) {
        return (PlayerDisguise) super.silentlyAddPlayer(playername);
    }

    @Override
    public PlayerDisguise silentlyRemovePlayer(final String playername) {
        return (PlayerDisguise) super.silentlyRemovePlayer(playername);
    }
}
