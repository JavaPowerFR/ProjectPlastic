package javapower.projectplastic.block;

import javapower.projectplastic.container.ContainerMicrowaveOven;
import javapower.projectplastic.core.PlasticCraft;
import javapower.projectplastic.gui.GuiMicrowaveOven;
import javapower.projectplastic.item.PCItems;
import javapower.projectplastic.tileentity.TileEntityMicrowaveOven;
import javapower.projectplastic.util.IGuiRegister;
import javapower.projectplastic.util.ItemStackValue;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMicrowaveOven extends BlockBaseContainer implements IGuiRegister
{
	public static final ItemStackValue[] FUELS_USEABLE = {
			new ItemStackValue(new ItemStack(PCItems.item_resource, 1, 10), 256),
			new ItemStackValue(new ItemStack(Items.GLOWSTONE_DUST), 64),
			new ItemStackValue(new ItemStack(Items.REDSTONE), 32)
			};
	
	public static boolean IsFuel(ItemStack stack)
	{
		for(ItemStackValue isv : BlockMicrowaveOven.FUELS_USEABLE)
			if(isv.itemstack.isItemEqual(stack))
				return true;
		
		return false;
	}
	
	public static int FuelValue(ItemStack stack)
	{
		for(ItemStackValue isv : BlockMicrowaveOven.FUELS_USEABLE)
			if(isv.itemstack.isItemEqual(stack))
				return isv.value;
		return 0;
	}
	
	public static final PropertyDirection FACING = PropertyDirection.create("dir");
    public static final PropertyBool IS_ACTIVATE = PropertyBool.create("isactiv");
    
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

    public BlockMicrowaveOven()
    {
        super(Material.IRON, "microwaveoven");
        setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(IS_ACTIVATE, false));
        setHardness(3.5F);
        setResistance(10F);
    }
    
    @Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMicrowaveOven();
	}
 
	
	// ---------- Block State ----------
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING, IS_ACTIVATE});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}
	
	// ---------- Event ----------
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if(!worldIn.isRemote)
		{
			worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
		}
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!playerIn.isSneaking())
			playerIn.openGui(PlasticCraft.INSTANCE, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		
		return true;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if(tileentity instanceof TileEntityMicrowaveOven)
		{
			InventoryHelper.dropInventoryItems(worldIn, pos, ((TileEntityMicrowaveOven)tileentity));
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	// ---------- Render ----------
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	// ---------- Player Inteface ----------

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(TileEntity tile, EntityPlayer player)
	{
		return new GuiMicrowaveOven((TileEntityMicrowaveOven) tile, player);
	}

	@Override
	public Container getContainer(TileEntity tile, EntityPlayer player)
	{
		return new ContainerMicrowaveOven((TileEntityMicrowaveOven) tile, player);
	}
}
