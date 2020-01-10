package com.stefthedev.villages.utilities.general;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Message {
    DISBAND("Messages.disband", "&7Oh no! &a&l{0} &7fell into ruins..."),
    HELP("Messages.help", "Type &b/villages help [page] &7to look at the help pages."),
    MENU_BACK_TITLE("Menu.back.title", "&fBack"),
    MENU_BACK_LORE("Menu.back.lore", Collections.singletonList("&7Click for previous menu.")),
    MENU_CLAIM_TITLE("Menu.claim.title", "&a{0}"),
    MENU_CLAIM_LORE("Menu.claim.lore", Collections.singletonList("&7Click to teleport to claim.")),
    MENU_CLAIMS_TITLE("Menu.claims.title", "&b&lClaims"),
    MENU_CLAIMS_LORE("Menu.claims.lore", Collections.singletonList("&7Edit claims of your village.")),
    MENU_DISABLED_TITLE("Menu.disabled.title", "&c{0}"),
    MENU_DISABLED_LORE("Menu.disabled.lore", Collections.singletonList("&7Click to enable Permission.")),
    MENU_DISBAND_TITLE("Menu.disband.title", "&c&lDisband"),
    MENU_DISBAND_LORE("Menu.disband.lore", Collections.singletonList("&7Click to disband your village.")),
    MENU_ENABLED_TITLE("Menu.enabled.title", "&a{0}"),
    MENU_ENABLED_LORE("Menu.enabled.lore", Collections.singletonList("&7Click to disable Permission.")),
    MENU_GLOBAL_PERMISSIONS_TITLE("Menu.global-permissions.title", "&6&lGlobal Permissions"),
    MENU_GLOBAL_PERMISSIONS_LORE("Menu.global-permissions.lore", Collections.singletonList("&7Click to change global permissions.")),
    MENU_HOME_TITLE("Menu.home.title", "&6&lHome"),
    MENU_HOME_LORE("Menu.home.lore", Collections.singletonList("&7Teleport to village home.")),
    MENU_INFORMATION_TITLE("Menu.information.title", "&e&lInformation"),
    MENU_INFORMATION_LORE("Menu.information.lore", Arrays.asList(
            "&7&o{description}",
            "",
            "&7Owner: &b{owner}",
            "&7Level: &a{level}",
            "&7Members: &e{members}",
            "&7Claims: &c{claims}"
    )),
    MENU_MEMBER_TITLE("Menu.member.title", "&a{0}"),
    MENU_MEMBER_LORE("Menu.member.lore", Collections.singletonList("&7Click to edit member.")),
    MENU_MEMBERS_TITLE("Menu.members.title", "&a&lMembers"),
    MENU_MEMBERS_LORE("Menu.members.lore", Collections.singletonList("&7Edit members of your village.")),
    MENU_NEXT_TITLE("Menu.next.title", "&fNext"),
    MENU_NEXT_LORE("Menu.next.lore", Collections.singletonList("&7Click for next page.")),
    MENU_PEACEFUL_ENABLED_TITLE("Menu.peaceful.enabled.title", "&aPeaceful"),
    MENU_PEACEFUL_ENABLED_LORE("Menu.peaceful.enabled.lore", Collections.singletonList("&7Click to disable.")),
    MENU_PEACEFUL_DISABLED_TITLE("Menu.peaceful.disabled.title", "&cPeaceful"),
    MENU_PEACEFUL_DISABLED_LORE("Menu.peaceful.disabled.lore", Collections.singletonList("&7Click to enable.")),
    MENU_PREVIOUS_TITLE("Menu.previous.title", "&fPrevious"),
    MENU_PREVIOUS_LORE("Menu.previous.lore", Collections.singletonList("&7Click for previous page.")),
    NO_COMMAND_PERMISSION("Messages.no-command-permission", "You do not have permissions for that command."),
    NO_PERMISSION("Messages.no-permission", "You do not have permissions for &b{0}&7."),
    PAGE_FORMAT("Messages.page-format", "&b{0}. &7{1}"),
    PAGE_HELP("Messages.page-help", "&e&lHelp: &7[{0}/{1}]"),
    PAGE_LIMIT("Messages.page-limit", "There are only &b{0} &7help pages."),
    PAGE_NEXT("Messages.page-next", "Type &b/villages help {0} &7for the next page."),
    PAGE_PREVIOUS("Messages.page-previous", "Type &b/villages help {0} &7for the previous page."),
    PLAYER_OFFLINE("Messages.player-offline", "The player &b{0} &7does not seem to be online."),
    PREFIX("Messages.prefix", "&e&lVillages: &7"),
    REQUEST_ACCEPT("Messages.request-accept", "&a[Accept]"),
    REQUEST_DENY("Messages.request-deny", "&c[Deny]"),
    REQUEST_DENIED("Messages.request-denied", "You have decided to deny the request."),
    REQUEST_DISBAND("Messages.request-disband", "Are you sure you want to disband the village? Members of your village may not be happy!"),
    REQUEST_INVITE("Messages.request-invite", "You have sent an invite request to &b{0}&7."),
    REQUEST_INVITE_SELF("Messages.request-invite-self", "You can't invite yourself."),
    REQUEST_INVITE_TARGET("Messages.request-invite-target", "You have been invited to join &b{0}&7. Do you wish to join?"),
    REQUEST_INVITE_TARGET_NOT_NULL("Messages.request-invite-target-not-null", "The player &b{0} &7already belongs to a village."),
    REQUEST_JOIN("Messages.request-join", "&b{0} &7has joined the village."),
    REQUEST_JOIN_TARGET("Messages.request-join-target", "&7You have joined the &b{0} &7village."),
    REQUEST_KICK("Messages.request-kick", "Are you sure you want to kick &b{0} &7 from your village?"),
    REQUEST_KICK_SELF("Messages.request-kick-self", "You can't kick yourself from the village."),
    REQUEST_KICK_TARGET("Messages.request-kick-target", "You have been kicked from the &b{0} &7village."),
    REQUEST_NULL("Messages.request-null", "You do not have any pending requests."),
    REQUEST_PENDING("Messages.request-pending", "You already have a pending request."),
    TITLE_HEADER("Title.header", "&e&l{0}!"),
    TITLE_FOOTER("Title.footer", "&7Welcome to &b{0}'s &7village."),
    TITLE_WILDERNESS_HEADER("Title.wilderness-header", "&a&lWilderness!"),
    TITLE_WILDERNESS_FOOTER("Title.wilderness-footer", "&7Fresh new land awaits you."),
    TOOLTIP("Messages.tooltip", "&7Click to select."),
    USAGE("Messages.usage", "Usage: &b{0}"),
    VILLAGE_ADMIN_ENABLED("Messages.village-admin-enabled", "Admin mode: &aenabled"),
    VILLAGE_ADMIN_NULL("Messages.village-admin-null", "No village exists at your current location."),
    VILLAGE_ADMIN_UNCLAIM("Messages.village-admin-unclaim", "Successfully unclaimed land at &b{0} &7from &b{1}&7."),
    VILLAGE_ADMIN_DISABLED("Messages.village-admin-disabled", "Admin mode: &cdisabled"),
    VILLAGE_ALREADY_OWNER("Messages.village-already-owner", "You can't set the new owner to yourself."),
    VILLAGE_CLAIM("Messages.village-claim", "You have claimed new land for your village: &b{0}"),
    VILLAGE_CLAIM_OWNED("Messages.village-claim-owned", "The land you are trying to claim already belongs to your village."),
    VILLAGE_CLAIM_OTHER("Messages.village-claim-other", "The land you are trying to claim belongs to another village."),
    VILLAGE_CLAIM_TELEPORT("Messages.village-teleport", "You have been teleported to claim: &b{0}&7"),
    VILLAGE_COOLDOWN("Messages.village-cooldown", "You can teleport home again in &b{0} &7seconds."),
    VILLAGE_CREATE("Messages.village-create", "You have established a new village named &b{0}&7."),
    VILLAGE_CREATE_LIMIT("Messages.village-create", "You have established a new village named &b{0}&7."),
    VILLAGE_CREATE_OTHER("Messages.village-create-other", "The name of your village can't be longer than &b32 &7characters."),
    VILLAGE_DESCRIPTION_LIMIT("Messages.village-description-limit", "The description of your village can't be longer than &b32&7 characters."),
    VILLAGE_EXISTS("Messages.village-exists", "A village named &b{0} &7has already been established."),
    VILLAGE_HOME("Messages.village-home", "You have been teleported to your village home."),
    VILLAGE_LEAVE("Messages.village-leave", "You have left the &b{0} &7village."),
    VILLAGE_LEAVE_OWNER("Messages.village-leave-owner", "You can't leave your current village because you are the owner."),
    VILLAGE_MAX_CLAIMS("Messages.village-max-claims", "You can't claim more than &b{0} &7land for you village."),
    VILLAGE_MEMBER_NULL("Messages.village-member-null", "&b{0} &7does not seem to belong to your village."),
    VILLAGE_NOT_NULL("Messages.village-not-null", "You already belong to a village."),
    VILLAGE_NULL("Messages.village-null", "You do not belong to a village."),
    VILLAGE_OWNER("Messages.village-owner", "You must be the owner of the village to do that action."),
    VILLAGE_RENAME("Messages.village-rename", "You have renamed the village to: &b{0}"),
    VILLAGE_SET_DESCRIPTION("Messages.village-set-description", "You have set the village description to: &b{0}"),
    VILLAGE_SET_HOME("Messages.village-set-home", "You have set the home for your village."),
    VILLAGE_SET_OWNER("Messages.village-set-owner", "You have set the new village owner to &b{0}&7."),
    VILLAGE_UNCLAIM("Messages.village-unclaim", "You have unclaimed land for your village."),
    VILLAGE_UNCLAIM_ONE("Messages.village-unclaim-one", "You can't unclaim because your village only owns one land."),
    VILLAGE_UNCLAIM_OTHER("Messages.village-unclaim-other", "The land you are trying to unclaim does not belong to your village."),
    WORLDGUARD_CLAIM("Messages.world-guard", "You can't claim in a worldguard region."),
    WORLDGUARD_CREATE("Messages.world-guard", "You create land in a worldguard region."),
    WORLD_NOT_ENABLED("Messages.world-not-enabled", "Villages is not enabled in this world.");

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

    public String getDef() {
        return configuration.getString(path, def);
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

    public List<String> getList() {
        return list;
    }
}
