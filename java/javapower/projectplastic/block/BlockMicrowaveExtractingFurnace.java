package javapower.projectplastic.block;

import javapower.projectplastic.container.ContainerMicrowaveExtractingFurnace;
import javapower.projectplastic.core.PlasticCraft;
import javapower.projectplastic.gui.GuiMicrowaveExtractingFurnace;
import javapower.projectplastic.tileentity.TileEntityMicrowaveExtractingFurnace;
import javapower.projectplastic.util.IGuiRegister;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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

public class BlockMicrowaveExtractingFurnace extends BlockBaseContainer implements IGuiRegister
{
	
	public static final PropertyDirection FACING = PropertyDirection.create("dir");
    
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

    public BlockMicrowaveExtractingFurnace()
    {
        super(Material.IRON, "microwaveextractingfurnace");
        setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH));
        setHardness(3.5F);
        setResistance(10F);
    }
    
    @Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMicrowaveExtractingFurnace();
	}
	
	// ---------- Block State ----------
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING});
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
		if(tileentity instanceof TileEntityMicrowaveExtractingFurnace)
		{
			InventoryHelper.dropInventoryItems(worldIn, pos, ((TileEntityMicrowaveExtractingFurnace)tileentity));
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
		return new GuiMicrowaveExtractingFurnace((TileEntityMicrowaveExtractingFurnace) tile, player);
	}

	@Override
	public Container getContainer(TileEntity tile, EntityPlayer player)
	{
		return new ContainerMicrowaveExtractingFurnace((TileEntityMicrowaveExtractingFurnace) tile, player);
	}
}
