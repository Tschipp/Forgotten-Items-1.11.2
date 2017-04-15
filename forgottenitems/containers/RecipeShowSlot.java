package tschipp.forgottenitems.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class RecipeShowSlot extends SingleItemSlot {

	public RecipeShowSlot(IInventory inventory, int index, int xPosition, int yPosition) {
		super(inventory, index, xPosition, yPosition);
	}
	
	
	@Override
	public boolean canTakeStack(EntityPlayer playerIn) {
		return false;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
	
	
	
	

}
