package com.stefthedev.villages.utilities;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

public enum Message {
    CLAIM("claim", "You have successfully claimed new land for your village."),
    CLAIM_LIMIT("claim-limit", "You cannot claim more than &b{0} &7lands."),
    CLAIMED("claimed", "You have already claimed this land."),
    CLAIMED_OTHER("claimed-other", "This land has already been claimed by another village."),
    CREATE("create", "&b{0} &7has established a new village named &a&l{1}!"),
    CREATE_EXISTS("create-exists", "The village &b{0} &7already exists. Please choose a different name."),
    CREATE_CLAIMED("create-claimed", "You are not able to create a village on land claimed by another village."),
    DISBAND("disband", "&7Oh no! &a&l{0} &7fell into ruins..."),
    INFO("info", Arrays.asList(
            "&a&lVillage: &7{0}'s info",
            "",
            "&7Owner: &b{1}",
            "&7Members: &b{2}",
            "&7Claims: &b{3}&3/&b{4}"
    )),
    HELP("help", "&e&lVillages: &7Version {0}"),
    HELP_ITEM("help-item", "  &7- {0}"),
    OWNER_CLAIM("owner-claim", "You must be owner to claim land."),
    OWNER_DISBAND("owner-disband", "You must be owner to disband the village."),
    OWNER_INVITE("owner-invite", "You must be owner to invite members to the village."),
    OWNER_KICK("owner-kick", "You must be owner to kick members from the village."),
    OWNER_UNCLAIM("owner-unclaim", "You must be owner to unclaim land."),
    NULL("null", "The village &b{0} &7does not seem to exist."),
    PLAYER_FALSE("player-false", "You do not belong to a village."),
    PLAYER_OFFLINE("player-offline", "The player &b{0} &7does not seem to be online."),
    PLAYER_TRUE("player-true", "You already belong to a village."),
    PREFIX("prefix", "&e&lVillages: &7"),
    REQUEST_ACCEPT("request-accept", "&a[Accept]"),
    REQUEST_DENIED("request-denied", "&7You decided to deny the request."),
    REQUEST_DENY("request-deny", "&c[Deny]"),
    REQUEST_DISBAND("request-disband", "&7Are you sure you wish to disband your village? Your members may not be happy."),
    REQUEST_INVITE("request-invite", "&7You have sent an invite request to &b{0}&7."),
    REQUEST_INVITE_TARGET("request-invite-target", "&7You have been invited to join &b{0}&7. Do you wish to join?"),
    REQUEST_JOIN("request-join", "&b{0} &7has joined the village."),
    REQUEST_JOIN_TARGET("request-join-target", "&7You have joined the &b{0} &7village."),
    REQUEST_KICK("request-kick", "Are you sure you want to kick &b{0} &7 from your village?"),
    REQUEST_KICK_MEMBER("request-kick-member", "&b{0} &7has been kicked from the village."),
    REQUEST_KICK_TARGET("request-kick-target", "You have been kicked from the &b{0} &7village."),
    REQUEST_NULL("request-null", "You currently do not have any requests"),
    REQUEST_OFFLINE("request-offline", "&b{0} &7does not seem to be in your village."),
    REQUEST_PENDING("request-pending", "You already have a pending request."),
    REQUEST_PENDING_TARGET("request-pending-target", "&b{0} &7currently has a pending request."),
    TARGET_TRUE("target-true", "&b{0} &7already belongs in a village."),
    TITLE_HEADER("title-header", "&e&l{0}!"),
    TITLE_FOOTER("title-footer", "&7Welcome to &b{0}'s &7village."),
    TITLE_WILDERNESS_HEADER("title-wilderness-header", "&a&lWilderness!"),
    TITLE_WILDERNESS_FOOTER("title-wilderness-footer", "&7Fresh new land awaits you."),
    TOOLTIP("tooltip", "&7Click to select."),
    UNCLAIM("unclaim", "You have unclaimed land from your village"),
    UNCLAIM_OTHER("unclaim-other", "You can't unclaim this land as it does not belong to your village."),
    USAGE("usage", "Usage: &b/village {0}"),
    WORLDGUARD_CREATE("worldguard-create", "You are not able to establish a village as you are in a worldguard region"),
    WORLDGUARD_CLAIM("worldguard-claim", "You are not able to claim land as you are in a worldguard region");

    private String path, def;
    private List<String> list;
    private static FileConfiguration configuration;

    Message(String path, String def) {
        this.path = path;
        this.def = def;
    }

    Message(String path, List<String> list) {
        this.path = path;
        this.list = list;
    }

    @Override
    public String toString() {
        return Chat.color(configuration.getString(path, def));
    }

    public List<String> toList() {
        return configuration.getStringList(path);
    }

    public static void setConfiguration(FileConfiguration configuration) {
        Message.configuration = configuration;
    }

    public String getPath() {
        return path;
    }

    public String getDef() {
        return def;
    }

    public List<String> getList() {
        return list;
    }
}
