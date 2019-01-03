package javapower.projectplastic.entity.render;

import org.lwjgl.opengl.GL11;

import javapower.projectplastic.core.PlasticCraft;
import javapower.projectplastic.entity.EntityPlasticBoat;
import javapower.projectplastic.entity.model.ModelPlasticBoat;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPlasticBoat extends Render<EntityPlasticBoat>
{
	
	private static final ResourceLocation boatTextures = new ResourceLocation(PlasticCraft.MODID, "textures/entity/plasticboat.png");
    protected ModelBase model;
    
    public RenderPlasticBoat(RenderManager rm)
    {
    	super(rm);
        this.shadowSize = 0.5F;
        this.model = new ModelPlasticBoat();
    }

    /**
     * The render method used in RenderBoat that renders the boat model.
     */
    public void doRender(EntityPlasticBoat entityBoat, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glRotatef(180.0F - par8, 0.0F, 1.0F, 0.0F);
        float f2 = (float)entityBoat.getTimeSinceHit() - par9;
        float f3 = entityBoat.getDamageTaken() - par9;

        if (f3 < 0.0F)
        {
            f3 = 0.0F;
        }

        if (f2 > 0.0F)
        {
            GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0F * (float)entityBoat.getForwardDirection(), 1.0F, 0.0F, 0.0F);
        }

        float f4 = 0.75F;
        GL11.glScalef(f4, f4, f4);
        GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
        this.bindEntityTexture(entityBoat);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.model.render(entityBoat, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityPlasticBoat par1EntityBoat)
    {
        return boatTextures;
    }
    
    public static final Factory FACTORY = new Factory();
	
	public static class Factory implements IRenderFactory<EntityPlasticBoat>
	{
    	@Override
	    public Render<? super EntityPlasticBoat> createRenderFor(RenderManager manager)
    	{
	      return new RenderPlasticBoat(manager);
    	}
    }
}
