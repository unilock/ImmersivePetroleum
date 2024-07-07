package flaxbeard.immersivepetroleum.common.data.recipes.builder;

import java.util.Objects;

import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.data.recipes.builder.IERecipeBuilder;
import flaxbeard.immersivepetroleum.api.crafting.CokerUnitRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class CokerUnitRecipeBuilder extends IERecipeBuilder<CokerUnitRecipeBuilder> {
	private IngredientWithSize inputItem;
	private FluidTagInput inputFluid;
	private ItemStack outputItem;
	private FluidStack outputFluid;
	private int energy = 1024;
	private int time = 1;
	
	public static CokerUnitRecipeBuilder builder(ItemStack output, Fluid fluid, int amount){
		Objects.requireNonNull(output);
		if(output.isEmpty())
			throw new IllegalArgumentException("Input stack cannot be empty.");
		
		return new CokerUnitRecipeBuilder()
				.addOutputItem(output)
				.addOutputFluid(fluid, amount);
	}
	
	private CokerUnitRecipeBuilder(){
		super();
	}
	
	public CokerUnitRecipeBuilder addInputItem(TagKey<Item> item, int amount){
		this.inputItem = new IngredientWithSize(item, amount);
		return this;
	}
	
	public CokerUnitRecipeBuilder addInputFluid(TagKey<Fluid> fluidTag, int amount){
		this.inputFluid = new FluidTagInput(fluidTag, amount);
		return this;
	}
	
	public CokerUnitRecipeBuilder addOutputItem(ItemStack item){
		this.outputItem = item;
		return this;
	}
	
	public CokerUnitRecipeBuilder addOutputFluid(Fluid fluid, int amount){
		this.outputFluid = new FluidStack(fluid, amount);
		return this;
	}
	
	public CokerUnitRecipeBuilder setTimeAndEnergy(int time, int energy){
		this.time = time;
		this.energy = energy;
		return this;
	}
	
	public void build(RecipeOutput out, ResourceLocation name){
		CokerUnitRecipe recipe = new CokerUnitRecipe(this.outputItem, this.outputFluid, this.inputItem, this.inputFluid, this.energy, this.time);
		out.accept(name, recipe, null, this.getConditions());
	}
}
