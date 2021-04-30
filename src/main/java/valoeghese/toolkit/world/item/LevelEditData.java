package valoeghese.toolkit.world.item;

import javax.annotation.Nullable;

import io.github.minecraftcursedlegacy.api.attacheddata.v1.AttachedData;
import io.github.minecraftcursedlegacy.api.event.ActionResult;
import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.entity.player.Player;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.TilePos;
import valoeghese.toolkit.Toolkit;

public class LevelEditData implements AttachedData {
	public LevelEditData(LevelEditData original) {
		this.start = new TilePos(original.start.x, original.start.y, original.end.z);
		this.end = new TilePos(original.end.x, original.end.y, original.end.z);
	}

	public LevelEditData() {
	}

	private boolean isWand = false;
	private TilePos start = OUT_OF_WORLD;
	private TilePos end = OUT_OF_WORLD;

	@Nullable
	public TilePos getStart() {
		return start.equals(OUT_OF_WORLD) ? null : start;
	}

	@Nullable
	public TilePos getEnd() {
		return end.equals(OUT_OF_WORLD) ? null : end;
	}

	public void setWand() {
		this.isWand = true;
	}

	public ActionResult interact(Player player, @Nullable TilePos pos) {
		if (this.isWand) {
			if (pos == null) {
				start = OUT_OF_WORLD;
				end = OUT_OF_WORLD;
			} else if (!start.equals(OUT_OF_WORLD) && end.equals(OUT_OF_WORLD)) {
				end = pos;
			} else {
				// Just in case
				end = OUT_OF_WORLD;
				start = pos;
				
			}

			return ActionResult.SUCCESS;
		} else {
			return ActionResult.PASS;
		}
	}

	@Override
	public Id getId() {
		return ID;
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		tag.put("wand", this.isWand);

		if (this.isWand) {
			tag.put("start", posToTag(this.start));
			tag.put("end", posToTag(this.end));
		}
		return tag;
	}

	@Override
	public void fromTag(CompoundTag tag) {
		this.isWand = tag.getBoolean("wand");

		if (this.isWand) {
			this.start = posFromTag(tag.getCompoundTag("start"));
			this.end = posFromTag(tag.getCompoundTag("end"));
		}
	}

	@Override
	public AttachedData copy() {
		return new LevelEditData(this);
	}

	private static TilePos posFromTag(CompoundTag tag) {
		return new TilePos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
	}

	private static CompoundTag posToTag(TilePos pos) {
		CompoundTag result = new CompoundTag();
		result.put("x", pos.x);
		result.put("y", pos.y);
		result.put("z", pos.z);
		return result;
	}

	// TilePos supports a reasonable amount
	private static final TilePos OUT_OF_WORLD = new TilePos(0, 365, 0);
	public static final Id ID = Toolkit.id("leveledit");
}
