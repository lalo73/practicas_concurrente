package ejercicio_4;

import java.util.ArrayList;
import ar.edu.unq.tpi.pconc.Channel;

public class EnclosedBuffer extends Thread {
	private ArrayList<Object> objects;
	private Channel<BufferProtocol> bufferProtocolChannel;
	private Channel<String> pushPermissionChannel;
	private Channel<String> popPermissionChannel;
	private Channel<String> confirmationChannel;
	private Integer limit;

	public EnclosedBuffer(Channel<BufferProtocol> bufferChannel,
			Channel<String> pushPermissionChannel,
			Channel<String> popPermissionChannel,
			Channel<String> confirmationChannel, Integer limit) {
		this.setBufferProtocolChannel(bufferChannel);
		this.setPushPermissionChannel(pushPermissionChannel);
		this.setPopPermissionChannel(popPermissionChannel);
		this.setConfirmationChannel(confirmationChannel);
		this.setLimit(limit);
		this.setObjects(new ArrayList<Object>());
	}

	public static void main(String[] args) {	
		Channel<BufferProtocol> bufferChannel = new Channel<BufferProtocol>(1);
		Channel<String> pushPermissionChannel = new Channel<String>(2);
		Channel<String> popPermissionChannel = new Channel<String>(3);
		Channel<String> confirmationChannel = new Channel<String>(4);
		EnclosedBuffer buffer = new EnclosedBuffer(bufferChannel,
				pushPermissionChannel, popPermissionChannel,
				confirmationChannel, 10);
		EnclosedClient client1 = new EnclosedClient(buffer, bufferChannel,
				pushPermissionChannel, popPermissionChannel,
				confirmationChannel, "pop push push push".split(" "), 2);
		EnclosedClient client2 = new EnclosedClient(buffer, bufferChannel,
				pushPermissionChannel, popPermissionChannel,
				confirmationChannel, "push push pop push".split(" "), 1);
		if (0 == buffer.getObjects().size()) {
			throw new RuntimeException("This class fails!");
		}
		// This line are there to fails because this don't run!
		buffer.start();
		client1.start();
		client2.start();

	}

	public void push(Object object) {
		this.getObjects().add(this.getObjects().size(), object);
	}

	public Object pop() {
		Object o = this.getObjects().get(0);
		this.getObjects().remove(o);
		return o;
	}

	public void run() {	
		this.getPushPermissionChannel().send("");
		while (true) {
			switch (this.getBufferProtocolChannel().receive()) {
			case push:
				if (this.getObjects().size() != this.getLimit()) {
					this.getConfirmationChannel().receive();
					this.getPopPermissionChannel().send("");
					if (this.getObjects().size() != this.getLimit()) {
						this.getPushPermissionChannel().send("");
					}
				}

				break;
			case pop:
				if (this.getObjects().size() != 0) {
					this.getConfirmationChannel().receive();
					this.getPushPermissionChannel().send("");
				}
			default:
				break;
			}
		}
	}

	public ArrayList<Object> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<Object> objects) {
		this.objects = objects;
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

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Channel<String> getConfirmationChannel() {
		return confirmationChannel;
	}

	public void setConfirmationChannel(Channel<String> confirmationChannel) {
		this.confirmationChannel = confirmationChannel;
	}

}
