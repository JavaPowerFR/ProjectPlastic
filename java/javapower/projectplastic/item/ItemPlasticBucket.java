package javapower.projectplastic.item;

import javax.annotation.Nullable;

import javapower.projectplastic.core.PlasticCraft;
import javapower.projectplastic.util.Tools;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemPlasticBucket extends ItemBase
{

	String[] names = {
			"plasticbucket",
			"plasticbucketwater",
			"plasticbucketmilk",
			"plasticbucketlatex"
			};
	
	public ItemPlasticBucket()
	{
		super("plasticbucket");
		this.setMaxStackSize(16);
		this.setHasSubtypes(true);
		this.setContainerItem(this);
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
	public int getMaxItemUseDuration(ItemStack stack)
    {
		if(stack != null && stack.getItemDamage() == 2)
			return 32;
		return 0;
    }
	
	@Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
		if(stack != null && stack.getItemDamage() == 2)
			return EnumAction.DRINK;
		return EnumAction.NONE;
    }
	
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        if (!worldIn.isRemote) entityLiving.curePotionEffects(stack);
        if (entityLiving instanceof EntityPlayerMP)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(entityplayermp, stack);
            entityplayermp.addStat(StatList.getObjectUseStats(this));
        }

        if (entityLiving instanceof EntityPlayer && !((EntityPlayer)entityLiving).capabilities.isCreativeMode)
        {
        	if(stack.getCount() == 1)
        		return new ItemStack(PCItems.item_bucket, 1, 0);
        	ItemStack result = Tools.TryToPutItemStackInInventory((EntityPlayer) entityLiving, new ItemStack(PCItems.item_bucket, 1, 0));
        	if(!result.isEmpty())
        		((EntityPlayer)entityLiving).dropItem(result, true);
        	
            stack.shrink(1);
        }
        return stack.isEmpty() ? new ItemStack(PCItems.item_bucket, 1, 0) : stack;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(itemstack != null && itemstack.getItem() instanceof ItemPlasticBucket)
		{
			if(itemstack.getItemDamage() == 2)
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
					if (!playerIn.capabilities.isCreativeMode)
			        {
						if(itemstack.getCount() == 1)
							return new ActionResult<ItemStack>(EnumActionResult.PASS, new ItemStack(PCItems.item_bucket, 1, 2));
						
			        	ItemStack result = Tools.TryToPutItemStackInInventory(playerIn, new ItemStack(PCItems.item_bucket, 1, 2));
			        	if(!result.isEmpty())
			        		playerIn.dropItem(result, true);
			        	
			            itemstack.shrink(1);
			        }
			        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack.isEmpty() ? new ItemStack(PCItems.item_bucket, 1, 1) : itemstack);
					//return new ActionResult<ItemStack>(EnumActionResult.PASS, new ItemStack(PCItems.item_bucket, 1,2));
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
	                        //playerIn.addStat(StatList.getObjectUseStats(this));
	                        playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
	                        //itemstack.setItemDamage(1);
	                        //return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	                        if (!playerIn.capabilities.isCreativeMode)
	    			        {
	    						if(itemstack.getCount() == 1)
	    							return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(PCItems.item_bucket, 1, 1));
	    						
	    			        	ItemStack result = Tools.TryToPutItemStackInInventory(playerIn, new ItemStack(PCItems.item_bucket, 1, 1));
	    			        	if(!result.isEmpty())
	    			        		playerIn.dropItem(result, true);
	    			        	
	    			            itemstack.shrink(1);
	    			        }
	    			        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack.isEmpty() ? new ItemStack(PCItems.item_bucket, 1, 1) : itemstack);
	    					
	                    }
	                    else if (material == Material.LAVA && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
	                    {
	                        playerIn.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1.0F, 1.0F);
	                        //return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(PCItems.item_resource,1,8));
	                        if (!playerIn.capabilities.isCreativeMode)
	    			        {
	    						if(itemstack.getCount() == 1)
	    							return new ActionResult<ItemStack>(EnumActionResult.PASS, new ItemStack(PCItems.item_resource,1,8));
	    						
	    			        	ItemStack result = Tools.TryToPutItemStackInInventory(playerIn, new ItemStack(PCItems.item_resource,1,8));
	    			        	if(!result.isEmpty())
	    			        		playerIn.dropItem(result, true);
	    			        	
	    			            itemstack.shrink(1);
	    			        }
	    			        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack.isEmpty() ? new ItemStack(PCItems.item_resource,1,8) : itemstack);
	    					
	                    }
	                    else
	                    {
	                        return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
	                    }
	                }
	            }
	            else
	            {
	                boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
	                BlockPos blockpos1 = flag1 && raytraceresult.sideHit == EnumFacing.UP ? blockpos : blockpos.offset(raytraceresult.sideHit);
	
	                if (!playerIn.canPlayerEdit(blockpos1, raytraceresult.sideHit, itemstack))
	                {
	                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
	                }
	                else if (this.tryPlaceContainedLiquid(playerIn, worldIn, blockpos1, itemstack))
	                {
	                    if (playerIn instanceof EntityPlayerMP)
	                    {
	                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)playerIn, blockpos1, itemstack);
	                    }
	
	                    playerIn.addStat(StatList.getObjectUseStats(this));
	                    return new ActionResult(EnumActionResult.SUCCESS, new ItemStack(PCItems.item_bucket, 1, 0));
	                }
	                else
	                {
	                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
	                }
	            }
	        }
		}
		else
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
    }
	
	public boolean tryPlaceContainedLiquid(@Nullable EntityPlayer player, World worldIn, BlockPos posIn, ItemStack item)
    {
        if (item.getItemDamage() != 1)//not water
        {
            return false;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(posIn);
            Material material = iblockstate.getMaterial();
            boolean flag = !material.isSolid();
            boolean flag1 = iblockstate.getBlock().isReplaceable(worldIn, posIn);

            if (!worldIn.isAirBlock(posIn) && !flag && !flag1)
            {
                return false;
            }
            else
            {
                if (worldIn.provider.doesWaterVaporize())
                {
                    int l = posIn.getX();
                    int i = posIn.getY();
                    int j = posIn.getZ();
                    worldIn.playSound(player, posIn, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

                    for (int k = 0; k < 8; ++k)
                    {
                        worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)l + Math.random(), (double)i + Math.random(), (double)j + Math.random(), 0.0D, 0.0D, 0.0D);
                    }
                }
                else
                {
                    if (!worldIn.isRemote && (flag || flag1) && !material.isLiquid())
                    {
                        worldIn.destroyBlock(posIn, true);
                    }
                    
                    worldIn.playSound(player, posIn, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    worldIn.setBlockState(posIn, Blocks.FLOWING_WATER.getDefaultState()/*BlockLiquid.getBlockFromName("water").getDefaultState()*/, 11);
                }
                return true;
            }
        }
    }
	
	/*@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack itemstack = player.getHeldItem(hand);
		if(itemstack != null && itemstack.getItem() instanceof ItemPlasticBucket)
		{
			ItemBucket
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}*/

}
