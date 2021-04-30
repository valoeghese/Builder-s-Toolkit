package valoeghese.toolkit.world.item;

import javax.annotation.Nullable;

import io.github.minecraftcursedlegacy.api.attacheddata.v1.AttachedData;
import io.github.minecraftcursedlegacy.api.event.ActionResult;
import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.entity.player.Player;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Vec3i;
import valoeghese.toolkit.Toolkit;

public class LevelEditData implements AttachedData {
	public LevelEditData(LevelEditData original) {
		this.start = new Vec3i(original.start.x, original.start.y, original.end.z);
		this.end = new Vec3i(original.end.x, original.end.y, original.end.z);
		this.isWand = original.isWand;
	}

	public LevelEditData() {
	}

	private boolean isWand = false;
	private Vec3i start = OUT_OF_WORLD;
	private Vec3i end = OUT_OF_WORLD;

	@Nullable
	public Vec3i getStart() {
		return start.equals(OUT_OF_WORLD) ? null : start;
	}

	@Nullable
	public Vec3i getEnd() {
		return end.equals(OUT_OF_WORLD) ? null : end;
	}

	public void setWand() {
		this.isWand = true;
	}

	public boolean isWand() {
		return this.isWand;
	}

	public ActionResult interact(Player player, @Nullable Vec3i pos) {
		if (this.isWand) {
			if (pos == null) {
				start = OUT_OF_WORLD;
				end = OUT_OF_WORLD;
				Toolkit.sidedProxy.sendChatMessage(player, "Cleared wand positions " + pos);
			} else if (!start.equals(OUT_OF_WORLD) && end.equals(OUT_OF_WORLD)) {
				end = pos;
				Toolkit.sidedProxy.sendChatMessage(player, "Set end pos of this wand to " + pos);
			} else {
				// Just in case
				end = OUT_OF_WORLD;
				start = pos;
				Toolkit.sidedProxy.sendChatMessage(player, "Set start pos of this wand to " + pos);
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

	private static Vec3i posFromTag(CompoundTag tag) {
		return new Vec3i(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
	}

	private static CompoundTag posToTag(Vec3i pos) {
		CompoundTag result = new CompoundTag();
		result.put("x", pos.x);
		result.put("y", pos.y);
		result.put("z", pos.z);
		return result;
	}

	// Vec3i supports a reasonable amount
	private static final Vec3i OUT_OF_WORLD = new Vec3i(0, 365, 0);
	public static final Id ID = Toolkit.id("leveledit");
}
