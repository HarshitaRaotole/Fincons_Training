import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ParkingLotsComponent } from './components/parking-lots/parking-lots.component';
import { VehicleEntryComponent } from './components/vehicle-entry/vehicle-entry.component';
import { ActiveSessionsComponent } from './components/active-sessions/active-sessions.component';
import { VehicleExitComponent } from './components/vehicle-exit/vehicle-exit.component';
import { ParkingHistoryComponent } from './components/parking-history/parking-history.component';

const routes: Routes = [
  { path: '', redirectTo: 'lots', pathMatch: 'full' },
  { path: 'lots', component: ParkingLotsComponent },
  { path: 'entry', component: VehicleEntryComponent },
  { path: 'active', component: ActiveSessionsComponent },
  { path: 'exit', component: VehicleExitComponent },
  { path: 'history', component: ParkingHistoryComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }