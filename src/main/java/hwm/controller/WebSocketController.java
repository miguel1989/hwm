package hwm.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

public class WebSocketController {

	@MessageMapping("/war")
	@SendTo("/topic/messages")
	public MyMessageOut send(MyMessage myMessage) throws Exception {
		return new MyMessageOut(myMessage);
	}
}
