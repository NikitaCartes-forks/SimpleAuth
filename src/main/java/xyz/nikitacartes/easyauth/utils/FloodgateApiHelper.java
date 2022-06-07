package xyz.nikitacartes.easyauth.utils;

import dev.cafeteria.fakeplayerapi.server.FakeServerPlayer;
import net.minecraft.entity.player.PlayerEntity;
import org.geysermc.floodgate.api.FloodgateApi;

public class FloodgateApiHelper{
    /**
     * Checks if player is a floodgate one.
     *
     * @param player player to check
     * @return true if it's fake, otherwise false
     */

    public static boolean isFloodgatePlayer(PlayerEntity player) {
        FloodgateApi floodgateApi = FloodgateApi.getInstance();
        return floodgateApi.isFloodgatePlayer(player.getUuid());
    }
}