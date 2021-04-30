package valoeghese.toolkit.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.util.maths.Vec3i;
import valoeghese.toolkit.Toolkit;
import valoeghese.toolkit.world.item.LevelEditData;

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

		register("fill", (player, args) -> {
			LevelEditData led = null;
			ItemInstance wandStack = player.inventory.getHeldItem();

			if (wandStack.getType() == ItemType.hatchetWood) {
				led = Toolkit.getLevelEditData(wandStack);

				if (!led.isWand()) {
					led = null;
				}
			}

			if (led == null) {
				Toolkit.sidedProxy.sendChatMessage(player, "No wand currently selected!");
				return false;
			}

			// Get tile
			// string tile
			int tileToSet = 1;

			if (args.length > 1) {
				String stile = args[1];
				try {
					tileToSet = Integer.parseInt(stile);
				} catch (NumberFormatException e) {
					tileToSet = Registries.TILE.getById(new Id(stile)).id;
				}
			}

			// Get bounds
			Vec3i start = led.getStart();
			Vec3i end = led.getEnd();

			int x0 = start.x;
			int x1 = end.x;

			if (x0 > x1) {
				int xt = x0; // temp
				x0 = x1;
				x1 = xt;
			}

			int y0 = start.y;
			int y1 = end.y;

			if (y0 > y1) {
				int yt = y0; // temp
				y0 = y1;
				y1 = yt;
			}

			int z0 = start.z;
			int z1 = end.z;

			if (z0 > z1) {
				int zt = z0; // temp
				z0 = z1;
				z1 = zt;
			}

			if (x1 - x0 > 128 || z1 - z0 > 128) {
				Toolkit.sidedProxy.sendChatMessage(player, "Fill region too large!");
				return false;
			}

			// Fill
			for (int x = x0; x < x1; ++x) {
				for (int y = y0; y < y1; ++y) {
					for (int z = z0; z < z1; ++z) {
						player.level.setTile(x, y, z, tileToSet);
					}
				}
			}

			return true;
		});
	}
}
