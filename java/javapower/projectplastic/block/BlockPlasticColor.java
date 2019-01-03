package javapower.projectplastic.block;

import java.util.List;

import javapower.projectplastic.core.PlasticCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPlasticColor extends BlockBase
{
	public static final PropertyInteger PROPERTY_META = PropertyInteger.create("meta", 0, 15);
	
	public BlockPlasticColor()
	{
		super(Material.IRON, "plasticblockcolor");
		this.setHardness(2.0F);
		this.setResistance(1000.0F);
	}

	@Override
	public Item getItem()
	{
		if(thisItem == null)
		{
			thisItem = new ItemBlockPlasticColor(this);
		}
		return thisItem;
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {PROPERTY_META});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(PROPERTY_META, meta);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_META);
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		int met = 0;
		ItemStack itemstack = placer.getHeldItem(hand);
		if(itemstack != null && itemstack.getItem() instanceof ItemBlockPlasticColor)
		{
			met = itemstack.getItemDamage();
		}
		return getStateFromMeta(met);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(thisItem, 1, getMetaFromState(state));
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		NonNullList<ItemStack> ret = NonNullList.create();
		ret.add(new ItemStack(thisItem, 1, getMetaFromState(state)));
		return ret;
	}
	
	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if(tab == PlasticCraft.creativeTab)
			for(int i = 0; i < 16; ++i)
				items.add(new ItemStack(this, 1, i));
	}
	
	
	public class ItemBlockPlasticColor extends ItemBlock
	{

		public ItemBlockPlasticColor(Block block)
		{
			super(block);
			setRegistryName(name);
			this.setHasSubtypes(true);
		}
		
		@Override
		public String getUnlocalizedName(ItemStack stack)
		{
			if(stack != null)
			return super.getUnlocalizedName(stack)+stack.getItemDamage();
			
			return super.getUnlocalizedName();
		}
		
		@Override
		public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
		{
			if(tab == PlasticCraft.creativeTab)
				for(int i = 0; i < 16; ++i)
					items.add(new ItemStack(this, 1, i));
		}
	}

}
