package games.enchanted.uniformfix.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.opengl.GlProgram;
import com.mojang.blaze3d.opengl.Uniform;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GlProgram.class)
public class GlProgramMixin {
	@Shadow @Final private int programId;

	@WrapOperation(
		at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/opengl/Uniform;setLocation(I)V", ordinal = 1),
		method = "setupUniforms"
	)
	private void eg_uniform_fix$replaceIndexWithLocationLookup(Uniform instance, int i, Operation<Void> original, @Local(ordinal = 0) String uniformName) {
		original.call(instance, Uniform.glGetUniformLocation(this.programId, uniformName));
	}
}