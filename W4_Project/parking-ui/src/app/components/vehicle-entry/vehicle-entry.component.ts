import { Component, OnInit } from '@angular/core';
import { ParkingService } from '../../services/parking.service';
import { ParkingLot, ParkingSession } from '../../models/parking';

@Component({
  selector: 'app-vehicle-entry',
  standalone: false,
  template: `
    <div class="form-box">
      <h3>Vehicle Entry</h3>

      <!-- Show success details after successful parking -->
      <div *ngIf="successSession" class="success-msg">
        <strong>Success!</strong><br>
        Ticket ID: {{ successSession.id }}<br>
        Slot Assigned: {{ successSession.slotNumber || successSession.parkingSlot?.slotNumber }}
      </div>

      <!-- Show backend error message -->
      <div *ngIf="errorMessage" class="error-msg">
        {{ errorMessage }}
      </div>

      <form #entryForm="ngForm" (ngSubmit)="onEnter(entryForm)">

        <!-- Parking Lot Selection -->
        <div class="form-group">
          <label>Select Parking Lot:</label>
          <select [(ngModel)]="lotId" name="lotId" required>
            <option value="" disabled>-- Select Lot --</option>
            <option *ngFor="let lot of lots" [value]="lot.id">
              {{ lot.name }}
            </option>
          </select>
        </div>

        <!-- Vehicle Number Input -->
        <div class="form-group">
          <label>Vehicle Number:</label>
          <input
            type="text"
            name="vNum"
            [(ngModel)]="vNum"
            #vehicleNum="ngModel"
            required
            pattern="^[A-Z]{2}-?\\d{2}-?[A-Z]{1,2}-?\\d{4}$"
            placeholder="MH-12-AB-1234"
          />

          <!-- Validation messages -->
          <div class="error-msg"
               *ngIf="vehicleNum.invalid && entryForm.submitted">
            <small *ngIf="vehicleNum.errors?.['required']">
              Vehicle number is required
            </small>
            <small *ngIf="vehicleNum.errors?.['pattern']">
              Enter a valid vehicle number (e.g. MH-12-AB-1234)
            </small>
          </div>
        </div>

        <!-- Vehicle Type Selection -->
        <div class="form-group">
          <label>Vehicle Type:</label>
          <select [(ngModel)]="vType" name="vType" required>
            <option value="" disabled>-- Select Type --</option>
            <option value="CAR">CAR</option>
            <option value="BIKE">BIKE</option>
          </select>
        </div>

        <button type="submit">Park Vehicle</button>
      </form>
    </div>
  `
})
export class VehicleEntryComponent implements OnInit {

  // Parking lots fetched from backend
  lots: ParkingLot[] = [];

  // Form fields
  lotId: number | null = null;
  vNum: string = '';
  vType: string | null = null;

  // UI state messages
  successSession: ParkingSession | null = null;
  errorMessage: string = '';

  // Inject ParkingService
  constructor(private service: ParkingService) {}

  // Load parking lots on page load
  ngOnInit() {
    this.service.getParkingLots().subscribe(data => {
      this.lots = data;
    });
  }

  // Handle vehicle entry submission
  onEnter(form: any) {
    this.successSession = null;
    this.errorMessage = '';

    // Stop submission if form is invalid
    if (form.invalid) {
      return;
    }

    this.service.enterVehicle(this.lotId!, this.vNum, this.vType!).subscribe({
      next: (res) => {
        this.successSession = res;

        // Reset form after successful entry
        form.resetForm({
          lotId: null,
          vNum: '',
          vType: null
        });
      },
      error: (err) => {
        // Handle plain text or JSON error from backend
        if (typeof err.error === 'string') {
          this.errorMessage = err.error;
        } else if (err.error?.message) {
          this.errorMessage = err.error.message;
        }
      }
    });
  }
}
