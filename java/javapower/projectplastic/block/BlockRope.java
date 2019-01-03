package javapower.projectplastic.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRope extends BlockBase
{
	protected static final AxisAlignedBB AABB_BLOCK = new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 0.5625D, 1.0D, 0.5625D);
	public static final PropertyBool PROPERTY_MAIN = PropertyBool.create("main");
	
	public BlockRope()
	{
		super(Material.CIRCUITS, "rope");
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
	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity)
	{
		return true;
	}
	
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
		IBlockState bs = worldIn.getBlockState(pos.add(0, 1, 0));
		if(bs != null && (worldIn.isBlockFullCube(pos.add(0,1,0)) || bs.getBlock() instanceof BlockRope))
			return true;
		
        return false;
    }
	
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
		this.checkForDrop(worldIn, pos, state);
    }
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		IBlockState bsup = worldIn.getBlockState(pos.add(0, 1, 0));
		worldIn.setBlockState(pos, state.withProperty(PROPERTY_MAIN, bsup != null ? !(bsup.getBlock() instanceof BlockRope) : false));
		for(int i = 1; i < 255; ++i)
    	{
    		BlockPos pos2 = pos.add(0, -i, 0);
    		if(pos.getY() > 0 && worldIn.isAirBlock(pos2))
    		{
    			worldIn.setBlockState(pos2, this.getDefaultState().withProperty(PROPERTY_MAIN, false));
    		}
    		else break;
    	}
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
	{
		for(int i = 1; i < 255; ++i)
    	{
    		BlockPos pos2 = pos.add(0, i, 0);
    		BlockPos pos3 = pos.add(0, i-1, 0);
    		if(pos2.getY() <= 256)
    		{
    			IBlockState bs_up = worldIn.getBlockState(pos2);
    			if(bs_up != null && !(bs_up.getBlock() instanceof BlockRope))
    			{
    				worldIn.setBlockToAir(pos3);
    				return;
    			}
    		}
    		else return;
    	}
	}
    
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        this.onNeighborChangeInternal(worldIn, pos, state);
    }

    protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.checkForDrop(worldIn, pos, state))
        {
            return true;
        }
        
        for(int i = 1; i < 255; ++i)
    	{
    		BlockPos pos2 = pos.add(0, -i, 0);
    		if(pos.getY() > 0 && worldIn.isAirBlock(pos2))
    		{
    			worldIn.setBlockState(pos2, this.getDefaultState().withProperty(PROPERTY_MAIN, false));
    		}
    		else break;
    	}
        
        return false;
    }

    protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
    {
        if (canPlaceBlockAt(worldIn, pos))
        {
            return true;
        }
        else
        {
            if (worldIn.getBlockState(pos).getBlock() == this)
            {
                if(state.getValue(PROPERTY_MAIN))
                	this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }

            return false;
        }
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
    	return new BlockStateContainer(this, new IProperty[] {PROPERTY_MAIN});
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
    	return getDefaultState().withProperty(PROPERTY_MAIN, meta == 1);
    }
    
    @Override
    public int getMetaFromState(IBlockState state)
    {
    	return state.getValue(PROPERTY_MAIN) ? 1 : 0;
    }

}
