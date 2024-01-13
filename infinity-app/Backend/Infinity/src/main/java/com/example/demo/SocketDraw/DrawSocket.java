package com.example.demo.SocketDraw;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@ServerEndpoint(value = "/canvas/{username}")
public class DrawSocket {

	private static CanvasRepository canvasRepo;

//		// Store all socket session and their corresponding username.
	private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
	private static Map<String, Session> usernameSessionMap = new Hashtable<>();

	private final Logger logger = LoggerFactory.getLogger(DrawSocket.class);

	@Autowired
	public void setCanvasRepository(CanvasRepository repo) {
		canvasRepo = repo; // we are setting the static variable
	}

	/**
	 * This method is called when the connection between the server and client is
	 * established. The "session" object is the representation of the client. There
	 * are various methods you can call on it. In this particular example, we are
	 * going to be sending a string saying "Connected" to inform the client that the
	 * connection was established successfully.
	 * 
	 * @param session
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException {

		/*
		 * BasicRemote sends the message synchronously. This means that the message will
		 * be sent on this thread. AsyncRemote relies on the RemoteEndpoint.Async
		 * interface and sends messages asynchronously. We will use BasicRemote as we
		 * have a pretty simple application which only sends the message back to a
		 * SINGLE client.
		 * 
		 * Another thing to note is that it is probably a good idea to create a helper
		 * method that sends the message for you. Rather than calling this long method
		 * to send a simple string. An example is provided further below. This will
		 * prove extra helpful when broadcasting messages to multiple sessions.
		 */

		logger.info("Entered into Open");
		// store connecting user information
		sessionUsernameMap.put(session, username);
		usernameSessionMap.put(username, session);

		// Send chat history to the newly connected user
		sendMessageToPArticularUser(username, getCanvasHistory());
		
		// SIDE NOTE \\ this part could be helpful
		// broadcast that new user joined
		String message = "User:" + username + " has Joined the Canvas";
		broadcast(message);
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");
		// remove the user connection information
		String username = sessionUsernameMap.get(session);
		sessionUsernameMap.remove(session);
		usernameSessionMap.remove(username);
		// SIDE NOTE \\ this part could be helpful
		// broadcase that the user disconnected
		String message = username + "disconnected";
		broadcast(message);
	}

	@OnMessage
	public void onMessage(Session session, String canvas) throws IOException {
		// Handle new messages
		logger.info("Entered into Canvas: " + canvas);
		String username = sessionUsernameMap.get(session);
		broadcast(username + ": " + canvas);
		// Saving chat history to repository
		canvasRepo.save(new Canvas(username, canvas));
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here

//			// If the app crashes/closes unexpectedly then the websocket will not get closed
//			// properly and will throw an error
//			// We are ignoring that exception in this situation because it clutters the
//			// console
		if (!throwable.getClass().getSimpleName().equals("EOFException")) {
			logger.debug("An error has occurred");
			throwable.printStackTrace();
		}
		logger.info("Entered into Error");
		throwable.printStackTrace();
	}

	/**
	 * Helper method to send a string to the given client
	 * 
	 * @param message The message to be sent
	 */
	private void sendMessageToPArticularUser(String username, String message) {
		try {
			usernameSessionMap.get(username).getBasicRemote().sendText(message);
		} catch (IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	/**
	 * Helper method to send a string to the given chat
	 * 
	 * @param message The message to be sent
	 */
	private void broadcast(String canvas) {
		sessionUsernameMap.forEach((session, username) -> {
			try {
				session.getBasicRemote().sendText(canvas);
			} catch (IOException e) {
				logger.info("Exception: " + e.getMessage().toString());
				e.printStackTrace();
			}
		});
	}

	// Gets the Chat history from the repository
	private String getCanvasHistory() {
		List<Canvas> CanvasLog = canvasRepo.findAll();
		// convert the list to a string
		StringBuilder sb = new StringBuilder();
		if (CanvasLog != null && CanvasLog.size() != 0) {
			for (Canvas canvas : CanvasLog) {
				sb.append(canvas.getUserName() + ": " + canvas.getContent() + "\n");
			}
		}
		return sb.toString();
	}
}
