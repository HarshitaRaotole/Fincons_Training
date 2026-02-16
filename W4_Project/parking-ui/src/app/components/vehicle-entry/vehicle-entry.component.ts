import { Component, OnInit } from '@angular/core';
import { ParkingService } from '../../services/parking.service';
import { ParkingLot, ParkingSession } from '../../models/parking';
// Import Material components
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TicketDialogComponent } from '../ticket-dialog/ticket-dialog.component';

@Component({
  selector: 'app-vehicle-entry',
  standalone: false,
  templateUrl: './vehicle-entry.component.html',
  styleUrls: ['./vehicle-entry.component.css']
})
export class VehicleEntryComponent implements OnInit {

  // Data for Dropdown
  lots: ParkingLot[] = [];

  // Form Inputs
  lotId: number | null = null;
  vNum: string = '';
  vType: string | null = null;

  // ⭐ FIX: Define these variables so HTML doesn't crash
  successSession: ParkingSession | null = null;
  errorMessage: string = '';

  constructor(
    private service: ParkingService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    // Load lots for the dropdown
    this.service.getParkingLots(0, 100, '').subscribe(res => {
      this.lots = res.content;
    });
  }

  onEnter(form: any) {
    this.errorMessage = ''; // Clear previous errors

    if (form.invalid) return;

    this.service.enterVehicle(this.lotId!, this.vNum, this.vType!).subscribe({
      next: (res) => {
        this.successSession = res; // Save session details

        // 1. Show Success Toast
        this.snackBar.open('Vehicle Parked Successfully!', 'OK', {
          duration: 3000,
          panelClass: ['success-snackbar'],
          horizontalPosition: 'right',
          verticalPosition: 'top'
        });

        // 2. Open Ticket Dialog
        this.dialog.open(TicketDialogComponent, {
          width: '350px',
          data: res,
          disableClose: true
        });

        // 3. Reset Form
        form.resetForm();
        this.vType = null;
        this.successSession = null; // Reset this so form stays visible
      },
      error: (err) => {

  // Handle both plain text and JSON errors
  if (typeof err.error === 'string') {
    this.errorMessage = err.error;
  } else if (err.error?.message) {
    this.errorMessage = err.error.message;
  } else {
    this.errorMessage = "Entry failed. Please check inputs.";
  }

  this.snackBar.open(this.errorMessage, 'Close', {
    duration: 4000,
    panelClass: ['error-snackbar'],
    verticalPosition: 'top'
  });

}

    });
  }
}