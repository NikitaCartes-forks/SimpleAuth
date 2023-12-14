package xyz.nikitacartes.easyauth.config;

import xyz.nikitacartes.easyauth.EasyAuth;
import xyz.nikitacartes.easyauth.config.deprecated.AuthConfig;

import java.io.File;

import static xyz.nikitacartes.easyauth.EasyAuth.gameDirectory;
import static xyz.nikitacartes.easyauth.utils.EasyLogger.LogInfo;
import static xyz.nikitacartes.easyauth.config.LangConfigV1.TranslatableText;

public class ConfigMigration {

    public static void migrateFromV0() {
        AuthConfig oldConfig = AuthConfig.load(new File(gameDirectory + "/mods/EasyAuth/config.json"));
        if (oldConfig == null) {
            return;
        }

        LogInfo("Migrating config from v0 to v1");

        EasyAuth.config = new MainConfigV1();
        EasyAuth.config.premiumAutologin = oldConfig.main.premiumAutologin;
        EasyAuth.config.floodgateAutoLogin = oldConfig.main.floodgateAutologin;
        EasyAuth.config.maxLoginTries = oldConfig.main.maxLoginTries;
        EasyAuth.config.kickTimeout = oldConfig.main.kickTime;
        EasyAuth.config.resetLoginAttemptsTimeout = oldConfig.experimental.resetLoginAttemptsTime;
        EasyAuth.config.sessionTimeout = oldConfig.main.sessionTimeoutTime;
        EasyAuth.config.enableGlobalPassword = oldConfig.main.enableGlobalPassword;
        EasyAuth.config.hidePlayerCoords = oldConfig.main.spawnOnJoin;
        EasyAuth.config.debug = oldConfig.experimental.debugMode;
        EasyAuth.config.configVersion = 1;
        EasyAuth.config.worldSpawn.dimension = oldConfig.worldSpawn.dimension;
        EasyAuth.config.worldSpawn.x = oldConfig.worldSpawn.x;
        EasyAuth.config.worldSpawn.y = oldConfig.worldSpawn.y;
        EasyAuth.config.worldSpawn.z = oldConfig.worldSpawn.z;
        EasyAuth.config.worldSpawn.yaw = oldConfig.worldSpawn.yaw;
        EasyAuth.config.worldSpawn.pitch = oldConfig.worldSpawn.pitch;
        EasyAuth.config.save();

        EasyAuth.extendedConfig = new ExtendedConfigV1();
        EasyAuth.extendedConfig.allowChat = oldConfig.experimental.allowChat;
        EasyAuth.extendedConfig.allowCommands = oldConfig.experimental.allowCommands;
        EasyAuth.extendedConfig.allowedCommands = oldConfig.experimental.allowedCommands;
        EasyAuth.extendedConfig.allowMovement = oldConfig.experimental.allowMovement;
        EasyAuth.extendedConfig.allowBlockInteraction = oldConfig.experimental.allowBlockUse;
        EasyAuth.extendedConfig.allowEntityInteraction = oldConfig.main.allowEntityInteract;
        EasyAuth.extendedConfig.allowBlockBreaking = oldConfig.experimental.allowBlockPunch;
        EasyAuth.extendedConfig.allowEntityAttacking = oldConfig.experimental.allowEntityPunch;
        EasyAuth.extendedConfig.allowItemDropping = oldConfig.experimental.allowItemDrop;
        EasyAuth.extendedConfig.allowItemMoving = oldConfig.experimental.allowItemMoving;
        EasyAuth.extendedConfig.allowItemUsing = oldConfig.experimental.allowItemUse;
        EasyAuth.extendedConfig.playerInvulnerable = oldConfig.experimental.playerInvulnerable;
        EasyAuth.extendedConfig.playerIgnored = oldConfig.experimental.playerInvisible;
        EasyAuth.extendedConfig.teleportationTimeoutMs = oldConfig.experimental.teleportationTimeoutInMs;
        EasyAuth.extendedConfig.enableAliases = oldConfig.experimental.enableAliases;
        EasyAuth.extendedConfig.tryPortalRescue = oldConfig.main.tryPortalRescue;
        EasyAuth.extendedConfig.minPasswordLength = oldConfig.main.minPasswordChars;
        EasyAuth.extendedConfig.maxPasswordLength = oldConfig.main.maxPasswordChars;
        EasyAuth.extendedConfig.usernameRegexp = oldConfig.main.usernameRegex;
        EasyAuth.extendedConfig.floodgateBypassRegex = oldConfig.experimental.floodgateBypassUsernameRegex;
        EasyAuth.extendedConfig.hidePlayersFromPlayerList = oldConfig.main.hideUnauthenticatedPLayersFromPlayerList;
        EasyAuth.extendedConfig.preventAnotherLocationKick = oldConfig.experimental.preventAnotherLocationKick;
        EasyAuth.extendedConfig.useBcrypt = oldConfig.experimental.useBCryptLibrary;
        EasyAuth.extendedConfig.forcedOfflineUuid = oldConfig.experimental.forcedOfflineUuids;
        EasyAuth.extendedConfig.skipAllAuthChecks = oldConfig.experimental.skipAllAuthChecks;
        EasyAuth.extendedConfig.save();

        EasyAuth.langConfig = new LangConfigV1();
        EasyAuth.langConfig.enableServerSideTranslation = oldConfig.experimental.enableServerSideTranslation;
        EasyAuth.langConfig.enterPassword = new TranslatableText("text.easyauth.enterPassword", oldConfig.lang.enterPassword);
        EasyAuth.langConfig.enterNewPassword = new TranslatableText("text.easyauth.enterNewPassword", oldConfig.lang.enterNewPassword);
        EasyAuth.langConfig.wrongPassword = new TranslatableText("text.easyauth.wrongPassword", oldConfig.lang.wrongPassword);
        EasyAuth.langConfig.matchPassword = new TranslatableText("text.easyauth.matchPassword", oldConfig.lang.matchPassword);
        EasyAuth.langConfig.passwordUpdated = new TranslatableText("text.easyauth.passwordUpdated", oldConfig.lang.passwordUpdated);
        EasyAuth.langConfig.loginRequired = new TranslatableText("text.easyauth.loginRequired", oldConfig.lang.loginRequired);
        EasyAuth.langConfig.loginTriesExceeded = new TranslatableText("text.easyauth.loginTriesExceeded", oldConfig.lang.loginTriesExceeded);
        EasyAuth.langConfig.globalPasswordSet = new TranslatableText("text.easyauth.globalPasswordSet", oldConfig.lang.globalPasswordSet);
        EasyAuth.langConfig.cannotChangePassword = new TranslatableText("text.easyauth.cannotChangePassword", oldConfig.lang.cannotChangePassword);
        EasyAuth.langConfig.cannotUnregister = new TranslatableText("text.easyauth.cannotUnregister", oldConfig.lang.cannotUnregister);
        EasyAuth.langConfig.notAuthenticated = new TranslatableText("text.easyauth.notAuthenticated", oldConfig.lang.notAuthenticated);
        EasyAuth.langConfig.alreadyAuthenticated = new TranslatableText("text.easyauth.alreadyAuthenticated", oldConfig.lang.alreadyAuthenticated);
        EasyAuth.langConfig.successfullyAuthenticated = new TranslatableText("text.easyauth.successfullyAuthenticated", oldConfig.lang.successfullyAuthenticated);
        EasyAuth.langConfig.successfulLogout = new TranslatableText("text.easyauth.successfulLogout", oldConfig.lang.successfulLogout);
        EasyAuth.langConfig.timeExpired = new TranslatableText("text.easyauth.timeExpired", oldConfig.lang.timeExpired);
        EasyAuth.langConfig.registerRequired = new TranslatableText("text.easyauth.registerRequired", oldConfig.lang.registerRequired);
        EasyAuth.langConfig.alreadyRegistered = new TranslatableText("text.easyauth.alreadyRegistered", oldConfig.lang.alreadyRegistered);
        EasyAuth.langConfig.registerSuccess = new TranslatableText("text.easyauth.registerSuccess", oldConfig.lang.registerSuccess);
        EasyAuth.langConfig.userdataDeleted = new TranslatableText("text.easyauth.userdataDeleted", oldConfig.lang.userdataDeleted);
        EasyAuth.langConfig.userdataUpdated = new TranslatableText("text.easyauth.userdataUpdated", oldConfig.lang.userdataUpdated);
        EasyAuth.langConfig.accountDeleted = new TranslatableText("text.easyauth.accountDeleted", oldConfig.lang.accountDeleted);
        EasyAuth.langConfig.configurationReloaded = new TranslatableText("text.easyauth.configurationReloaded", oldConfig.lang.configurationReloaded);
        EasyAuth.langConfig.maxPasswordChars = new TranslatableText("text.easyauth.maxPasswordChars", oldConfig.lang.maxPasswordChars);
        EasyAuth.langConfig.minPasswordChars = new TranslatableText("text.easyauth.minPasswordChars", oldConfig.lang.minPasswordChars);
        EasyAuth.langConfig.disallowedUsername = new TranslatableText("text.easyauth.disallowedUsername", oldConfig.lang.disallowedUsername);
        EasyAuth.langConfig.playerAlreadyOnline = new TranslatableText("text.easyauth.playerAlreadyOnline", oldConfig.lang.playerAlreadyOnline);
        EasyAuth.langConfig.worldSpawnSet = new TranslatableText("text.easyauth.worldSpawnSet", oldConfig.lang.worldSpawnSet);
        EasyAuth.langConfig.corruptedPlayerData = new TranslatableText("text.easyauth.corruptedPlayerData", oldConfig.lang.corruptedPlayerData);
        EasyAuth.langConfig.userNotRegistered = new TranslatableText("text.easyauth.userNotRegistered", oldConfig.lang.userNotRegistered);
        EasyAuth.langConfig.cannotLogout = new TranslatableText("text.easyauth.cannotLogout", oldConfig.lang.cannotLogout);
        EasyAuth.langConfig.offlineUuid = new TranslatableText("text.easyauth.offlineUuid", oldConfig.lang.offlineUuid);
        EasyAuth.langConfig.registeredPlayers = new TranslatableText("text.easyauth.registeredPlayers", oldConfig.lang.registeredPlayers);
        EasyAuth.langConfig.addToForcedOffline = new TranslatableText("text.easyauth.addToForcedOffline", oldConfig.lang.addToForcedOffline);
        EasyAuth.langConfig.save();

        EasyAuth.technicalConfig = new TechnicalConfigV1();
        EasyAuth.technicalConfig.globalPassword = oldConfig.main.globalPassword;
        EasyAuth.technicalConfig.forcedOfflinePlayers = oldConfig.main.forcedOfflinePlayers;
        EasyAuth.technicalConfig.confirmedOnlinePlayers = oldConfig.experimental.verifiedOnlinePlayer;
        EasyAuth.technicalConfig.save();

        EasyAuth.storageConfig = new StorageConfigV1();
        EasyAuth.storageConfig.databaseType = oldConfig.main.databaseType;
        EasyAuth.storageConfig.mySqlConfig.mysqlHost = oldConfig.main.MySQLHost;
        EasyAuth.storageConfig.mySqlConfig.mysqlUser = oldConfig.main.MySQLUser;
        EasyAuth.storageConfig.mySqlConfig.mysqlPassword = oldConfig.main.MySQLPassword;
        EasyAuth.storageConfig.mySqlConfig.mysqlDatabase = oldConfig.main.MySQLDatabase;
        EasyAuth.storageConfig.mySqlConfig.mysqlTable = oldConfig.main.MySQLTableName;
        EasyAuth.storageConfig.mongoDBConfig.mongodbConnectionString = oldConfig.main.MongoDBConnectionString;
        EasyAuth.storageConfig.mongoDBConfig.mongodbDatabase = oldConfig.main.MongoDBDatabase;
        EasyAuth.storageConfig.useSimpleAuthDb = oldConfig.experimental.useSimpleAuthDatabase;
        EasyAuth.storageConfig.save();
    }
}
