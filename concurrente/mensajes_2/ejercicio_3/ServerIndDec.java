package ejercicio_3;

import ar.edu.unq.tpi.pconc.Channel;

public class ServerIndDec extends Thread {

	private Channel<IncDecProtocol> protocolChannel;
	private Channel<String> confirmationChannel;
	private Integer count;
	private Integer decCount;

	public ServerIndDec(Channel<IncDecProtocol> protocolChannel,
			Channel<String> confirmationChannel) {
		this.setProtocolChannel(protocolChannel);
		this.setConfirmationChannel(confirmationChannel);
		this.setCount(0);
		this.setDecCount(0);
	}

	public void run() {
		while (true) {
			switch (this.getProtocolChannel().receive()) {
			case inc:
				if (this.getDecCount() == 0) {
					this.incCount();
				} else {
					this.decDecCount();
					this.getConfirmationChannel().send("");
				}
				break;
			case dec:
				if (this.getCount() == 0) {
					this.incDecCount();
				} else {
					this.decCount();
					this.getConfirmationChannel().send("");
				}
			default:
				break;
			}
		}
	}

	public static void main(String[] args) {
		Channel<IncDecProtocol> protocolChannel = new Channel<IncDecProtocol>(1);
		Channel<String> confirmationChannel = new Channel<String>(2);
		ServerIndDec server = new ServerIndDec(protocolChannel, confirmationChannel);
		ClientIncDec client1 = new ClientIncDec(protocolChannel, confirmationChannel, "inc inc dec".split(" "), 1);
		ClientIncDec client2 = new ClientIncDec(protocolChannel, confirmationChannel, "dec dec dec".split(" "), 2);
		server.start();
		client1.start();
		client2.start();
	}
	
	public void printCounsStatus(){
		System.out.println("count = " + this.getCount());
		System.out.println("dec count = " + this.getDecCount());
	}

	public void decDecCount() {
		this.setDecCount(this.getDecCount() - 1);
		this.printCounsStatus();
	}

	public void incDecCount() {
		this.setDecCount(this.getDecCount() + 1);
		this.printCounsStatus();
	}

	public void decCount() {
		this.setCount(this.getCount() - 1);
		this.printCounsStatus();
		this.printCounsStatus();
	}

	public void incCount() {
		this.setCount(this.getCount() + 1);
		this.printCounsStatus();
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getDecCount() {
		return decCount;
	}

	public void setDecCount(Integer decCount) {
		this.decCount = decCount;
	}

}
