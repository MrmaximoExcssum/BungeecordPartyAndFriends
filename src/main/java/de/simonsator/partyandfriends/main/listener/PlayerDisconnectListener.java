/**
 * The class with the PlayerDisconnectEvent event.
 *
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.main.listener;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.party.playerpartys.PlayerParty;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getPlayerManager;
import static de.simonsator.partyandfriends.utilities.CompilePatter.PLAYERPATTERN;

/**
 * The class with the PlayerDisconnectEvent event.
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class PlayerDisconnectListener implements Listener {

	/**
	 * Will be executed on player disconnect
	 *
	 * @param pEvent The disconnect event
	 */
	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent pEvent) {
		OnlinePAFPlayer player = getPlayerManager().getPlayer(pEvent.getPlayer());
		PlayerParty party = Main.getPartyManager().getParty(player);
		if (party != null) {
			party.leaveParty(player);
		}
		for (PAFPlayer friend : player.getFriends()) {
			friend.sendMessage(
					new TextComponent(
							Main.getInstance().getFriendsPrefix() + PLAYERPATTERN
									.matcher(Main.getInstance().getMessagesYml()
											.getString("Friends.General.PlayerIsNowOffline"))
									.replaceAll(Matcher.quoteReplacement(player.getDisplayName()))));
		}
	}
}