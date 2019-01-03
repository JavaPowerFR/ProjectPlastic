package javapower.projectplastic.proxy;

import javapower.projectplastic.core.PlasticCraft;
import net.minecraft.util.ResourceLocation;

public class ResourceLocationRegister
{
	public static ResourceLocation texture_overlay_guinightvision;
	public static ResourceLocation texture_gui_microwaveoven;
	public static ResourceLocation texture_gui_guiextractor;
	public static ResourceLocation texture_gui_guiextractor2;
	public static ResourceLocation texture_gui_guiextractor3;
	public static ResourceLocation textrue_gui_jei_recipe;
	
	public static void register()
	{
		texture_overlay_guinightvision = resource("textures/guis/guinightvisionsdk.png");
		texture_gui_microwaveoven = resource("textures/guis/guimicrowave.png");
		texture_gui_guiextractor = resource("textures/guis/guiextractor.png");
		texture_gui_guiextractor2 = resource("textures/guis/guiextractor2.png");
		texture_gui_guiextractor3 = resource("textures/guis/guiextractor3.png");
		textrue_gui_jei_recipe = resource("textures/guis/jei_recipe.png");
	}
	
	private static ResourceLocation resource(String target)
	{
		return new ResourceLocation(PlasticCraft.MODID, target);
	}
}
