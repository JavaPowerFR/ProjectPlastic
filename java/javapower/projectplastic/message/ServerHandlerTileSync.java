package javapower.projectplastic.message;

import javapower.projectplastic.util.ITileUpdate;
import javapower.projectplastic.util.NetworkUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerHandlerTileSync implements IMessageHandler<NetworkTileSync, IMessage>
{
	@Override
	public IMessage onMessage(NetworkTileSync message, MessageContext ctx)
	{
		int lock_key = message.nbt_inf.getInteger("lk");
		if(lock_key == 1)
		{
			String te_name = message.nbt_inf.getString("te");
			TileEntity the_te = message.env.GetTileEntity();
			if(the_te.getClass().getName().equalsIgnoreCase(te_name) && the_te instanceof ITileUpdate)
			{
				((ITileUpdate)the_te).addOrRemovePlayer(ctx.getServerHandler().player, true);
				
				NBTTagCompound nbt = new NBTTagCompound();
				((ITileUpdate)the_te).onPlayerOpenGUISendData(nbt, ctx.getServerHandler().player);
				NetworkUtils.sendToPlayerTheData(the_te, nbt, ctx.getServerHandler().player);
			}
		}
		else if(lock_key == 2)
		{
			String te_name = message.nbt_inf.getString("te");
			TileEntity the_te = message.env.GetTileEntity();
			if(the_te.getClass().getName().equalsIgnoreCase(te_name) && the_te instanceof ITileUpdate)
			{
				((ITileUpdate)the_te).reciveDataFromClient(message.nbt, ctx.getServerHandler().player);
			}
		}
		else if(lock_key == 3)
		{
			TileEntity the_te = message.env.GetTileEntity();
			if(the_te instanceof ITileUpdate)
			{
				((ITileUpdate)the_te).addOrRemovePlayer(ctx.getServerHandler().player, false);
			}
		}
		
		return null;
	}

}