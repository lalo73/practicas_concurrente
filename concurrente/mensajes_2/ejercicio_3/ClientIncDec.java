package ejercicio_3;

import ar.edu.unq.tpi.pconc.Channel;

public class ClientIncDec extends Thread {

	private String[] moves;
	private Channel<IncDecProtocol> protocolChannel;
	private Channel<String> confirmationChannel;
	private Integer clientID;

	public ClientIncDec(Channel<IncDecProtocol> protocolChannel, Channel<String> confirmationChannel, String[] moves, Integer clientID) {
		this.setMoves(moves);
		this.setProtocolChannel(protocolChannel);
		this.setConfirmationChannel(confirmationChannel);
		this.setClientID(clientID);
	}

	public void run() {		
			for (String move : this.getMoves()) {
				if(move.startsWith("i")){
					System.out.println(this.getClientID() + " are Sending inc command");
					this.getProtocolChannel().send(IncDecProtocol.inc);					
					System.out.println(this.getClientID() +" sent inc command");
				}else{
					System.out.println(this.getClientID() + " are Sending dec command");
					this.getProtocolChannel().send(IncDecProtocol.dec);
					this.getConfirmationChannel().receive();
					System.out.println(this.getClientID() + " sent dec command");
				}
			}		
	}

	public String[] getMoves() {
		return moves;
	}

	public void setMoves(String[] moves) {
		this.moves = moves;
	}

	public Channel<IncDecProtocol> getProtocolChannel() {
		return protocolChannel;
	}

	public void setProtocolChannel(Channel<IncDecProtocol> protocolChannel) {
		this.protocolChannel = protocolChannel;
	}

	public Channel<String> getConfirmationChannel() {
		return confirmationChannel;
	}

	public void setConfirmationChannel(Channel<String> confirmationChannel) {
		this.confirmationChannel = confirmationChannel;
	}

	public Integer getClientID() {
		return clientID;
	}

	public void setClientID(Integer clientID) {
		this.clientID = clientID;
	}

}
