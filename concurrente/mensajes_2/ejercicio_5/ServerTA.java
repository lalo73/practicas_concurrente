package ejercicio_5;

import ar.edu.unq.tpi.pconc.Channel;

public class ServerTA extends BasicServerT {

	private Channel<String> channelToR;
	private Channel<String> messageChannel;
	private Channel<String> permissionChannel;

	public void run() {
		this.getPermissionChannel().send("");
		while (true) {
			String message = this.getMessageChannel().receive();
			String code = this.codificar(message);
			this.getChannelToR().send(code);
			this.getPermissionChannel().send("");
		}
	}

	public Channel<String> getChannelToR() {
		return channelToR;
	}

	public void setChannelToR(Channel<String> channelToR) {
		this.channelToR = channelToR;
	}

	public Channel<String> getMessageChannel() {
		return messageChannel;
	}

	public void setMessageChannel(Channel<String> messageChannel) {
		this.messageChannel = messageChannel;
	}

	public Channel<String> getPermissionChannel() {
		return permissionChannel;
	}

	public void setPermissionChannel(Channel<String> permissionChannel) {
		this.permissionChannel = permissionChannel;
	}

}
