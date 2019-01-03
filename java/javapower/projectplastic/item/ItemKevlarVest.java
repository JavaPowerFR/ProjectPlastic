package javapower.projectplastic.item;

import javapower.projectplastic.core.PlasticCraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemKevlarVest extends ItemArmor
{
	public ItemKevlarVest()
	{
		super(ArmorMaterial.DIAMOND, 4, EntityEquipmentSlot.CHEST);
		
		setCreativeTab(PlasticCraft.creativeTab);
		setRegistryName("kevlarvest");
		setUnlocalizedName("kevlarvest");
	}
	
	@Override
	public EntityEquipmentSlot getEquipmentSlot(ItemStack stack)
	{
		return EntityEquipmentSlot.CHEST;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		return PlasticCraft.MODID+":textures/models/armor/kevlar1.png";
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		stack.addEnchantment(Enchantment.getEnchantmentByID(3), 5);
	}

}