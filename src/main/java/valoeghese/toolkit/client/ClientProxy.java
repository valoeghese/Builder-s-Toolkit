package valoeghese.toolkit.client;

import net.minecraft.entity.player.AbstractClientPlayer;
import net.minecraft.entity.player.Player;
import valoeghese.toolkit.common.Proxy;

public class ClientProxy implements Proxy {

	@Override
	public void sendChatMessage(Player player, String message) {
		// this should only be used to send local chat messages on client
		AbstractClientPlayer aplayer = (AbstractClientPlayer) player;
		aplayer.sendChatMessage(message);
	}
	
}
