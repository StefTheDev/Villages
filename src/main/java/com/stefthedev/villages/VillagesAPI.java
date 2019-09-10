package com.stefthedev.villages;

import java.util.Optional;
import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageMember;
import com.stefthedev.villages.villages.VillagePermission;

/**
 * This is a public API for the Spigot Plugin Villages.
 * It contains some methods that may be useful for external Plugins.
 * 
 * @author TheBusyBiscuit
 *
 */
public final class VillagesAPI {
	
	private VillagesAPI() {
		// We don't want anyone to create an instance of this Class
	}
	
	/**
	 * This method returns the current instance of our Villages Plugin's main class
	 * 
	 * @return The plugin instance
	 */
	private static Villages getPlugin() {
		return (Villages) JavaPlugin.getProvidingPlugin(VillagesAPI.class);
	}
	
	/**
	 * This method returns an Optional<Village> for the specified Chunk.
	 * If no Village was found, it will return <code>Optional.empty()</code>
	 * 
	 * @param chunk	The chunk that is being queried
	 * @return		An Optional<Village> for the possibly found Village
	 */
	public static Optional<Village> getVillage(Chunk chunk) {
		if (chunk == null) return Optional.empty();
		
		return Optional.ofNullable(getPlugin().getVillageManager().getVillage(chunk));
	}
	
	/**
	 * This method returns an Optional<Village> for the specified Player.
	 * If no Village was found, it will return <code>Optional.empty()</code>
	 * 
	 * @param p		The Player that is being queried
	 * @return		An Optional<Village> for the possibly found Village
	 */
	public static Optional<Village> getVillage(Player p) {
		if (p == null) return Optional.empty();
		
		return getVillage(p.getUniqueId());
	}
	
	/**
	 * This method returns an Optional<Village> for the specified UUID.
	 * If no Village was found, it will return <code>Optional.empty()</code>
	 * 
	 * @param uuid	The UUID that is being queried
	 * @return		An Optional<Village> for the possibly found Village
	 */
	public static Optional<Village> getVillage(UUID uuid) {
		if (uuid == null) return Optional.empty();
		
		return Optional.ofNullable(getPlugin().getVillageManager().getVillage(uuid));
	}
	
	/**
	 * This method returns an Optional<Village> for the specified Player.
	 * If no Village was found, it will return <code>Optional.empty()</code>
	 * 
	 * @param name	The Name of your Village
	 * @return		An Optional<Village> for the possibly found Village
	 */
	public static Optional<Village> getVillage(String name) {
		if (name == null) return Optional.empty();
		
		return Optional.ofNullable(getPlugin().getVillageManager().getVillage(name));
	}
	
	/**
	 * This method returns whether the specified UUID has the specified Permission
	 * in the specified Village.
	 * 
	 * It will return true if village is null.
	 * It will return false if village is not null and the Player has no village assigned to them.
	 * It will return false if the specified village does not equal the village of the player.
	 * It will return true if the Player is the owner of that village.
	 * Otherwise it will call {@link VillageMember#hasPermission(VillagePermission)}
	 * 
	 * @param uuid			The UUID of the Player you are querying for
	 * @param village		The Village for which you want the permission to be checked
	 * @param permission	The Permission that you want to query
	 * @return				Whether the specified UUID has the specified permission in the specified village
	 */
	public static boolean hasPermission(UUID uuid, Village village, VillagePermission permission) {
		if (uuid == null || permission == null) return false;
		if (village == null) return true;
		
		Optional<Village> optional = getVillage(uuid);
		if (!optional.isPresent()) return false;
		
		Village pVillage = optional.get();
		
		if (pVillage == village) {
			if (village.getOwner().equals(uuid)) return true;
			else return village.getMember(uuid).hasPermission(permission);
		}
		
		return false;
	}

}
