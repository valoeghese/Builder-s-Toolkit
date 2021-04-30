package valoeghese.toolkit;

import io.github.minecraftcursedlegacy.api.attacheddata.v1.DataManager;
import io.github.minecraftcursedlegacy.api.attacheddata.v1.DataManager.DataKey;
import io.github.minecraftcursedlegacy.api.event.ActionResult;
import io.github.minecraftcursedlegacy.api.event.TileInteractionCallback;
import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Translations;
import io.github.minecraftcursedlegacy.api.worldtype.WorldType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.util.maths.Vec3i;
import valoeghese.toolkit.client.ClientProxy;
import valoeghese.toolkit.core.Commands;
import valoeghese.toolkit.core.Configurations;
import valoeghese.toolkit.core.Proxy;
import valoeghese.toolkit.server.ServerProxy;
import valoeghese.toolkit.world.item.LevelEditData;
import valoeghese.toolkit.world.terrain.SuperflatWorldType;

public class Toolkit implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Initialising Builder's Toolkit");

		sidedProxy = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ? new ClientProxy() : new ServerProxy();

		Configurations.initConfigs();
		superflat = new SuperflatWorldType(id("superflat"));
		Translations.addTranslation("generator.toolkit.superflat", "Superflat");

		Commands.initCommands();

		levelEditData = DataManager.ITEM_INSTANCE.addAttachedData(LevelEditData.ID, i -> new LevelEditData());

		TileInteractionCallback.EVENT.register((player, level, item, tile, x, y, z, face) -> {
			if (item.getType() == ItemType.hatchetWood) {
				return getLevelEditData(item).interact(player, new Vec3i(x, y, z));
			} else {
				return ActionResult.PASS;
			}
		});
		
	}

	private static DataKey<LevelEditData> levelEditData;

	public static Id id(String name) {
		return new Id("toolkit", name);
	}

	public static LevelEditData getLevelEditData(ItemInstance item) {
		return DataManager.ITEM_INSTANCE.getAttachedData(item, levelEditData);
	}

	public static WorldType superflat;
	public static Proxy sidedProxy;
}
