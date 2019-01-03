package javapower.projectplastic.block;

import java.util.List;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGooeyPlastic extends BlockBase
{
	public static final AxisAlignedBB bone = new AxisAlignedBB(0.1f, 0.1f, 0.1f, 0.9f, 0.9f, 0.9f);
	public BlockGooeyPlastic()
	{
		super(Material.IRON, "gooeyplastic");
		setHardness(0.8f);
		setResistance(4f);
		setSoundType(SoundType.CLOTH);
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return bone;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return bone;
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
	{
		return bone;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		entityIn.attackEntityFrom(DamageSource.HOT_FLOOR, 1);
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		NonNullList<ItemStack> ret = NonNullList.create();
		ret.add(new ItemStack(thisItem, 1));
		return ret;
	}
	
	@Override
	public int getHarvestLevel(IBlockState state)
	{
		return 0;
	}

}
