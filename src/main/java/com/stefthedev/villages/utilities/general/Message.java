package com.stefthedev.villages.utilities.general;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.List;

public enum Message {
    DISBAND("disband", "&7Oh no! &a&l{0} &7fell into ruins..."),
    HELP("help", "Type &b/villages help [page] &7to look at the help pages."),
    MENU_DISABLED_TITLE("menu-disabled-title", "&c{0}"),
    MENU_DISABLED_LORE("menu-disabled-lore", Collections.singletonList("&7Click to enable Permission.")),
    MENU_ENABLED_TITLE("menu-enabled-title", "&a{0}"),
    MENU_ENABLED_LORE("menu-enabled-lore", Collections.singletonList("&7Click to disable Permission.")),
    NO_PERMISSION("no-permission", "You do not have permissions for &b{0}&7."),
    PAGE_FORMAT("page-format", "&b{0}. &7{1}"),
    PAGE_HELP("page-help", "&e&lHelp: &7[{0}/{1}]"),
    PAGE_LIMIT("page-limit", "There are only &b{0} &7help pages."),
    PAGE_NEXT("page-next", "Type &b/villages help {0} &7for the next page."),
    PAGE_PREVIOUS("page-previous", "Type &b/villages help {0} &7for the previous page."),
    PLAYER_OFFLINE("player-offline", "The player &b{0} &7does not seem to be online."),
    PREFIX("prefix", "&e&lVillages: &7"),
    REQUEST_ACCEPT("request-accept", "&a[Accept]"),
    REQUEST_DENY("request-deny", "&c[Deny]"),
    REQUEST_DENIED("request-denied", "You have decided to deny the request."),
    REQUEST_DISBAND("request-disband", "Are you sure you want to disband the village? Members of your village may not be happy!"),
    REQUEST_INVITE("request-invite", "You have sent an invite request to &b{0}&7."),
    REQUEST_INVITE_TARGET("request-invite-target", "You have been invited to join &b{0}&7. Do you wish to join?"),
    REQUEST_JOIN("request-join", "&b{0} &7has joined the village."),
    REQUEST_JOIN_TARGET("request-join-target", "&7You have joined the &b{0} &7village."),
    REQUEST_KICK("request-kick", "Are you sure you want to kick &b{0} &7 from your village?"),
    REQUEST_KICK_SELF("request-kick-self", "You can't kick yourself from the village."),
    REQUEST_KICK_TARGET("request-kick-target", "You have been kicked from the &b{0} &7village."),
    REQUEST_NULL("request-null", "You do not have any pending requests."),
    REQUEST_PENDING("request-pending", "You already have a pending request."),
    TITLE_HEADER("title-header", "&e&l{0}!"),
    TITLE_FOOTER("title-footer", "&7Welcome to &b{0}'s &7village."),
    TITLE_WILDERNESS_HEADER("title-wilderness-header", "&a&lWilderness!"),
    TITLE_WILDERNESS_FOOTER("title-wilderness-footer", "&7Fresh new land awaits you."),
    TOOLTIP("tooltip", "&7Click to select."),
    USAGE("usage", "Usage: &b{0}"),
    VILLAGE_ALREADY_OWNER("village-already-owner", "You can't set the new owner to yourself."),
    VILLAGE_CLAIM("village-claim", "You have claimed new land for your village."),
    VILLAGE_CLAIM_OWNED("village-claim-owned", "The land you are trying to claim already belongs to your village."),
    VILLAGE_CLAIM_OTHER("village-claim-other", "The land you are trying to claim belongs to another village."),
    VILLAGE_CREATE("village-create", "You have established a new village named &b{0}&7."),
    VILLAGE_DESCRIPTION_LIMIT("village-description-limit", "The description of your village can't be longer than &b32&7 characters."),
    VILLAGE_EXISTS("village-exists", "A village named &b{0} &7has already been established."),
    VILLAGE_HOME("village-home", "You have been teleported to your village home."),
    VILLAGE_LEAVE("village-leave", "You have left the &b{0} &7village."),
    VILLAGE_LEAVE_OWNER("village-leave-owner", "You can't leave your current village because you are the owner."),
    VILLAGE_MEMBER_NULL("village-member-null", "&b{0} &7does not seem to belong to your village."),
    VILLAGE_NOT_NULL("village-not-null", "You already belong to a village."),
    VILLAGE_NULL("village-null", "You do not belong to a village."),
    VILLAGE_OWNER("village-owner", "You must be the owner of the village to do that action."),
    VILLAGE_SET_DESCRIPTION("village-set-description", "You have set the village description to: &b{0}"),
    VILLAGE_SET_HOME("village-set-home", "You have set the home for your village."),
    VILLAGE_SET_OWNER("village-set-owner", "You have set the new village owner to &b{0}&7."),
    VILLAGE_UNCLAIM("village-unclaim", "You have unclaimed land for your village."),
    VILLAGE_UNCLAIM_ONE("village-unclaim-one", "You can't unclaim because your village only owns one land."),
    VILLAGE_UNCLAIM_OTHER("village-unclaim-other", "The land you are trying to unclaim does not belong to your village.");

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
