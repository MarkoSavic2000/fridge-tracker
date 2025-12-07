import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  constructor(private keycloak: KeycloakService) {}

  logout() {
    this.keycloak.logout(window.location.origin);
  }
}
