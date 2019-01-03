package javapower.projectplastic.message;

import javapower.projectplastic.proxy.ClientProxy;
import javapower.projectplastic.util.IGUITileSync;
import javapower.projectplastic.util.NetworkUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientHandlerTileSync implements IMessageHandler<NetworkTileSync, IMessage>
{
	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(NetworkTileSync message, MessageContext ctx)
	{
		if(ClientProxy.minecraft.currentScreen instanceof IGUITileSync)
		{
			((IGUITileSync)ClientProxy.minecraft.currentScreen).reciveDataFromServer(message.nbt);
		}
		else
		{
			NetworkUtils.sendToServerPlayerStopOpenGUI(message.env);
		}
		return null;
	}

}