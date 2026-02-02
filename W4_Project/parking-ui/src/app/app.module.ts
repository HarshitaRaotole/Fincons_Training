import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ParkingLotsComponent } from './components/parking-lots/parking-lots.component';
import { VehicleEntryComponent } from './components/vehicle-entry/vehicle-entry.component';
import { ActiveSessionsComponent } from './components/active-sessions/active-sessions.component';
import { VehicleExitComponent } from './components/vehicle-exit/vehicle-exit.component';
import { ParkingHistoryComponent } from './components/parking-history/parking-history.component';

@NgModule({
  declarations: [
    AppComponent,
    ParkingLotsComponent,
    VehicleEntryComponent,
    ActiveSessionsComponent,
    VehicleExitComponent,
    ParkingHistoryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule, // Needed for API calls
    FormsModule       // Needed for Forms
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }