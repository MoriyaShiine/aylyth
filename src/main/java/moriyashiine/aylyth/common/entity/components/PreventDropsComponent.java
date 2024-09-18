package moriyashiine.aylyth.common.entity.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;

public class PreventDropsComponent implements Component {
	private boolean preventsDrops = false;
	
	@Override
	public void readFromNbt(NbtCompound tag) {
		setPreventsDrops(tag.getBoolean("PreventsDrops"));
	}
	
	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putBoolean("PreventsDrops", getPreventsDrops());
	}
	
	public boolean getPreventsDrops() {
		return preventsDrops;
	}
	
	public void setPreventsDrops(boolean preventsDrops) {
		this.preventsDrops = preventsDrops;
	}
}
