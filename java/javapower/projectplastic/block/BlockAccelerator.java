package javapower.projectplastic.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAccelerator extends BlockBase
{
	protected static final AxisAlignedBB AABB_BLOCK = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.40625D, 1.0D);
	
	public BlockAccelerator()
	{
		super(Material.WOOD, "accelerator");
		setHardness(2.0F);
	}

	@Override
	public Item getItem()
	{
		if(thisItem == null)
		{
			thisItem = new ItemBlock(this);
			thisItem.setRegistryName(name);
		}
		return thisItem;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB_BLOCK;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return AABB_BLOCK;
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
	{
		return AABB_BLOCK;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		double X = entityIn.posX - (pos.getX() + 0.5D);
		double Y = entityIn.posY - (pos.getY() + 0.5D);
		double Z = entityIn.posZ - (pos.getZ() + 0.5D);
		if(Math.abs(X) < 0.7D && Math.abs(Z) < 0.7D && Y < 0 && entityIn instanceof EntityPlayer)
		{
			((EntityPlayer)entityIn).addPotionEffect(new PotionEffect(Potion.getPotionById(1), 1, 3));
		}
	}

}
