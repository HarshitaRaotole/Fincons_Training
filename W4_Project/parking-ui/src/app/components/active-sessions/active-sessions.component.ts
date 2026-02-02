import { Component, OnInit } from '@angular/core';
import { ParkingService } from '../../services/parking.service';
import { ParkingSession } from '../../models/parking';

@Component({
  selector: 'app-active-sessions',
  standalone: false,
  template: `
    <!-- Displays list of currently active parking sessions -->
    <h3>Active Sessions</h3>

    <table>
      <thead>
        <tr>
          <th>Ticket ID</th>
          <th>Vehicle</th>
          <th>Type</th>
          <th>Slot</th>
          <th>Entry Time</th>
          <th>Status</th>
        </tr>
      </thead>

      <tbody>
        <!-- Loop through active sessions -->
        <tr *ngFor="let s of sessions">
          <td>{{ s.id }}</td>
          <td>{{ s.vehicleNumber || s.vehicle?.vehicleNumber }}</td>
          <td>{{ s.vehicleType || s.vehicle?.vehicleType }}</td>
          <td>{{ s.slotNumber || s.parkingSlot?.slotNumber }}</td>
          <td>{{ s.entryTime | date:'medium' }}</td>
          <td>{{ s.status }}</td>
        </tr>

        <!-- Show message when no active sessions are available -->
        <tr *ngIf="sessions.length === 0">
          <td colspan="6" style="text-align:center">No active vehicles.</td>
        </tr>
      </tbody>
    </table>
  `
})
export class ActiveSessionsComponent implements OnInit {

  // Stores active parking sessions
  sessions: ParkingSession[] = [];

  // Inject ParkingService to fetch data from backend
  constructor(private service: ParkingService) {}

  // Load active sessions on component initialization
  ngOnInit() {
    this.service.getActiveSessions().subscribe(d => this.sessions = d);
  }
}
