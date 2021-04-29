package valoeghese.toolkit.world;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.worldtype.WorldType;
import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.gen.BiomeSource;
import net.minecraft.level.gen.FixedBiomeSource;
import net.minecraft.level.source.LevelSource;
import net.minecraft.util.io.CompoundTag;
import valoeghese.toolkit.Toolkit;

public class SuperflatWorldType extends WorldType {
	public SuperflatWorldType(Id id) {
		super(id);
	}

	@Override
	public LevelSource createChunkGenerator(Level level, CompoundTag additionalData) {
		return new FlatChunkGenerator(level);
	}

	@Override
	public BiomeSource createBiomeSource(Level level, CompoundTag additionalData) {
		return new FixedBiomeSource(Biome.PLAINS, Toolkit.superflatConfig.getDoubleValue("temperature"), Toolkit.superflatConfig.getDoubleValue("rainfall"));
	}
}
