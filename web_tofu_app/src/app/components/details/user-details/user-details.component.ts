import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent {
  @Input() name = "Prueba";
  @Input() description = "Prueba";
  @Input() birthday ="Prueba";
  @Input() registerDate = "Prueba";
}
