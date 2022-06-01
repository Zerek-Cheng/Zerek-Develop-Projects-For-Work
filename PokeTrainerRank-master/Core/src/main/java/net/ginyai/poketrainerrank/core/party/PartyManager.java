package net.ginyai.poketrainerrank.core.party;

import net.ginyai.poketrainerrank.core.battle.MatchingManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static net.ginyai.poketrainerrank.core.util.Utils.getPlayer;
import static net.ginyai.poketrainerrank.core.util.Utils.translate;

public class PartyManager {
    public static final PartyManager instance = new PartyManager();
    private Map<UUID, CoupleParty> coupleMap = new HashMap<>();

    public Party getParty(EntityPlayerMP player) {
        UUID uuid = player.getUniqueID();
        if (coupleMap.containsKey(uuid)) {
            return coupleMap.get(uuid);
        } else {
            return new SingleParty(uuid);
        }
    }

    public boolean hasCouple(EntityPlayerMP player) {
        return coupleMap.containsKey(player.getUniqueID());
    }

    public CoupleParty createCouple(EntityPlayerMP player1, EntityPlayerMP player2) {
        UUID uuid1 = player1.getGameProfile().getId();
        UUID uuid2 = player2.getGameProfile().getId();
        if(uuid1.equals(uuid2)) {
            return null;
        }
        leaveParty(uuid1);
        leaveParty(uuid2);
        CoupleParty party = new CoupleParty(player1.getGameProfile().getId(), player2.getGameProfile().getId());
        coupleMap.put(uuid1, party);
        coupleMap.put(uuid2, party);
        MatchingManager.instance.remove(uuid1);
        MatchingManager.instance.remove(uuid2);
        return party;
    }

    public void leaveParty(UUID uuid) {
        leaveParty(uuid, translate("poketrainerrank.battle.mateLeave"));
    }

    public void leaveParty(UUID uuid, ITextComponent msg) {
        CoupleParty party = coupleMap.remove(uuid);
        if (party != null) {
            MatchingManager.instance.remove(party);
            UUID left = party.findOther(uuid);
            Optional<EntityPlayerMP> leftPlayer = getPlayer(left);
            leftPlayer.ifPresent(p -> p.sendMessage( msg));
            coupleMap.remove(left);
        }
    }

    public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        UUID uuid = event.player.getUniqueID();
        leaveParty(uuid, translate("poketrainerrank.battle.mateLogout"));
    }
}
