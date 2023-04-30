import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-custom-button',
  templateUrl: './custom-button.component.html',
  styleUrls: ['./custom-button.component.css']
})
export class CustomButtonComponent {
  @Input() label = "";
  @Input() btnStyle = "";
  @Input() isDisabled = false;

  @Output() OnClick = new EventEmitter()

  emitEvent(){
    this.OnClick.emit()
  }
}
