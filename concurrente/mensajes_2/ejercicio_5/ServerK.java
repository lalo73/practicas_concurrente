package ejercicio_5;

import ar.edu.unq.tpi.pconc.Channel;

public class ServerK extends Thread{
	
	private Channel<String> protocolChannel;
	private Channel<String> messageChannel;
	
	public ServerK(Channel<String> protocolChannel, Channel<String> messageChannel){
		this.setProtocolChannel(protocolChannel);
		this.setMessageChannel(messageChannel);
	}
	
	public void run(){
		this.getProtocolChannel().receive();
		String code = this.getCode();
		this.getMessageChannel().send(code);
	}
	private String getCode(){
		return "12345"; // =P
	}
	public Channel<String> getProtocolChannel() {
		return protocolChannel;
	}
	public void setProtocolChannel(Channel<String> protocolChannel) {
		this.protocolChannel = protocolChannel;
	}
	public Channel<String> getMessageChannel() {
		return messageChannel;
	}
	public void setMessageChannel(Channel<String> messageChannel) {
		this.messageChannel = messageChannel;
	}
	

}
