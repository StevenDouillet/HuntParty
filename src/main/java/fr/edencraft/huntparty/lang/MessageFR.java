package fr.edencraft.huntparty.lang;

import org.bukkit.ChatColor;

public class MessageFR {
    public static String prefix = ChatColor.YELLOW + "HuntParty" + ChatColor.WHITE + ChatColor.BOLD + " » " + ChatColor.RESET;

    // Info messages
    public static String huntPartyVersion = prefix + ChatColor.WHITE + "Version du plugin : {Version}";

    // Error messages
    public static String huntNameAlreadyExist = prefix + ChatColor.RED + "Ce nom est déjà utilisé";
    public static String huntDoesntExist = prefix + ChatColor.RED + "Ce nom n'existe pas";
    public static String huntIDDoesntExist = prefix + ChatColor.RED + "Cet id n'existe pas";

    // Success messages
    public static String huntCreated = prefix + ChatColor.YELLOW + "{Hunt}" + ChatColor.WHITE + " a été crée avec succès";
    public static String huntHasBeenDelete = prefix + ChatColor.YELLOW + "{Hunt}" + ChatColor.WHITE + " a été supprimée avec succès";
    public static String treasureHuntSet = prefix + "Point sauvegardé pour {Hunt}";
}
