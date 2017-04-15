package tschipp.forgottenitems.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.registry.GameData;
import tschipp.forgottenitems.items.ItemList;

public class FIHelper {

	public static ArrayList<HashMap<Item, Item>> OUTPUTS_CORES = new ArrayList<HashMap<Item,Item>>();
	public static ArrayList<HashMap<Item, Item>> CUSTOM_OUTPUTS_CORES = new ArrayList<HashMap<Item,Item>>();


	/**
	 * Sets the Core and output Item for the given id
	 * @param id
	 * @param output
	 * @param core
	 */
	public static void setOutputCore(int id, Item output, Item core)
	{
		HashMap<Item, Item> map = new HashMap<Item, Item>();
		map.put(output, core);
		OUTPUTS_CORES.set(id, map);
	}


	/**
	 * Gets the Core Item for the given Recipe ID
	 * @param id
	 * @return 
	 */
	public static Item getCoreItem(int id) 
	{
		try {
			return OUTPUTS_CORES.get(id).entrySet().iterator().next().getValue();
		}
		catch (Exception e)
		{
			return null;
		}
	}


	/**
	 * Gets the Output for the given ID
	 * @param id
	 * @return
	 */
	public static Item getOutputItem(int id)
	{
		try {
			return OUTPUTS_CORES.get(id).entrySet().iterator().next().getKey();
		}
		catch (Exception e)
		{
			return null;
		}
	}


	/**
	 * Sets the Core and output Item for the given id
	 * @param id
	 * @param output
	 * @param core
	 */
	public static void setOutputCoreCustom(int id, Item output, Item core)
	{
		HashMap<Item, Item> map = new HashMap<Item, Item>();
		map.put(output, core);
		CUSTOM_OUTPUTS_CORES.set(id, map);
	}


	/**
	 * Gets the Core Item for the given Recipe ID
	 * @param id
	 * @return 
	 */
	public static Item getCoreItemCustom(int id) 
	{
		try{
			return CUSTOM_OUTPUTS_CORES.get(id).entrySet().iterator().next().getValue();
		}
		catch (Exception e)
		{
			return null;
		}
	}


