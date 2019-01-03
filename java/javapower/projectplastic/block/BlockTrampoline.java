package javapower.projectplastic.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTrampoline extends BlockBase
{
	protected static final AxisAlignedBB AABB_BLOCK = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.40625D, 1.0D);
	
	public BlockTrampoline()
	{
		super(Material.WOOD, "trampoline");
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
		if(Math.abs(X) < 0.7D && Math.abs(Z) < 0.7D && Y < 0)
		{
			if(entityIn instanceof EntityPlayer && ((EntityPlayer)entityIn).isSneaking())
				return;
			int multi = 0;
			for(EnumFacing face : EnumFacing.HORIZONTALS)
			{
				IBlockState bs = worldIn.getBlockState(pos.offset(face));
				if(bs != null && bs.getBlock() instanceof BlockTrampoline)
					++multi;
			}
			
			entityIn.addVelocity(0, 1+multi*0.25D, 0);
			entityIn.fallDistance = 0;
			/*if(entityIn instanceof EntityPlayer)
				System.out.println(X+" "+Y+" "+Z);*/
		}
	}

}
