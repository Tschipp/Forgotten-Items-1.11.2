package tschipp.forgottenitems.items;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWaterTalisman extends ItemTalisman {

	public ItemWaterTalisman() {
		super("water_talisman", "Creates a Water Block", 14, ItemList.waterGem);
		this.setMaxDamage(1000);
	}


	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		IBlockState iblockstate = world.getBlockState(pos);
		Block block = iblockstate.getBlock();
		ItemStack stack = player.getHeldItem(hand);

		if (!block.isReplaceable(world, pos))
		{
			pos = pos.offset(facing);
		}
		
		world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState());
		stack.damageItem(1, player);

		return EnumActionResult.SUCCESS;
	}
	
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {		
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		
		ItemStack stack2 = stack.copy();
		stack2.setItemDamage(stack2.getItemDamage() + 1);
		return stack2;
	}
	
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == ItemList.waterGem;
    }
	

}
