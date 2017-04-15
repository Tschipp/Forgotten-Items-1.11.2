package tschipp.forgottenitems.items;

import tschipp.forgottenitems.util.FIConfig;
import tschipp.forgottenitems.util.FIHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemWindTalisman extends ItemTalisman {

	public ItemWindTalisman() {
		super("wind_talisman", "Fly with the wind", 16, ItemList.windGem);
		this.setMaxDamage(1000);
	}


	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		super.onItemRightClick(world, player, hand);

		double x = player.getLookVec().xCoord;
		double y = player.getLookVec().yCoord;
		double z = player.getLookVec().zCoord;

		double motx = player.motionX;
		double moty = player.motionY;
		double motz = player.motionZ;

		//System.out.println(motx + ", " + moty + ", " + motz);
		ItemStack stack = player.getHeldItem(hand);
		if(motx < FIConfig.windTalismanMaxVelocity && motx > -FIConfig.windTalismanMaxVelocity && moty < FIConfig.windTalismanMaxVelocity && moty > -FIConfig.windTalismanMaxVelocity && motz < FIConfig.windTalismanMaxVelocity && motz > -FIConfig.windTalismanMaxVelocity)
		{
			player.addVelocity(x*FIConfig.windTalismanVelocityMultiplier, y*FIConfig.windTalismanVelocityMultiplier, z*FIConfig.windTalismanVelocityMultiplier);
			player.setAir(0);

			stack.damageItem(((int) (Math.abs(motx)+ Math.abs(moty)+ Math.abs(motz)/3)) + 1, player);
			world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_FIREWORK_LAUNCH, SoundCategory.PLAYERS, 0.6F, 0.8F / (itemRand.nextFloat() * 0.1F + 0.8F));
			return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));

		}

		return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return repair.getItem() == ItemList.windGem;
	}

}
