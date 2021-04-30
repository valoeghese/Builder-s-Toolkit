package valoeghese.toolkit.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.AbstractClientPlayer;

@Mixin(AbstractClientPlayer.class)
public interface AccessorAbstractClientPlayer {
	@Accessor("minecraft")
	Minecraft getMinecraft();
}
