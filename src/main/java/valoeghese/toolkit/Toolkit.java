package valoeghese.toolkit;

import java.io.IOException;

import io.github.minecraftcursedlegacy.api.config.Configs;
import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import io.github.minecraftcursedlegacy.api.registry.Translations;
import io.github.minecraftcursedlegacy.api.worldtype.WorldType;
import net.fabricmc.api.ModInitializer;
import net.minecraft.tile.Tile;
import tk.valoeghese.zoesteriaconfig.api.container.WritableConfig;
import tk.valoeghese.zoesteriaconfig.api.deserialiser.Comment;
import tk.valoeghese.zoesteriaconfig.api.template.ConfigTemplate;
import valoeghese.toolkit.world.SuperflatWorldType;

public class Toolkit implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Initialising Builder's Toolkit");

		// example config
		try {
			superflatConfig = Configs.loadOrCreate(id("superflat"),
					ConfigTemplate.builder()
					.addComment(new Comment("The list of block ids, descending, for the superflat surface."))
					.addList("layers", l -> {
						l.add(Registries.TILE.getId(Tile.GRASS).toString());
						l.add(Registries.TILE.getId(Tile.DIRT).toString());
						l.add(Registries.TILE.getId(Tile.DIRT).toString());
						l.add(Registries.TILE.getId(Tile.DIRT).toString());
						l.add(Registries.TILE.getId(Tile.BEDROCK).toString());
					})
					.addContainer("climate", c -> c.addDataEntry("temperature", 0.8).addDataEntry("rainfall", 0.4))
					.build());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		superflat = new SuperflatWorldType(id("superflat"));
		Translations.addTranslation("generator.toolkit.superflat", "Superflat");
		// TODO in ZoesteriaConfig 1.3.7 catch NPE and redirect to a new NullPointer or some other exception giving a detailed explanation of what key is null.
	}

	public static Id id(String name) {
		return new Id("toolkit", name);
	}

	public static WorldType superflat;
	public static WritableConfig superflatConfig;
}
