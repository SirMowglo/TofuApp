import { Component, Input} from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-custom-input',
  templateUrl: './custom-input.component.html',
  styleUrls: ['./custom-input.component.css'],
})
export class CustomInputComponent {
  @Input() inputId = '';
  @Input() control = new FormControl();
  @Input() label = '';
  @Input() inputType = '';
  @Input() enableVisibility = false;
  @Input() maxValue = 0
  @Input() minValue = 0
  visibilityIcon = 'fa-eye';

  @Input() errorMessages: Record<string, string> = {
    required: 'The field is required',
  };

  toggleVisibility() {
    this.inputType = this.inputType === 'password' ? 'text' : 'password';
    this.visibilityIcon =
      this.inputType === 'password' ? 'fa-eye' : 'fa-eye-slash';
  }
}
