package moriyashiine.aylyth.common.item;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

import java.util.UUID;

public class YmpeDaggerItem extends SwordItem {
	private static final EntityAttributeModifier REACH_MODIFIER = new EntityAttributeModifier(UUID.fromString("ccec5f1b-1597-4994-aa5f-c8848721897d"), "Weapon modifier", -0.5, EntityAttributeModifier.Operation.ADDITION);
	
	public YmpeDaggerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}
	
	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		Multimap<EntityAttribute, EntityAttributeModifier> map = LinkedHashMultimap.create(super.getAttributeModifiers(slot));
		if (slot == EquipmentSlot.MAINHAND) {
			map.put(ReachEntityAttributes.ATTACK_RANGE, REACH_MODIFIER);
		}
		return map;
	}
}
