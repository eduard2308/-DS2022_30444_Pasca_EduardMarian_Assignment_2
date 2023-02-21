package ro.tuc.ds2020.websockets;

import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Message {

    public String text;
    private String sender;
    private boolean isTyping;
}
