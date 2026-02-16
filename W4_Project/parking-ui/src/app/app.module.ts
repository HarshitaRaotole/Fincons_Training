import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
// ⭐ NEW: Required for Angular Material Animations (Dialogs, Inputs, Snackbars)
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// Page Components
import { ParkingLotsComponent } from './components/parking-lots/parking-lots.component';
import { VehicleEntryComponent } from './components/vehicle-entry/vehicle-entry.component';
import { ActiveSessionsComponent } from './components/active-sessions/active-sessions.component';
import { VehicleExitComponent } from './components/vehicle-exit/vehicle-exit.component';
import { ParkingHistoryComponent } from './components/parking-history/parking-history.component';

// Dialog Components
import { TicketDialogComponent } from './components/ticket-dialog/ticket-dialog.component';
import { ReceiptDialogComponent } from './components/receipt-dialog/receipt-dialog.component';

// Angular Material Imports
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { MatDividerModule } from '@angular/material/divider';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';

@NgModule({
  declarations: [
    AppComponent,
    ParkingLotsComponent,
    VehicleEntryComponent,
    ActiveSessionsComponent,
    VehicleExitComponent,
    ParkingHistoryComponent,
    TicketDialogComponent,
    ReceiptDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule, // ⭐ Critical: Added this
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    
    // Material Modules
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    MatCardModule,
    MatSelectModule,
    MatDividerModule,
    MatSnackBarModule,
    MatDialogModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }