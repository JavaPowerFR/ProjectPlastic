package javapower.projectplastic.item;

import javapower.projectplastic.core.PlasticCraft;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemPlasticBottle extends ItemBase 
{
	String[] names = {
			"plasticbottle",
			"plasticbottlemilk",
			"plasticbottlewater",
			};
	
	public ItemPlasticBottle()
	{
		super("plasticbottle");
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setContainerItem(this);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
		if(stack != null && stack.getItemDamage() != 0)
			return 32;
		return 0;
    }
	
	@Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
		if(stack != null && stack.getItemDamage() != 0)
			return EnumAction.DRINK;
		return EnumAction.NONE;
    }
	
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
		if(entityLiving instanceof EntityPlayer && ((EntityPlayer)entityLiving).getFoodStats().getFoodLevel() < 20)
		{
	        if (!worldIn.isRemote)
	    	{
	        	((EntityPlayer)entityLiving).getFoodStats().setFoodLevel(
	        			(int) (((EntityPlayer)entityLiving).getFoodStats().getFoodLevel()
	        			+(stack.getItemDamage() == 1 ? 3 : 1)));
	    	}
	        
	        if(entityLiving instanceof EntityPlayerMP)
	        {
	            EntityPlayerMP entityplayermp = (EntityPlayerMP)entityLiving;
	            CriteriaTriggers.CONSUME_ITEM.trigger(entityplayermp, stack);
	            entityplayermp.addStat(StatList.getObjectUseStats(this));
	        }
	
	        if (entityLiving instanceof EntityPlayer && !((EntityPlayer)entityLiving).capabilities.isCreativeMode)
	        {
	            stack.shrink(1);
	        }
	
	        return stack.isEmpty() ? new ItemStack(PCItems.item_bottle, 1, 0) : stack;
		}
		
		return stack;
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		if(stack != null && names.length > stack.getItemDamage())
		return "item."+names[stack.getItemDamage()];
		
		return super.getUnlocalizedName();
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if(tab == PlasticCraft.creativeTab)
			for(int i = 0; i <= names.length-1; ++i)
				items.add(new ItemStack(this, 1, i));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(itemstack != null && itemstack.getItem() instanceof ItemPlasticBottle)
		{
			if(itemstack.getItemDamage() != 0)
			{
				playerIn.setActiveHand(handIn);
				return super.onItemRightClick(worldIn, playerIn, handIn);
			}
	        boolean flag = itemstack.getItemDamage() == 0;
	        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, flag);
			
	        if (raytraceresult == null)
	        {
	            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
	        }
	        
	        //---------- cow ----------
	        double i = raytraceresult.hitVec.x;
	        double j = raytraceresult.hitVec.y;
	        double k = raytraceresult.hitVec.z;
			
			for (EntityLiving entityliving : worldIn.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB((double)i - 0.1D, (double)j - 0.1D, (double)k - 0.1D, (double)i + 0.1D, (double)j + 0.1D, (double)k +0.1D)))
	        {
				if(flag && entityliving != null && entityliving instanceof EntityCow)
				{
					return new ActionResult<ItemStack>(EnumActionResult.PASS, new ItemStack(PCItems.item_bottle, 1,1));
				}
	        }
	        //---------- end cow ----------
			
	        if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
	        {
	            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
	        }
	        else
	        {
	            BlockPos blockpos = raytraceresult.getBlockPos();
	
	            if (!worldIn.isBlockModifiable(playerIn, blockpos))
	            {
	                return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
	            }
	            else if (flag)
	            {
	                if (!playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack))
	                {
	                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
	                }
	                else
	                {
	                    IBlockState iblockstate = worldIn.getBlockState(blockpos);
	                    Material material = iblockstate.getMaterial();
	
	                    if (material == Material.WATER && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
	                    {
	                        worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
	                        playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
	                        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(PCItems.item_bottle, 1,2));
	                    }
	                    else
	                    {
	                        return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
	                    }
	                }
	            }
	        }
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
    }
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		//TODO
	}	
	
}
