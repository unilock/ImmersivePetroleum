package flaxbeard.immersivepetroleum.common.items;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.annotation.Nonnull;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

/**
 * @author TwistedGate
 */
public class SurveyResultItem extends IPItemBase{
	public SurveyResultItem(){
		super(new Item.Properties().stacksTo(1));
	}
	
	@Override
	@Nonnull
	public Component getName(@Nonnull ItemStack stack){
		String selfKey = getDescriptionId(stack);
		return new TranslatableComponent(selfKey).withStyle(ChatFormatting.GOLD);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn){
		if(stack.hasTag() && stack.getTag() != null){
			boolean hasSurveyScan = stack.getTag().contains("surveyscan");
			boolean hasIslandScan = stack.getTag().contains("islandscan");
			
			if(hasSurveyScan){
				CompoundTag tag = stack.getTagElement("surveyscan");
				
				tooltip.add(new TextComponent("Hold in Hand."));
				
				if(flagIn == TooltipFlag.Default.ADVANCED){
					UUID uuid = tag.hasUUID("uuid") ? tag.getUUID("uuid") : null;
					byte[] mapData = tag.getByteArray("map");
					
					tooltip.add(new TextComponent("ID: " + (uuid != null ? uuid.toString() : "Null")));
					tooltip.add(new TextComponent("dSize: " + (mapData != null ? mapData.length : "Null")));
				}
			}
			
			if(hasIslandScan){
				CompoundTag tag = stack.getTagElement("islandscan");
				int expected = tag.getInt("expected");
				long amount = tag.getLong("amount");
				byte percentage = tag.getByte("status");
				String fluidTranslation = tag.getString("fluid");
				
				tooltip.add(new TranslatableComponent(fluidTranslation).withStyle(ChatFormatting.DARK_GRAY));
				tooltip.add(new TranslatableComponent("desc.immersivepetroleum.info.survey_result.amount", String.format(Locale.ENGLISH, "%,.3f", amount / 1000D), percentage).withStyle(ChatFormatting.DARK_GRAY));
				tooltip.add(new TranslatableComponent("desc.immersivepetroleum.info.survey_result.expected", expected).withStyle(ChatFormatting.DARK_GRAY));
			}
			
			if(hasSurveyScan || hasIslandScan){
				CompoundTag tag;
				if((tag = stack.getTagElement("surveyscan")) == null){
					tag = stack.getTagElement("islandscan");
				}
				
				if(tag != null){
					int x = tag.getInt("x");
					int z = tag.getInt("z");
					
					tooltip.add(new TranslatableComponent("desc.immersivepetroleum.flavour.surveytool.location", x, z).withStyle(ChatFormatting.DARK_GRAY));
				}
			}
		}
	}
}
