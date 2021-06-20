package net.azagwen.atbyw.mixin;

import net.azagwen.atbyw.main.DataResourceListener;
import net.azagwen.atbyw.main.ItemOperations;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public class AxeItemMixin {

    //TODO: read https://fabricmc.net/wiki/tutorial:custom_resources

    @Inject(at = @At("HEAD"), method = "useOnBlock", cancellable = true)
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        var world = context.getWorld();
        var player = context.getPlayer();
        var pos = context.getBlockPos();
        var direction = context.getSide();
        var heldItem = context.getStack();

        var replaceMap = DataResourceListener.AXE_REPLACE;

        if (replaceMap.containsKey(world.getBlockState(pos).getBlock())) {
            ItemOperations.replaceBlock(world, player, pos, direction, SoundEvents.ITEM_AXE_STRIP, replaceMap);

            if (player != null) {
                heldItem.damage(1, player, ((entity) -> {
                    entity.sendToolBreakStatus(context.getHand());
                }));
            }

            cir.setReturnValue(ActionResult.success(world.isClient));
        }
    }
}