package javapower.projectplastic.item;

import javapower.projectplastic.core.PlasticCraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemShockAbsorbingBoots extends ItemArmor
{
	public ItemShockAbsorbingBoots()
	{
		super(ArmorMaterial.DIAMOND, 4, EntityEquipmentSlot.FEET);
		
		setCreativeTab(PlasticCraft.creativeTab);
		setRegistryName("shockabsorbingboots");
		setUnlocalizedName("shockabsorbingboots");
	}
	
	@Override
	public EntityEquipmentSlot getEquipmentSlot(ItemStack stack)
	{
		return EntityEquipmentSlot.FEET;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		return PlasticCraft.MODID+":textures/models/armor/falldampener1.png";
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		stack.addEnchantment(Enchantment.getEnchantmentByID(3), 5);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		player.fallDistance = 0;
	}

}