	/**
	 * Gets the Output for the given ID
	 * @param id
	 * @return
	 */
	public static Item getOutputItemCustom(int id)
	{
		try{
			return CUSTOM_OUTPUTS_CORES.get(id).entrySet().iterator().next().getKey();
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Gets the seed on both Client and Server
	 * @param world
	 * @return
	 */
	public static long getSeed(World world)
	{
		if(!world.isRemote)
		{
			return Math.abs(world.getSeed());
		}
		else
		{
			return FIWorldSavedData.getInstance(world).getSeed();
		}

	}

	/**
	 * Creates a new Item with the given seed
	 * @param seed
	 * @return
	 */
	public static Item getNewItemBySeed(long seed, World world) 
	{


		Item item = null;

		ArrayList<String> forbiddenList = FIWorldSavedData.getInstance(world).getForbidden();

		int k = 0;
		while(forbiddenList != null && (item == null || forbiddenList.contains(item.getRegistryName().toString())))
		{
			Random rand = new Random(seed + k);
			if(FIConfig.useNonVanillaItems)
			{
				item = Item.getByNameOrId(((ResourceLocation) Item.REGISTRY.getKeys().toArray()[rand.nextInt(Item.REGISTRY.getKeys().size())]).toString());

			}
			else
			{
				item = GameData.getItemRegistry().getRaw(rand.nextInt(GameData.getItemRegistry().getKeys().size()));
			}
			k++;
		}
		return item;
	}



	/**
	 * Gets a new Item by the given Seed, the current Number of Item, and the Recipe ID
	 * @param seed
	 * @param number
	 * @param id
	 * @return
	 */
	public static Item getItemBySeed(long seed, int number, int id, World world)
	{
		Item item = null;

		ArrayList<String> forbiddenList = FIWorldSavedData.getInstance(world).getForbidden();

		int k = 0;
		while(forbiddenList != null && (item == null || forbiddenList.contains(item.getRegistryName().toString())))
		{
			Random rand = new Random((seed + k) / (number * id) + id);
			if(FIConfig.useNonVanillaItems)
			{
				item = Item.getByNameOrId(((ResourceLocation) Item.REGISTRY.getKeys().toArray()[rand.nextInt(Item.REGISTRY.getKeys().size())]).toString());

			}
			else
			{
				item = GameData.getItemRegistry().getRaw(rand.nextInt(GameData.getItemRegistry().getKeys().size()));
			}			
			k++;

		}
		return item;
	}


	/**
	 * Gets a new Item by the given Seed, the current Number of Item, and the Recipe ID
	 * @param seed
	 * @param number
	 * @param id
	 * @return
	 */
	public static Item getCustomItemBySeed(long seed, int number, int id, World world)
	{
		Item item = null;

		ArrayList<String> forbiddenList = FIWorldSavedData.getInstance(world).getForbidden();

		int k = 0;
		while(forbiddenList != null && (item == null || forbiddenList.contains(item.getRegistryName().toString())))
		{
			Random rand = new Random((seed * (seed/2) + k) / (number * id) + id);
			if(FIConfig.useNonVanillaItems)
			{
				item = Item.getByNameOrId(((ResourceLocation) Item.REGISTRY.getKeys().toArray()[rand.nextInt(Item.REGISTRY.getKeys().size())]).toString());

			}
			else
			{
				item = GameData.getItemRegistry().getRaw(rand.nextInt(GameData.getItemRegistry().getKeys().size()));
			}			
			k++;

		}
		return item;
	}


	/**
	 * Gets a list of Forbidden items
	 * @return
	 */
	public static ArrayList<String> getForbiddenList()
	{
		String[] forbidden = FIConfig.bannedItems;
		ArrayList<String> forbiddenList = new ArrayList<String>();

		for(int i = 0; i < forbidden.length; i++)
		{
			if(forbidden[i].contains("*"))
			{
				String modid = forbidden[i].replace("*", "");
				for(int k = 0; k < Item.REGISTRY.getKeys().size(); k++)
				{
					if(Item.REGISTRY.getKeys().toArray()[k].toString().contains(modid))
					{
						forbiddenList.add(Item.REGISTRY.getKeys().toArray()[k].toString());
					}
				}
			}
			forbiddenList.add(forbidden[i]);


		}

		return forbiddenList;
	}

	/**
	 * Checks if the player has an Item in the inventory
	 * @param itemStackIn
	 * @param player
	 * @return
	 */
	public static boolean hasItem(Item item, EntityPlayer player)
	{
		final List<NonNullList<ItemStack>> allInventories = Arrays.<NonNullList<ItemStack>>asList(new NonNullList[] {player.inventory.mainInventory, player.inventory.armorInventory, player.inventory.offHandInventory});


		label19:

			for (List<ItemStack> list : allInventories)
			{
				Iterator iterator = list.iterator();

				while (true)
				{
					if (!iterator.hasNext())
					{
						continue label19;
					}

					ItemStack itemstack = (ItemStack)iterator.next();


					if (!itemstack.isEmpty() && itemstack.getItem() == item)
					{
						break;
					}
				}

				return true;
			}

		return false;
	}


	/**
	 * Checks if the player has an Item equipped as Armor
	 * @param itemStackIn
	 * @param player
	 * @return
	 */
	public static boolean hasArmorItem(Item item, EntityPlayer player)
	{

		for (int i = 0; i < player.inventory.armorInventory.size(); i++)
		{
			if(!player.inventory.armorInventory.get(i).isEmpty() && player.inventory.armorInventory.get(i).getItem() == item)
				return true;
		}

		return false;
	}


	/**
	 * Gets the slot for a specific item in all inventories
	 * @param item
	 * @param player
	 * @return
	 */
	public static int getSlotForItem(Item item, EntityPlayer player)
	{

		final List<NonNullList<ItemStack>> allInventories = Arrays.<NonNullList<ItemStack>>asList(new NonNullList[] { player.inventory.mainInventory, player.inventory.armorInventory, player.inventory.offHandInventory});

		for(int k = 0; k < allInventories.size(); k++)
		{
			NonNullList<ItemStack> list = allInventories.get(k);
			for (int i = 0; i < list.size(); ++i)
			{
				if (!((ItemStack)list.get(i)).isEmpty() && ((ItemStack)list.get(i)).getItem() == item)
				{
					if(list.size() == 4)
						return 36 + i;
					if(list.size() == 1)
						return 40;
					return i;

				}
			}
		}

		return -1;
	}

	/**
	 * Prints the Crafting recipe in the Chat and in the Console
	 * @param world
	 * @param player
	 * @param id
	 */
	public static void printCraftingRecipe(World world, EntityPlayer player, int id)
	{
		long seed = FIHelper.getSeed(world);

		Item item1 = FIHelper.getItemBySeed(seed, 1, id, world);
		Item item2 = FIHelper.getItemBySeed(seed, 2, id, world);
		Item item3 = FIHelper.getItemBySeed(seed, 3, id, world);
		Item item4 = FIHelper.getItemBySeed(seed, 4, id, world);
		Item item5 = FIHelper.getItemBySeed(seed, 5, id, world);
		Item item6 = FIHelper.getItemBySeed(seed, 6, id, world);
		Item item7 = FIHelper.getItemBySeed(seed, 7, id, world);
		Item item8 = FIHelper.getItemBySeed(seed, 8, id, world);
		Item core = FIHelper.getCoreItem(id);

		if(FIConfig.recipeGivesItems)
		{
			player.inventory.addItemStackToInventory(new ItemStack(item1, 64));
			player.inventory.addItemStackToInventory(new ItemStack(item2, 64));
			player.inventory.addItemStackToInventory(new ItemStack(item3, 64));
			player.inventory.addItemStackToInventory(new ItemStack(item4, 64));
			player.inventory.addItemStackToInventory(new ItemStack(item5, 64));
			player.inventory.addItemStackToInventory(new ItemStack(item6, 64));
			player.inventory.addItemStackToInventory(new ItemStack(item7, 64));
			player.inventory.addItemStackToInventory(new ItemStack(item8, 64));
			player.inventory.addItemStackToInventory(new ItemStack(core, 64));
		}

		player.sendMessage(new TextComponentString("Core Item: " + core.getRegistryName() + ", Other Items: " +item1.getRegistryName() + ", " + item2.getRegistryName() + ", " + item3.getRegistryName() + ", " + item4.getRegistryName() + ", " + item5.getRegistryName() + ", " + item6.getRegistryName() + ", " + item7.getRegistryName() + ", " + item8.getRegistryName()));
		FMLLog.log("ForgottenItems", Level.INFO, "Core Item: " + core.getRegistryName() + ", Other Items: " + item1.getRegistryName() + ", " + item2.getRegistryName() + ", " + item3.getRegistryName() + ", " + item4.getRegistryName() + ", " + item5.getRegistryName() + ", " + item6.getRegistryName() + ", " + item7.getRegistryName() + ", " + item8.getRegistryName());

	}


	/**
	 * Gets the Items required for a recipe, contained in a List.
	 * @param world
	 * @param id
	 * @return A list with the crafting components
	 */
	public static ArrayList<Item> getItemsForRecipe(World world, int id)
	{
		long seed = FIHelper.getSeed(world);
		ArrayList<Item> craftingList = new ArrayList<Item>();

		Item item1 = FIHelper.getItemBySeed(seed, 1, id, world);
		Item item2 = FIHelper.getItemBySeed(seed, 2, id, world);
		Item item3 = FIHelper.getItemBySeed(seed, 3, id, world);
		Item item4 = FIHelper.getItemBySeed(seed, 4, id, world);
		Item item5 = FIHelper.getItemBySeed(seed, 5, id, world);
		Item item6 = FIHelper.getItemBySeed(seed, 6, id, world);
		Item item7 = FIHelper.getItemBySeed(seed, 7, id, world);
		Item item8 = FIHelper.getItemBySeed(seed, 8, id, world);

		Item core = null;
		try{
			core = FIHelper.getCoreItem(id);
		}
		catch (Exception e){}
		

		craftingList.add(item1);
		craftingList.add(item2);
		craftingList.add(item3);
		craftingList.add(item4);
		craftingList.add(core);
		craftingList.add(item5);
		craftingList.add(item6);
		craftingList.add(item7);
		craftingList.add(item8);


		return craftingList;

	}


	/**
	 * Gets the Items required for a recipe, contained in a List.
	 * @param world
	 * @param id
	 * @return A list with the crafting components
	 */
	public static ArrayList<Item> getItemsForCustomRecipe(World world, int id)
	{
		long seed = FIHelper.getSeed(world);
		ArrayList<Item> craftingList = new ArrayList<Item>();
		String[] customRecipes = FIConfig.customCraftingRecipes; 

		Item item1 = FIHelper.getCustomItemBySeed(seed, 1, id, world);
		Item item2 = FIHelper.getCustomItemBySeed(seed, 2, id, world);
		Item item3 = FIHelper.getCustomItemBySeed(seed, 3, id, world);
		Item item4 = FIHelper.getCustomItemBySeed(seed, 4, id, world);
		Item item5 = FIHelper.getCustomItemBySeed(seed, 5, id, world);
		Item item6 = FIHelper.getCustomItemBySeed(seed, 6, id, world);
		Item item7 = FIHelper.getCustomItemBySeed(seed, 7, id, world);
		Item item8 = FIHelper.getCustomItemBySeed(seed, 8, id, world);

		Item core = null;

		try{
			core = Item.getByNameOrId(customRecipes[id-1]);
		}
		catch (Exception e){}


		craftingList.add(item1);
		craftingList.add(item2);
		craftingList.add(item3);
		craftingList.add(item4);
		craftingList.add(core);
		craftingList.add(item5);
		craftingList.add(item6);
		craftingList.add(item7);
		craftingList.add(item8);


		return craftingList;

	}







}
