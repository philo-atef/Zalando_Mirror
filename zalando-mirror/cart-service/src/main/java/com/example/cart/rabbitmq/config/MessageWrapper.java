package com.example.cart.rabbitmq.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

@Data
@JsonSerialize
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor
public class MessageWrapper {
    @Setter
    @Getter
   private String type;
    @Setter @Getter private Object payload;

    @Override
    public String toString() {
        return "MessageWrapper{" +
                "type='" + type + '\'' +
                ", payload=" + payload +
           '}';
}
}