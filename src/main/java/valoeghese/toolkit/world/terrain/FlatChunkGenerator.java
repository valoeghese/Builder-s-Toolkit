package valoeghese.toolkit.world.terrain;


import java.util.List;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import io.github.minecraftcursedlegacy.api.terrain.ChunkGenerator;
import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.source.LevelSource;
import valoeghese.toolkit.core.Configurations;

public class FlatChunkGenerator extends ChunkGenerator {
	public FlatChunkGenerator(Level level, long seed) {
		super(level, seed);

		List<Object> layers = Configurations.superflatConfig.getList("layers");
		this.layers = new byte[layers.size()];

		for (int i = 0; i < layers.size(); ++i) {
			this.layers[i] = (byte) Registries.TILE.getById(new Id(layers.get(i).toString())).id;
		}
	}

	private final byte[] layers;

	@Override
	protected void shapeChunk(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes) {
		for (int i = 0; i < this.layers.length; ++i) {
			int y = this.layers.length - 1 - i;

			for (int xo = 0; xo < 16; ++xo) {
				for (int zo = 0; zo < 16; ++zo) {
					tiles[getIndex(xo, y, zo)] = this.layers[i];
				}
			}
		}
	}

	@Override
	protected void buildSurface(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes) {
		// No-Op
	}

	@Override
	public void decorate(LevelSource levelSource, int chunkX, int chunkZ) {
		// No-Op
	}

	// Always can spawn
	@Override
	public boolean isValidSpawnPos(int x, int z) {
		this.level.getProperties().setSpawnY(this.layers.length);
		return true;
	}

	@Override
	public int getMinSpawnY() {
		return this.layers.length - 1;
	}
}
