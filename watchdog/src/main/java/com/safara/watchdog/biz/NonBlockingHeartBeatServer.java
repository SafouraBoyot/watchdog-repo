package com.safara.watchdog.biz;

/**
 * Created by safoura
 */
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import com.safara.watchdog.common.ConfigFileLoader;
import com.safara.watchdog.common.HeartBeatAdapter;
import com.safara.watchdog.common.Heartbeat;
import com.safara.watchdog.common.LogUtil;
import com.safara.watchdog.common.XmlMarshaler;
import com.safara.watchdog.dto.HeartBeatDto;
import com.safara.watchdog.exception.UnableToCloseSocket;

public class NonBlockingHeartBeatServer {

    private static ServerSocketChannel serverSocketChannel;
    private static Integer port;
    private static Selector selector;
    private static Logger log;

    public static void startServer() {
        log = new LogUtil(NonBlockingHeartBeatServer.class.getName()).getLogger();
        log.log(Level.INFO, "startServer");
        port = Integer.parseInt(new ConfigFileLoader("server.properties").getValue("server.port"));
        ServerSocket serverSocket;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocket = serverSocketChannel.socket();
            InetSocketAddress netAddress = new InetSocketAddress(port);
            serverSocket.bind(netAddress);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException ioEx) {
            log.log(Level.SEVERE, "NonBlockingHeartBeatServer.startServer.IOException", ioEx);
            ioEx.printStackTrace();
            System.exit(1);
        }
        processConnections();
    }

    private static void processConnections() {
        do {
            try {
                int numKeys = selector.select();
                if (numKeys > 0) {
                    Set eventKeys = selector.selectedKeys();
                    Iterator keyCycler = eventKeys.iterator();
                    synchronized (keyCycler) {
                        while (keyCycler.hasNext()) {
                            SelectionKey key = (SelectionKey) keyCycler.next();
                            int keyOps = key.readyOps();
                            if ((keyOps & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                                acceptConnection(key);
                                continue;
                            }
                            if ((keyOps & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                                acceptData(key);
                            }
                        }
                    }
                }
            } catch (IOException ioEx) {
                log.log(Level.SEVERE, "NonBlockingHeartBeatServer.processConnections.IOException", ioEx);
                ioEx.printStackTrace();
                System.exit(1);
            }
        } while (true);
    }

    private static void acceptConnection(SelectionKey key) throws IOException {

        SocketChannel socketChannel;
        Socket socket;
        socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socket = socketChannel.socket();
        log.log(Level.INFO, "Connection on " + socket + ".");
        socketChannel.register(selector, SelectionKey.OP_READ);
        selector.selectedKeys().remove(key);

    }

    private static void acceptData(SelectionKey key) throws IOException {

        SocketChannel socketChannel;
        Socket socket;
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        socketChannel = (SocketChannel) key.channel();
        buffer.clear();
        int numBytes;
        socket = socketChannel.socket();

        numBytes = socketChannel.read(buffer);

        if (numBytes == -1) {
            key.cancel();
            log.log(Level.INFO, "\nClosing socket " + socket + "…");
            closeSocket(socket);
        } else {

            log.log(Level.INFO, " buffer.position():" + buffer.position());
            System.out.println(numBytes);
            byte[] b = new byte[buffer.position()];
            for (int i = 0; i < buffer.position(); i++) {
                b[i] = buffer.get(i);
            }

            InputStream is = new ByteArrayInputStream(b);
            try {
                Heartbeat heartbeat = XmlMarshaler.marshaler(is);
                log.log(Level.INFO, "heartbeat = " + heartbeat);
                HeartBeatDto heartBeatDto = HeartBeatAdapter.heartBeatDtoAdapter(heartbeat);
                log.log(Level.INFO, "heartbeatDto = " + heartBeatDto);

                HeartBeatService heartBeatService = new HeartBeatService();
                heartBeatService.doHeartBeat(heartBeatDto);

            } catch (JAXBException e) {
                log.log(Level.SEVERE, "NonBlockingHeartBeatServer.acceptData.JAXBException", e);
                e.printStackTrace();
            }
            log.log(Level.INFO, numBytes + " bytes read.");
        }

        selector.selectedKeys().remove(key);
    }

    private static void closeSocket(Socket socket) throws UnableToCloseSocket {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ioEx) {
            log.log(Level.INFO, "*** Unable to close socket! ***");
            log.log(Level.SEVERE, "NonBlockingHeartBeatServer.closeSocket.IOException", ioEx);
            throw new UnableToCloseSocket();

        }
    }

}
