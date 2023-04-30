import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-custom-input',
  templateUrl: './custom-input.component.html',
  styleUrls: ['./custom-input.component.css']
})
export class CustomInputComponent  {
@Input() inputId = "";
@Input() control = new FormControl;
@Input() label = "";


errorMessages: Record<string,string> = {
  required: "The field is required",
}
}
