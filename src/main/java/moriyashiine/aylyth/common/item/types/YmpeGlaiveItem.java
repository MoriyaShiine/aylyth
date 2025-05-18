package moriyashiine.aylyth.common.item.types;

import moriyashiine.aylyth.common.entity.AylythAttributes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class YmpeGlaiveItem extends SwordItem {
    public YmpeGlaiveItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        settings.attributeModifiers(
                AttributeModifiersComponent.builder()
                        .add(
                                EntityAttributes.ATTACK_DAMAGE,
                                new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, attackDamage + toolMaterial.attackDamageBonus(), EntityAttributeModifier.Operation.ADD_VALUE),
                                AttributeModifierSlot.MAINHAND
                        )
                        .add(
                                EntityAttributes.ATTACK_SPEED,
                                new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
                                AttributeModifierSlot.MAINHAND
                        )
                        .add(
                                EntityAttributes.BLOCK_INTERACTION_RANGE,
                                new EntityAttributeModifier(AylythAttributes.BASE_BLOCK_INTERACTION_RANGE, 1.2D, EntityAttributeModifier.Operation.ADD_VALUE),
                                AttributeModifierSlot.MAINHAND
                        )
                        .add(
                                EntityAttributes.ENTITY_INTERACTION_RANGE,
                                new EntityAttributeModifier(AylythAttributes.BASE_ENTITY_INTERACTION_RANGE, 1.2D, EntityAttributeModifier.Operation.ADD_VALUE),
                                AttributeModifierSlot.MAINHAND
                        )
                        .build()
        );
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.aylyth.glaive.desc_1").formatted(Formatting.GOLD, Formatting.ITALIC));
        tooltip.add(Text.translatable("item.aylyth.glaive.desc_2").formatted(Formatting.GOLD, Formatting.ITALIC));
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player && player.getEntityWorld() instanceof ServerWorld serverWorld) {
            player.spawnSweepAttackParticles();
            float attack = (float) attacker.getAttributeBaseValue(EntityAttributes.ATTACK_DAMAGE);
            target.damage(serverWorld, serverWorld.aylythDamageSources().soulRip(player), attack);
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!player.getItemCooldownManager().isCoolingDown(stack)) {
            float yaw = player.getYaw() * 0.017453292F;
            Vec3d pos = player.getPos().add(-MathHelper.sin(yaw) * 1.4D, player.getHeight() / 2D, MathHelper.cos(yaw) * 1.4D);
            List<LivingEntity> targets = player.getWorld().getEntitiesByClass(LivingEntity.class, Box.from(pos).offset(-0.5D, -0.5D, -0.5D).expand(3D, 1D, 3D), EntityPredicates.EXCEPT_SPECTATOR);
            stack.damage(1, player, hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

            float attack = (float) player.getAttributeBaseValue(EntityAttributes.ATTACK_DAMAGE);
            for (LivingEntity target : targets) {
                if (target != player && player.squaredDistanceTo(target) > 6.0 && player.squaredDistanceTo(target) < 36.0) {
                    if(!(target instanceof ArmorStandEntity)) {
                        target.takeKnockback(0.4D, MathHelper.sin(player.getYaw() * 0.0175F), -MathHelper.cos(player.getYaw() * 0.0175F));
                    }
                    if (world instanceof ServerWorld serverWorld) {
                        target.damage(serverWorld, world.aylythDamageSources().soulRip(player), attack);
                    }
                }
            }
            player.getWorld().playSoundFromEntity(null, player, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1F, 1F);
            spawnSweepAttackParticles(player);
            return ActionResult.SUCCESS;
        }
        return super.use(world, player, hand);

    }
    private void spawnSweepAttackParticles(PlayerEntity player) {
        if (player.getWorld() instanceof ServerWorld serverWorld) {
            for (int i = 0; i <= 6; i++) {
                double d = -MathHelper.sin((player.getYaw() + i*20 - 60) * MathHelper.RADIANS_PER_DEGREE) * 3;
                double e = MathHelper.cos((player.getYaw() + i*20 - 60) * MathHelper.RADIANS_PER_DEGREE) * 3;
                serverWorld.spawnParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d, player.getBodyY(0.5), player.getZ() + e, 0, d, 0.0, e, 0.0);
            }
        }
    }
}