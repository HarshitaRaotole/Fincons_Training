import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ParkingSession } from '../../models/parking';

@Component({
  selector: 'app-ticket-dialog',
  templateUrl: './ticket-dialog.component.html',
  standalone: false,
  styleUrls: ['./ticket-dialog.component.css']
})
export class TicketDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<TicketDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ParkingSession
  ) {}

  close() {
    this.dialogRef.close();
  }
}