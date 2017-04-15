package tschipp.forgottenitems.items;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tschipp.forgottenitems.FIM;
import tschipp.forgottenitems.util.FIHelper;
import tschipp.tschipplib.item.TSPickaxe;

public class ItemObsidianHarvester extends TSPickaxe {

	protected ItemObsidianHarvester(ToolMaterial material) {
		super("obsidian_harvester", material, FIM.MODID);
		this.setCreativeTab(FIM.forgottenItems);
		FIHelper.setOutputCore(19, this, ItemList.hastyPickaxe);

	}


	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		if(state.getBlock() == Blocks.OBSIDIAN)
		{
			return super.getStrVsBlock(stack, state) + 100f;
		}
		
		return super.getStrVsBlock(stack, state);

	}


	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack)
	{
		return "" + TextFormatting.AQUA + I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name");
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add("Very useful for mining Obsidian");
	}


	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		if(player.isSneaking() && !world.isRemote && player.isCreative())
		{
			FIHelper.printCraftingRecipe(world, player, 19);
		}
		return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

}
