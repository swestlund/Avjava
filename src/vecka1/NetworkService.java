package vecka1;

// public så att alla kan nå klassen
public class NetworkService {

//private boolean i bakgrunden
private boolean useEpoll = Epoll.isAvailable();

// privat för att generera serverID
private UUID serverId = UUID.randomUUID();

// private variable declaration?
private String serverAddress = "";

// private så ingen ändrar på klassen
private Class<SocketChannel> channelClass = useEpoll ?
            EpollSocketChannel.class
            :
            NioSocketChannel.class;

// public så att användaren kan hämta networkinfo
public EventLoopGroup group = useEpoll ?
            ServerNetworkIo.EPOLL_CHANNEL.get()
            :
            ServerNetworkIo.DEFAULT_CHANNEL.get();



// private för att den sätter värdet till null på variabeln channel som ska innehålla ett objekt av typ Channel
private Channel channel = null;



//public för att användaren ska kunna köra connect-metoden, kanske sätta som private och ha en annan
// metod som kallar på den som är public??
public void connect() {
        try {
            LOGGER.info("Trying to connect .")
            this.channel = Bootstrap()
                    .group(group)
                    .channel(channelClass)
                    .handler(this)
                    .connect("127.0.0.1", 25580)
                    .sync()
                    .channel()
            serverAddress = "${Server.serverIp}:${Server.serverPort}"
            channel.writeAndFlush(new HandshakePayload(serverId,
                    serverAddress))

            LOGGER.info("Connected as ${serverAddress}.")
        } catch (Exception exception) {
            LOGGER.error("Could not connect, retrying in 5s")
            reconnect();
        }
    }

// private för att metoden kallas från connect();
private void reconnect() {
        executor.schedule(() -> {
                connect();
        }), 5, TimeUnit.SECONDS);
    }


//public för inget pekar på denna
public void send(OutboundPayload payload) {
        if (channel != null && channel.isOpen())
            channel.write(payload)
        else {
            throw new IllegalStateException("Channel not open.");
        }
    }
}