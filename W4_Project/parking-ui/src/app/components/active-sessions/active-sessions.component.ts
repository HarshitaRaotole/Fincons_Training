import { Component, OnInit, OnDestroy } from '@angular/core'; // 1. Import OnDestroy
import { ParkingService } from '../../services/parking.service';
import { ParkingSession } from '../../models/parking';
import { PageEvent } from '@angular/material/paginator';
import { WebSocketService } from '../../services/websocket.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-active-sessions',
  templateUrl: './active-sessions.component.html',
  standalone: false,
  styleUrls: ['./active-sessions.component.css']
})
// 2. Implement OnDestroy
export class ActiveSessionsComponent implements OnInit, OnDestroy {

  displayedColumns: string[] = ['id', 'vehicleNumber', 'type', 'slotNumber', 'entryTime', 'status'];
  sessions: ParkingSession[] = [];
  totalElements: number = 0;
  currentPage: number = 0;
  pageSize: number = 10;
  keyword: string = '';

  constructor(
    private service: ParkingService,
    private webSocketService: WebSocketService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.loadData();

    // Connect to WebSocket
    this.webSocketService.connect((message) => {
      const updatedMessage = message.replace('$', '₹');
      // Show Notification
      this.snackBar.open(message, 'Close', {
        duration: 3000,
        verticalPosition: 'top',
        horizontalPosition: 'right'
      });

      // Refresh Grid
      this.loadData();
    });
  }

  // 3. Disconnect when leaving the page to prevent memory leaks
  ngOnDestroy() {
    this.webSocketService.disconnect();
  }

  loadData() {
    this.service.getActiveSessions(this.currentPage, this.pageSize, this.keyword)
      .subscribe(response => {
        this.sessions = response.content;
        this.totalElements = response.totalElements;
      });
  }

  
  onSearch() {
    this.currentPage = 0;
    this.loadData();
  }

  onReset() {
    this.keyword = '';
    this.onSearch();
  }

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadData();
  }
}