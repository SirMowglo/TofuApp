import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-green-button',
  templateUrl: './custom-button.component.html',
  styleUrls: ['./custom-button.component.css']
})
export class CustomButtonComponent {
  @Input() label = "";
  @Input() btnStyle = "";

  @Output() OnClick = new EventEmitter()

  emitEvent(){
    this.OnClick.emit()
  }
}
