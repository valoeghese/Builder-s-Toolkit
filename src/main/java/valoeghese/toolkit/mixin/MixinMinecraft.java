package valoeghese.toolkit.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.Minecraft;
import valoeghese.toolkit.common.Commands;

@Mixin(Minecraft.class)
public class MixinMinecraft {
	@Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;hasLevel()Z", ordinal = 0))
	private boolean canOpenChatScreen(Minecraft minecraft) {
		return ((Minecraft) (Object) this).level != null;
	}
	
	@Inject(at = @At("HEAD"), method = "method_2124", cancellable = true)
	private void method_2124(String string, CallbackInfoReturnable<Boolean> info) {
		if (string.length() > 2 && string.startsWith("//")) {
			String[] args = string.substring(2).split(" ");
			Commands.handle(((Minecraft) (Object) this).player, args);
			info.setReturnValue(true);
		}
	}
}
