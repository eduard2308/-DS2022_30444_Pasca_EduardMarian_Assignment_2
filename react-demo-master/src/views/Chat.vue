<template>
  <div class="chat-container">
    <div class="chat-messages">
      <div class="message" v-for="message in messages" :key="message.sender">
        <div v-if="message.isTyping && message.text != null">
          <span class="text">{{ message.sender }} is typing...</span>
        </div>
        <div v-else>
          <span class="sender">{{ message.sender }}:</span>
          <span class="text">{{ message.text }}</span>
        </div>
      </div>
    </div>
    <div class="chat-input">
      <input
        v-model="message"
        @input="startTyping"
        placeholder="Enter message here"
      />
      <button @click="sendMessage(message)">Send</button>
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "stompjs";

export default {
  name: "Chat",
  data() {
    return {
      messages: [],
      message: "",
      stompClient: null,
      typingTimeout: null,
      isTyping: false,
      typingIndicator: "",
    };
  },
  created() {
    this.connect();
  },
  methods: {
    connect() {
      this.socket = new SockJS("http://localhost:8080/api/websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect({}, (frame) => {
        console.log("Connected: " + frame);
        this.stompClient.subscribe("/topic/public", (message) => {
          this.messages.push(JSON.parse(message.body));
        });
        this.stompClient.subscribe("/topic/typing", (message) => {
          if (
            JSON.parse(message.body).text != null &&
            !this.checkIfIsTypingExists(JSON.parse(message.body).text)
          ) {
            this.messages.push(JSON.parse(message.body));
          }
        });
      });
    },
    handleMessage(message) {
      this.messages.push(message);
    },
    sendMessage() {
      this.isTyping = false;
      const messageToSend = {
        sender: this.$store.getters["auth/getEmail"],
        text: this.message,
        isTyping: this.isTyping,
      };
      this.stompClient.send(
        "/api/chat.sendMessage",
        {},
        JSON.stringify(messageToSend)
      );
      this.typingIndicator = "";
      this.message = "";
    },
    startTyping() {
      clearTimeout(this.typingTimeout);
      this.isTyping = true;
      this.stompClient.send(
        "/api/chat.typing",
        {},
        JSON.stringify({
          sender: this.$store.getters["auth/getEmail"],
          text: "is typing...",
          isTyping: this.isTyping,
        })
      );
      this.typingTimeout = setTimeout(() => {
        this.messages.splice(
          this.messages.indexOf({
            sender: this.$store.getters["auth/getEmail"],
            text: "is typing...",
            isTyping: this.isTyping,
          }),
          1
        );
        this.stompClient.send(
          "/api/chat.typing",
          {},
          JSON.stringify({ sender: "", text: "", isTyping: false })
        );
      }, 5000);
    },
    checkIfIsTypingExists(message) {
      this.messages.forEach((msg) => {
        if (msg.text === message) {
          return true;
        }
      });
      return false;
    },
  },
};
</script>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 80vh;
  width: 50%;
  margin: 0 auto;
}

.chat-messages {
  flex: 1;
  overflow-y: scroll;
  padding: 10px;
}

.message {
  margin-bottom: 10px;
}

.sender {
  font-weight: bold;
}

.chat-input {
  padding: 10px;
  display: flex;
}

.chat-input input {
  flex: 1;
  padding: 10px;
  font-size: 16px;
}

.chat-input button {
  padding: 10px;
  font-size: 16px;
  background-color: #4caf50;
  color: white;
  border: none;
  cursor: pointer;
}
</style>
