package javapower.projectplastic.item;

import javapower.projectplastic.core.PlasticCraft;
import javapower.projectplastic.proxy.ClientProxy;
import javapower.projectplastic.proxy.ResourceLocationRegister;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNightvisionGoggles extends ItemArmor
{
	public ItemNightvisionGoggles()
	{
		super(ArmorMaterial.IRON, 1, EntityEquipmentSlot.HEAD);
		
		setCreativeTab(PlasticCraft.creativeTab);
		setRegistryName("nightvisiongoggles");
		setUnlocalizedName("nightvisiongoggles");
	}
	
	@Override
	public EntityEquipmentSlot getEquipmentSlot(ItemStack stack)
	{
		return EntityEquipmentSlot.HEAD;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		return PlasticCraft.MODID+":textures/models/armor/nightvisiongoggles_layer_1.png";
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution scaledRes, float partialTicks)
	{
		GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();
        ClientProxy.minecraft.getTextureManager().bindTexture(ResourceLocationRegister.texture_overlay_guinightvision);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0.0D, (double)scaledRes.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
        bufferbuilder.pos((double)scaledRes.getScaledWidth(), (double)scaledRes.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos((double)scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public static Potion potion = Potion.getPotionById(16);
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		player.addPotionEffect(new PotionEffect(potion, 220, 10, false, false));
	}

}
