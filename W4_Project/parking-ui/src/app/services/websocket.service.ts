import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private stompClient: any = null;

  constructor() { }

  connect(onMessageReceived: (message: string) => void) {
    // 1. FIX: URL must match registry.addEndpoint("/ws") from Java
    const socket = new SockJS('http://localhost:8081/ws'); 
    
    this.stompClient = Stomp.over(socket);

    // Optional: Disable debug logs if you want a clean console
    // this.stompClient.debug = null; 

    this.stompClient.connect({}, (frame: any) => {
      console.log('✅ Connected to WebSocket');

      // 2. Subscribe to the topic defined in your Java Consumer
      // Ensure your Java Consumer uses: template.convertAndSend("/topic/updates", message);
      this.stompClient.subscribe('/topic/updates', (notification: any) => {
        onMessageReceived(notification.body);
      });

    }, (error: any) => {
      console.error('❌ WebSocket Error:', error);
    });
  }

  disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
      console.log("Disconnected");
    }
  }
}