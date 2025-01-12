package xyz.nikitacartes.easyauth.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import xyz.nikitacartes.easyauth.storage.PlayerCacheV0;
import xyz.nikitacartes.easyauth.utils.AuthHelper;
import xyz.nikitacartes.easyauth.utils.PlayerAuth;

import java.util.concurrent.atomic.AtomicInteger;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import static xyz.nikitacartes.easyauth.EasyAuth.*;
import static xyz.nikitacartes.easyauth.utils.EasyLogger.LogDebug;

public class LoginCommand {

    public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralCommandNode<ServerCommandSource> node = registerLogin(dispatcher); // Registering the "/login" command
        if (extendedConfig.aliases.login) {
            dispatcher.register(literal("l")
                    .requires(Permissions.require("easyauth.commands.login", true))
                    .redirect(node));
        }
    }

    public static LiteralCommandNode<ServerCommandSource> registerLogin(CommandDispatcher<ServerCommandSource> dispatcher) {
        return dispatcher.register(literal("login")
                .requires(Permissions.require("easyauth.commands.login", true))
                .then(argument("password", string())
                        .executes(ctx -> login(ctx.getSource(), getString(ctx, "password")) // Tries to authenticate user
                        ))
                .executes(ctx -> {
                    langConfig.enterPassword.send(ctx.getSource());
                    return 0;
                }));
    }

    // Method called for checking the password
    private static int login(ServerCommandSource source, String pass) throws CommandSyntaxException {
        // Getting the player who send the command
        ServerPlayerEntity player = source.getPlayerOrThrow();
        String uuid = ((PlayerAuth) player).easyAuth$getFakeUuid();
        LogDebug("Player " + player.getNameForScoreboard() + "{" + uuid + "} is trying to login");
        if (((PlayerAuth) player).easyAuth$isAuthenticated()) {
            LogDebug("Player " + player.getNameForScoreboard() + "{" + uuid + "} is already authenticated");
            langConfig.alreadyAuthenticated.send(source);
            return 0;
        }
        PlayerCacheV0 playerCacheV0 = playerCacheMap.get(uuid);

        long maxLoginTries = config.maxLoginTries;
        AtomicInteger curLoginTries = playerCacheV0.loginTries;
        AuthHelper.PasswordOptions passwordResult = AuthHelper.checkPassword(uuid, pass.toCharArray());

        if (passwordResult == AuthHelper.PasswordOptions.CORRECT) {
            LogDebug("Player " + player.getNameForScoreboard() + "{" + uuid + "} provide correct password");
            if (playerCacheV0.lastKicked >= System.currentTimeMillis() - 1000 * config.resetLoginAttemptsTimeout) {
                LogDebug("Player " + player.getNameForScoreboard() + "{" + uuid + "} will be kicked due to kick timeout");
                player.networkHandler.disconnect(langConfig.loginTriesExceeded.get());
                return 0;
            }
            langConfig.successfullyAuthenticated.send(source);
            ((PlayerAuth) player).easyAuth$setAuthenticated(true);
            ((PlayerAuth) player).easyAuth$restoreLastLocation();
            curLoginTries.set(0);
            // player.getServer().getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.ADD_PLAYER, player));
            return 0;
        } else if (passwordResult == AuthHelper.PasswordOptions.NOT_REGISTERED) {
            LogDebug("Player " + player.getNameForScoreboard() + "{" + uuid + "} is not registered");
            langConfig.registerRequired.send(source);
            return 0;
        } else if (curLoginTries.incrementAndGet() == maxLoginTries && maxLoginTries != -1) { // Player exceeded maxLoginTries
            LogDebug("Player " + player.getNameForScoreboard() + "{" + uuid + "} exceeded max login tries");
            // Send the player a different error message if the max login tries is 1.
            if (maxLoginTries == 1) {
                player.networkHandler.disconnect(langConfig.wrongPassword.get());
            } else {
                player.networkHandler.disconnect(langConfig.loginTriesExceeded.get());
            }
            playerCacheV0.lastKicked = System.currentTimeMillis();
            curLoginTries.set(0);
            return 0;
        }
        LogDebug("Player " + player.getNameForScoreboard() + "{" + uuid + "} provided wrong password");
        // Sending wrong pass message
        langConfig.wrongPassword.send(source);
        return 0;
    }
}
