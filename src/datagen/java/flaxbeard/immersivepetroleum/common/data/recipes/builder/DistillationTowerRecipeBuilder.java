package flaxbeard.immersivepetroleum.common.data.recipes.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import blusunrize.immersiveengineering.data.recipes.builder.IERecipeBuilder;
import flaxbeard.immersivepetroleum.api.crafting.DistillationTowerRecipe;
import flaxbeard.immersivepetroleum.common.util.ChancedItemStack;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

/**
 * Distillation Recipe creation using DataGeneration
 * 
 * @author TwistedGate
 */
public class DistillationTowerRecipeBuilder extends IERecipeBuilder<DistillationTowerRecipeBuilder> {

	private final List<FluidStack> fluidOutput = new ArrayList<>();
	private final List<ChancedItemStack> byproducts = new ArrayList<>();
	private FluidTagInput input;
	private int energy = 1024;
	private int time = 1;
	
	public static DistillationTowerRecipeBuilder builder(FluidStack... fluidOutput){
		if(fluidOutput == null || fluidOutput.length == 0)
			throw new IllegalArgumentException("Fluid output missing. It's required.");
		
		DistillationTowerRecipeBuilder b = new DistillationTowerRecipeBuilder();
		b.addFluids(fluidOutput);
		return b;
	}
	
	private DistillationTowerRecipeBuilder(){
		super();
//		addWriter(jsonObject -> {
//			if(this.byproducts.size() > 0){
//				final JsonArray main = new JsonArray();
//				this.byproducts.forEach(by -> main.add(serializerItemStackWithChance(by)));
//				jsonObject.add("byproducts", main);
//				this.byproducts.clear();
//			}
//		});
	}
	
	/**
	 * Can be called multiple times to add more byproducts to the recipe
	 * 
	 * @param byproduct the {@link ItemStack} byproduct to add to the recipe
	 * @param chance    0 to 100 (clamped)
	 * @return self for chaining
	 */
	public DistillationTowerRecipeBuilder addByproduct(ItemStack byproduct, int chance){
		return addByproduct(byproduct, chance / 100D);
	}
	
	/**
	 * Can be called multiple times to add more byproducts to the recipe. Or never to not have any byproducts.
	 * 
	 * @param byproduct {@link ItemStack} to output as byproduct
	 * @param chance    0.0 to 1.0 (clamped)
	 * @return {@link DistillationTowerRecipeBuilder} self for chaining
	 */
	public DistillationTowerRecipeBuilder addByproduct(ItemStack byproduct, double chance){
		this.byproducts.add(new ChancedItemStack(byproduct, Mth.clamp(chance, 0.0, 1.0)));
		return this;
	}
	
	public DistillationTowerRecipeBuilder setTimeAndEnergy(int time, int energy){
		this.time = time;
		this.energy = energy;
		return this;
	}
	
	public DistillationTowerRecipeBuilder addInput(TagKey<Fluid> fluidTag, int amount){
		this.input = new FluidTagInput(fluidTag, amount);
		return this;
	}
	
	public DistillationTowerRecipeBuilder addInput(Fluid fluid, int amount){
		return addInput(new FluidStack(fluid, amount));
	}
	
	public DistillationTowerRecipeBuilder addInput(FluidStack fluidStack){
		this.fluidOutput.add(fluidStack);
		return this;
	}
	
	public DistillationTowerRecipeBuilder addFluids(FluidStack... fluidStacks){
        this.fluidOutput.addAll(Arrays.asList(fluidStacks));
		return this;
	}
	
	public DistillationTowerRecipeBuilder addItems(ItemStack... itemStacks){
		for(ItemStack stack:itemStacks){
			this.byproducts.add(new ChancedItemStack(stack, 1.0));
		}
		return this;
	}
	
	public void build(RecipeOutput out, ResourceLocation name) {
		DistillationTowerRecipe recipe = new DistillationTowerRecipe(this.fluidOutput.toArray(new FluidStack[0]), this.byproducts.toArray(new ChancedItemStack[0]), this.input, this.energy, this.time);
		out.accept(name, recipe, null, this.getConditions());
	}
}
