package net.trentv.musicalenergy;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.trentv.musicalenergy.common.element.Element;
import net.trentv.musicalenergy.common.item.ItemInstrument;

public class MusicalEnergyPacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MusicalEnergy.MODID);

	public static void init()
	{
		INSTANCE.registerMessage(SpellMessageHandler.class, SpellMessage.class, 0, Side.SERVER);
	}

	public static class SpellMessage implements IMessage
	{
		private int[] spellIDs;

		public SpellMessage()
		{
			this.spellIDs = new int[0];
		}

		public SpellMessage(int... spellIDs)
		{
			this.spellIDs = spellIDs;
		}

		@Override
		public void fromBytes(ByteBuf buf)
		{
			spellIDs = new int[buf.readableBytes() >> 2];
			for (int i = 0; i < spellIDs.length; i++)
			{
				spellIDs[i] = buf.readInt();
			}
		}

		@Override
		public void toBytes(ByteBuf buf)
		{
			for (int i = 0; i < spellIDs.length; i++)
			{
				buf.writeInt(spellIDs[i]);
			}
		}
	}

	public static class SpellMessageHandler implements IMessageHandler<SpellMessage, IMessage>
	{
		@Override
		public IMessage onMessage(SpellMessage message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;

			player.getServerWorld().addScheduledTask(() ->
			{
				ItemStack heldItem = player.getActiveItemStack();
				if (heldItem.getItem() instanceof ItemInstrument)
				{
					ItemInstrument instrument = (ItemInstrument) heldItem.getItem();
					Element[] newElements = new Element[message.spellIDs.length];
					for (int i = 0; i < newElements.length; i++)
					{
						newElements[i] = Element.ELEMENTS.get(message.spellIDs[i]);
					}
					instrument.doot(newElements, player, player.getEntityWorld(), heldItem);
				}

			});
			return null;
		}
	}
}
