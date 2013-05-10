package ejercicio_4;

import java.util.ArrayList;

import ar.edu.unq.tpi.pconc.Channel;

public class EnclosedClient extends Thread {

	private String[] moves;
	private ArrayList<Object> objects;
	private Integer ClientID;
	private Channel<BufferProtocol> bufferProtocolChannel;
	private Channel<String> pushPermissionChannel;
	private Channel<String> popPermissionChannel;
	private Channel<String> confirmationChannel;
	private EnclosedBuffer server;

	public EnclosedClient(EnclosedBuffer server,
			Channel<BufferProtocol> bufferChannel,
			Channel<String> pushPermissionChannel,
			Channel<String> popPermissionChannel,
			Channel<String> confirmationChannel, String[] moves, Integer id) {
		this.setServer(server);
		this.setMoves(moves);
		this.setClientID(id);
		this.setObjects(new ArrayList<Object>());
		this.setBufferProtocolChannel(bufferChannel);
		this.setPushPermissionChannel(pushPermissionChannel);
		this.setPopPermissionChannel(popPermissionChannel);
		this.setConfirmationChannel(confirmationChannel);
	}

	public void run() {
		Integer count = 0;
		for (String move : this.getMoves()) {
			if (move.startsWith("pu")) {
				System.out
						.println("Client " + this.getClientID() + " try push");
				this.getPushPermissionChannel().receive();
				this.getServer().push(
						"Object " + count + " of client " + this.getClientID());
				this.getConfirmationChannel().send("");
				count++;
				System.out.println("Client " + this.getClientID()
						+ " finish push");
			} else {
				System.out.println("Client " + this.getClientID() + " try pop");
				this.getPopPermissionChannel().receive();
				Object object = this.getServer().pop();
				this.getConfirmationChannel().send("");
				this.getObjects().add(object);
				System.out.println("Client " + this.getClientID()
						+ " finish pop");
			}
		}

		if (this.getObjects().size() > 0) {
			System.out.println("Client " + this.getClientID() + " receive "
					+ this.getObjects().size() + " objects");
		} else {
			System.out.println("Client " + this.getClientID()
					+ " don't receive objects");
		}
		if (count > 0) {
			System.out.println("Client " + this.getClientID() + " sent "
					+ count + " objects");
		} else {
			System.out.println("Client " + this.getClientID()
					+ " didn't send objects");
		}
		System.out.println("Client " + this.getClientID() + " ends");
	}

	public Channel<BufferProtocol> getBufferProtocolChannel() {
		return bufferProtocolChannel;
	}

	public void setBufferProtocolChannel(
			Channel<BufferProtocol> bufferProtocolChannel) {
		this.bufferProtocolChannel = bufferProtocolChannel;
	}

	public Channel<String> getPushPermissionChannel() {
		return pushPermissionChannel;
	}

	public void setPushPermissionChannel(Channel<String> pushPermissionChannel) {
		this.pushPermissionChannel = pushPermissionChannel;
	}

	public Channel<String> getPopPermissionChannel() {
		return popPermissionChannel;
	}

	public void setPopPermissionChannel(Channel<String> popPermissionChannel) {
		this.popPermissionChannel = popPermissionChannel;
	}

	public Channel<String> getConfirmationChannel() {
		return confirmationChannel;
	}

	public void setConfirmationChannel(Channel<String> confirmationChannel) {
		this.confirmationChannel = confirmationChannel;
	}

	public String[] getMoves() {
		return moves;
	}

	public void setMoves(String[] moves) {
		this.moves = moves;
	}

	public ArrayList<Object> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<Object> objects) {
		this.objects = objects;
	}

	public Integer getClientID() {
		return ClientID;
	}

	public void setClientID(Integer clientID) {
		ClientID = clientID;
	}

	public EnclosedBuffer getServer() {
		return server;
	}

	public void setServer(EnclosedBuffer server) {
		this.server = server;
	}

}
