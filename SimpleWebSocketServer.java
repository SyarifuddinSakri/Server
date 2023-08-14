import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SimpleWebSocketServer extends WebSocketServer {

    public Map<String, WebSocket> clients;

    public SimpleWebSocketServer(int port) {
        super(new InetSocketAddress(port));
        clients = new HashMap<>();
    }

    @Override
    public void onOpen(WebSocket connection, ClientHandshake handshake) {
        System.out.println("New connection established: " + connection.getRemoteSocketAddress());
        // Store the WebSocket connection in the map
        clients.put(connection.getRemoteSocketAddress().toString(), connection);
    }

    @Override
    public void onClose(WebSocket connection, int code, String reason, boolean remote) {
        System.out.println("connection closed: " + connection.getRemoteSocketAddress());
        // Remove the WebSocket connection from the map
        clients.remove(connection.getRemoteSocketAddress().toString());
    }

    @Override
    public void onMessage(WebSocket connection, String message) {
        System.out.println("Received message from " + connection.getRemoteSocketAddress() + ": " + message);
        // Process the received message here
        // You can implement your own logic based on the received command
        
    }

    @Override
    public void onError(WebSocket connection, Exception ex) {
        System.err.println("WebSocket error occurred on connection " + connection.getRemoteSocketAddress() + ":");
        ex.printStackTrace();
    }
    
    public void sendMessage(String message) {
    	for (WebSocket i: clients.values()) {
    		i.send(message);
    	}
    		
    }
    
    public static void main(String[] args) {
        int port = 666; // Replace with your desired port number
        SimpleWebSocketServer server = new SimpleWebSocketServer(port);
        server.start();
        System.out.println("WebSocket server started on port " + port);
        
        JButton button = new JButton("Click me");
        JPanel panel = new JPanel();
        panel.add(button);
        JFrame frame = new JFrame();
        frame.setSize(400,400);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
        
        button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("makan babi");
				   server.sendMessage("minum");
			}
		});
     
        
    }
}
