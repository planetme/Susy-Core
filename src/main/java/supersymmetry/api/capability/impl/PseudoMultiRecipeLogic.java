package supersymmetry.api.capability.impl;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.RecipeLogicEnergy;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import org.jetbrains.annotations.NotNull;
import supersymmetry.api.metatileentity.PseudoMultiMachineMetaTileEntity;
import supersymmetry.api.recipes.properties.PseudoMultiProperty;

import java.util.function.Supplier;

public class PseudoMultiRecipeLogic extends RecipeLogicEnergy {

    private final PseudoMultiMachineMetaTileEntity pmMTE;

    public PseudoMultiRecipeLogic(PseudoMultiMachineMetaTileEntity tileEntity, RecipeMap recipeMap, Supplier<IEnergyContainer> energyContainer) {
        super(tileEntity, recipeMap, energyContainer);
        pmMTE = tileEntity;
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe) {
        if (this.pmMTE.getTargetBlockState() == null) return false; //if world was remote or null
        return recipe.getProperty(PseudoMultiProperty.getInstance(), null)
                .getValidBlockStates().contains(this.pmMTE.getTargetBlockState()) && super.checkRecipe(recipe);
    }

    @Override
    public boolean canProgressRecipe() {
        return this.previousRecipe == null || this.previousRecipe.getProperty(PseudoMultiProperty.getInstance(), null).getValidBlockStates()
                .contains(this.pmMTE.getTargetBlockState()) && super.canProgressRecipe();
    }
}
