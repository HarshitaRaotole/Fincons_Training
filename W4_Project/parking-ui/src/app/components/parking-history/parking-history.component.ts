import { Component, OnInit } from '@angular/core';
import { ParkingService } from '../../services/parking.service';
import { ParkingSession } from '../../models/parking';

@Component({
  selector: 'app-parking-history',
  standalone: false,
  template: `
    <!-- Displays completed and ongoing parking sessions -->
    <h3>Parking History</h3>

    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Vehicle</th>
          <th>Slot</th>
          <th>Entry</th>
          <th>Exit</th>
          <th>Amount</th>
          <th>Status</th>
        </tr>
      </thead>

      <tbody>
        <!-- Loop through parking session history -->
        <tr *ngFor="let s of sessions">
          <td>{{ s.id }}</td>
          <td>{{ s.vehicleNumber || s.vehicle?.vehicleNumber }}</td>
          <td>{{ s.slotNumber || s.parkingSlot?.slotNumber }}</td>
          <td>{{ s.entryTime | date:'short' }}</td>
          <td>{{ s.exitTime ? (s.exitTime | date:'short') : 'In Progress' }}</td>
          <td>{{ s.totalAmount }}</td>
          <td>{{ s.status }}</td>
        </tr>
      </tbody>
    </table>
  `
})
export class ParkingHistoryComponent implements OnInit {

  // Stores parking session history
  sessions: ParkingSession[] = [];

  // Inject ParkingService to fetch history data
  constructor(private service: ParkingService) {}

  // Load parking history on component initialization
  ngOnInit() {
    this.service.getHistory().subscribe(d => this.sessions = d);
  }
}
