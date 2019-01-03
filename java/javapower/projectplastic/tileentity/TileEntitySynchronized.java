package javapower.projectplastic.tileentity;

import java.util.ArrayList;
import java.util.List;

import javapower.projectplastic.util.ITileUpdate;
import javapower.projectplastic.util.NetworkUtils;
import javapower.projectplastic.util.Tools;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public abstract class TileEntitySynchronized extends TileEntityBase implements ITileUpdate, ITickable
{
	public List<EntityPlayerMP> players = new ArrayList<EntityPlayerMP>();

	@Override
	public void addOrRemovePlayer(EntityPlayerMP player, boolean isAdded)
	{
		if(isAdded)
		{
			if(players.isEmpty())
			{
				players.add(player);
			}
			else
			{
				if(!Tools.PlayerIsOnList(players, player))
				{
					players.add(player);
				}
			}
		}
		else
		{
			Tools.RemovePlayerOnList(players, player);
		}
	}

	@Override
	public void update()
	{
		if(!players.isEmpty())
		{
			NBTTagCompound update = updateData();
			if(update != null)
			{
				for(EntityPlayerMP pl : players)
				{
					if(pl != null)
					NetworkUtils.sendToPlayerTheData(this, update, pl);
				}
			}
		}
	}

}
