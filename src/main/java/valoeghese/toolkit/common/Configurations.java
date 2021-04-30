package valoeghese.toolkit.common;

import java.io.IOException;

import io.github.minecraftcursedlegacy.api.config.Configs;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import net.minecraft.tile.Tile;
import tk.valoeghese.zoesteriaconfig.api.container.WritableConfig;
import tk.valoeghese.zoesteriaconfig.api.deserialiser.Comment;
import tk.valoeghese.zoesteriaconfig.api.template.ConfigTemplate;
import valoeghese.toolkit.Toolkit;

public class Configurations {
	public static void initConfigs() {
		// example config
		try {
			superflatConfig = Configs.loadOrCreate(Toolkit.id("superflat"),
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

		// TODO in ZoesteriaConfig 1.3.7 catch NPE and redirect to a new NullPointer or some other exception giving a detailed explanation of what key is null.
	}

	public static WritableConfig superflatConfig;
}
