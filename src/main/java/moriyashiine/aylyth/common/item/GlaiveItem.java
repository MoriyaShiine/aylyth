package moriyashiine.aylyth.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import moriyashiine.aylyth.common.registry.ModDamageSources;
import moriyashiine.aylyth.common.registry.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class GlaiveItem extends SwordItem {
    private final float attackDamage;
    public final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public GlaiveItem(int attackDamage, float attackSpeed, Item.Settings settings) {
        super(ToolMaterials.NETHERITE, attackDamage, attackSpeed, settings);
        this.attackDamage = ToolMaterials.NETHERITE.getAttackDamage() + (float)attackDamage;
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", (double)this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", (double)attackSpeed, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ReachEntityAttributes.REACH, new EntityAttributeModifier("Attack range", 1.2, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier("Attack range", 1.2, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("It is the lament of the fallen").formatted(Formatting.GOLD, Formatting.ITALIC));
        tooltip.add(Text.literal("which pushes the living onward.").formatted(Formatting.GOLD, Formatting.ITALIC));
        super.appendTooltip(stack, world, tooltip, context);
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            player.spawnSweepAttackParticles();
            target.damage(ModDamageSources.SoulRipDamageSource.playerRip(player), this.attackDamage);
        }

        return super.postHit(stack, target, attacker);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!player.getItemCooldownManager().isCoolingDown(ModItems.GLAIVE)) {
            float yaw = player.getYaw() * 0.017453292F;
            Vec3d pos = player.getPos().add((double)(-MathHelper.sin(yaw)) * 1.4, (double)player.getHeight() / 2.0, (double)MathHelper.cos(yaw) * 1.4);
            List<LivingEntity> targets = player.world.getEntitiesByClass(LivingEntity.class, Box.from(pos).offset(-0.5, -0.5, -0.5).expand(3.0, 1.0, 3.0), EntityPredicates.EXCEPT_SPECTATOR);
            stack.damage(1, player, (entity) -> {
                entity.sendEquipmentBreakStatus(hand.equals(Hand.MAIN_HAND) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            });
            targets.forEach((target) -> {
                if (target != player && player.squaredDistanceTo(target) > 6.0 && player.squaredDistanceTo(target) < 36.0) {
                    if (!(target instanceof ArmorStandEntity)) {
                        target.takeKnockback(0.4, MathHelper.sin(player.getYaw() * 0.0175F), (double)(-MathHelper.cos(player.getYaw() * 0.0175F)));
                    }

                    target.damage(ModDamageSources.SoulRipDamageSource.playerRip(player), this.attackDamage);
                }

            });
            player.world.playSoundFromEntity(null, player, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
            player.swingHand(hand);
            this.spawnSweepAttackParticles(player);
            player.getItemCooldownManager().set(ModItems.GLAIVE, 35);
            return TypedActionResult.success(stack);
        } else {
            return super.use(world, player, hand);
        }
    }

    private void spawnSweepAttackParticles(PlayerEntity player) {
        if (player.world instanceof ServerWorld serverWorld) {
            for(int i = 0; i <= 6; ++i) {
                double d = (-MathHelper.sin((player.getYaw() + (float)(i * 20) - 60.0F) * 0.017453292F) * 3.0F);
                double e = (MathHelper.cos((player.getYaw() + (float)(i * 20) - 60.0F) * 0.017453292F) * 3.0F);
                serverWorld.spawnParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d, player.getBodyY(0.5), player.getZ() + e, 0, d, 0.0, e, 0.0);
            }
        }

    }
}