package flaxbeard.immersivepetroleum.common.data.loot;

import java.util.List;
import java.util.Map;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootDataId;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class IPLootGenerator extends LootTableProvider{
	public IPLootGenerator(PackOutput output) {
		super(output, null, List.of(
				new SubProviderEntry(IPLoot::new, LootContextParamSets.EMPTY),
				new SubProviderEntry(IPBlockLoot::new, LootContextParamSets.BLOCK)
		));
	}
	
	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationcontext) {
		map.forEach((p_278897_, p_278898_) -> {
			p_278898_.validate(validationcontext.setParams(p_278898_.getParamSet()).enterElement("{" + p_278897_ + "}", new LootDataId(LootDataType.TABLE, p_278897_)));
		});
	}
}
