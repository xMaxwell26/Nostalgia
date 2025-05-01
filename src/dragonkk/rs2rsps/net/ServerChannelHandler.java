package dragonkk.rs2rsps.net;

import dragonkk.rs2rsps.io.InStream;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.codec.ConnectionHandler;
import dragonkk.rs2rsps.net.codec.ConnectionWorker;
import dragonkk.rs2rsps.util.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;


@ChannelPipelineCoverage("all")
public class ServerChannelHandler extends SimpleChannelHandler {

    public ServerChannelHandler() {
        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.getPipeline().addLast("handler", this);
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.bind(new InetSocketAddress(43594));
        Logger.log(this, "Loaded port 43594");
    }

    @Override
    public final void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        Channel channel = e.getChannel();
        int count = 0;
        String host = channel.getRemoteAddress().toString();
        host = host.substring(1, host.indexOf(':'));
        //Used against flooders.
        if (World.PermbannedIpsContain(host)) {
            e.getChannel().close();
            return;
        }
        for (Player player : World.getPlayers()) {
            String playerHost = player.getConnection().getChannel().getRemoteAddress().toString();
            playerHost = playerHost.substring(1, playerHost.indexOf(':'));
            if (playerHost.equals(host)) {
                if (++count == 6) {
                    channel.close();
                    return;
                }
            }
        }
        ConnectionHandler connection = new ConnectionHandler(channel);
        ctx.setAttachment(connection);
    }

    @Override
    public final void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        ConnectionHandler p = (ConnectionHandler) ctx.getAttachment();
        if (p != null) {
            World.unRegisterConnection(p);
            ctx.setAttachment(null);
        }
    }

    @Override
    public final void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent ee) throws Exception {
    }

    @Override
    public final void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        ConnectionHandler p = (ConnectionHandler) ctx.getAttachment();
        ChannelBuffer buf = (ChannelBuffer) e.getMessage();
        buf.markReaderIndex();
        int avail = buf.readableBytes();
        if (avail > 5000) {
            e.getChannel().close();
            return;
        }

        byte[] b = new byte[avail];
        // in.addBytes(b, 0, b.length);
        buf.readBytes(b);
        InStream in = new InStream(b);
        if (p.getPlayer() == null) {
            ConnectionWorker.run(p, in);
        } else {
            Packets.run(p, in);
        }
    }

}
