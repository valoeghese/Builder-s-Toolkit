package valoeghese.toolkit.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import valoeghese.toolkit.Toolkit;

/**
 * Simple commands API for use by toolkit.
 */
public class Commands {
	private static Map<String, BiPredicate<Player, String[]>> TOOLKIT_COMMANDS = new HashMap<>();

	public static boolean handle(Player player, String[] args) {
		return TOOLKIT_COMMANDS.get(args[0]).test(player, args);
	}

	public static void register(String commandName, BiPredicate<Player, String[]> commandFunction) {
		TOOLKIT_COMMANDS.put(commandName, commandFunction);
	}
	
	public static void initCommands() {
		register("wand", (player, args) -> {
			ItemInstance wandStack = new ItemInstance(ItemType.hatchetWood);
			Toolkit.getLevelEditData(wandStack).setWand();
			player.inventory.pickupItem(wandStack);
			return true;
		});
	}
}
