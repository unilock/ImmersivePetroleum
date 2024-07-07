package flaxbeard.immersivepetroleum.common.data.recipes.builder;

import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import blusunrize.immersiveengineering.data.recipes.builder.IERecipeBuilder;
import flaxbeard.immersivepetroleum.api.crafting.HighPressureRefineryRecipe;
import flaxbeard.immersivepetroleum.common.util.ChancedItemStack;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class HighPressureRefineryRecipeBuilder extends IERecipeBuilder<HighPressureRefineryRecipeBuilder> {
	private FluidStack outputFluid;
	private ChancedItemStack outputItem;
	private FluidTagInput inputFluid;
	@Nullable
	private FluidTagInput inputFluidSecondary;
	private int energy = 1024;
	private int time = 1;
	
	public static HighPressureRefineryRecipeBuilder builder(FluidStack fluidOutput, int energy, int time){
		return new HighPressureRefineryRecipeBuilder()
				.setTimeAndEnergy(time, energy)
				.addResultFluid(fluidOutput);
	}
	
	protected HighPressureRefineryRecipeBuilder(){
		super();
	}
	
	public HighPressureRefineryRecipeBuilder addResultFluid(FluidStack fluid){
		this.outputFluid = fluid;
		return this;
	}
	
	public HighPressureRefineryRecipeBuilder addInputFluid(FluidTagInput fluid){
		this.inputFluid = fluid;
		return this;
	}
	
	public HighPressureRefineryRecipeBuilder addInputFluid(TagKey<Fluid> fluid, int amount){
		this.inputFluid = new FluidTagInput(fluid, amount);
		return this;
	}
	
	/** Optionaly add a second fluid to be pumped in */
	public HighPressureRefineryRecipeBuilder addSecondaryInputFluid(FluidTagInput fluid){
		this.inputFluidSecondary = fluid;
		return this;
	}
	
	/** Optionaly add a second fluid to be pumped in */
	public HighPressureRefineryRecipeBuilder addSecondaryInputFluid(TagKey<Fluid> fluid, int amount){
		this.inputFluidSecondary = new FluidTagInput(fluid, amount);
		return this;
	}
	
	/** Optional */
	public HighPressureRefineryRecipeBuilder addItemWithChance(ItemStack item, double chance){
		this.outputItem = new ChancedItemStack(item, chance);
		return this;
	}
	
	protected HighPressureRefineryRecipeBuilder setTimeAndEnergy(int time, int energy){
		this.time = time;
		this.energy = energy;
		return this;
	}
	
	public void build(RecipeOutput out, ResourceLocation name){
		HighPressureRefineryRecipe recipe = new HighPressureRefineryRecipe(this.outputFluid, this.outputItem, this.inputFluid, this.inputFluidSecondary, this.energy, this.time);
		out.accept(name, recipe, null, this.getConditions());
	}
}
