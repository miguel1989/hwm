package hwm.controller;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class MyMessageOut extends MyMessage {
	public LocalDateTime created = LocalDateTime.now();

	public MyMessageOut(MyMessage msg) {
		this.from = msg.from;
		this.text = msg.text;
	}
}
