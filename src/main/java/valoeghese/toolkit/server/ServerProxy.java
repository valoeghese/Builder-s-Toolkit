package valoeghese.toolkit.server;

import net.minecraft.entity.player.Player;
import net.minecraft.entity.player.ServerPlayer;
import valoeghese.toolkit.common.Proxy;

public class ServerProxy implements Proxy {

	@Override
	public void sendChatMessage(Player player, String message) {
		//((ServerPlayer) player).field_261.
	}

}
