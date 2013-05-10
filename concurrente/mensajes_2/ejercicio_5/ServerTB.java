package ejercicio_5;

import java.util.HashMap;
import ar.edu.unq.tpi.pconc.Channel;
import ar.edu.unq.tpi.pconc.Utils;

public class ServerTB extends BasicServerT {
	private Channel<TBProtocol> protocolChannel;
	private Channel<String> messageChannel;
	private HashMap<Integer, Channel<String>> clientServiceHash;
	private Channel<String> permissionChannel;

	public void run() {
		this.getPermissionChannel().send("");
		while (true) {
			String message;
			Integer clientID;
			switch (this.getProtocolChannel().receive()) {
			case join:
				this.putNewService();
				break;
			case send:
				clientID = Utils.parseInt(this.getMessageChannel().receive());
				message = this.getMessageChannel().receive();
				Channel<String> channel = this.getClientServiceHash().get(
						clientID);
				String code = this.codificar(message);
				channel.send(code);
			case change:
				this.putNewService();
			default:
				break;
			}
		}
	}

	private void putNewService() {
		Integer clientID = Utils.parseInt(this.getMessageChannel().receive());
		Integer serviceChannelID = Utils.parseInt(this.getMessageChannel()
				.receive());
		this.getClientServiceHash().put(clientID,
				new Channel<String>(serviceChannelID));
	}

	public Channel<String> getMessageChannel() {
		return messageChannel;
	}

	public void setMessageChannel(Channel<String> messageChannel) {
		this.messageChannel = messageChannel;
	}

	public HashMap<Integer, Channel<String>> getClientServiceHash() {
		return clientServiceHash;
	}

	public void setClientServiceHash(
			HashMap<Integer, Channel<String>> clientServiceHash) {
		this.clientServiceHash = clientServiceHash;
	}

	public Channel<TBProtocol> getProtocolChannel() {
		return protocolChannel;
	}

	public void setProtocolChannel(Channel<TBProtocol> protocolChannel) {
		this.protocolChannel = protocolChannel;
	}

	public Channel<String> getPermissionChannel() {
		return permissionChannel;
	}

	public void setPermissionChannel(Channel<String> permissionChannel) {
		this.permissionChannel = permissionChannel;
	}

}
