import { Component, Input, OnInit } from '@angular/core';
import { BehaviorSubject, Observable, ReplaySubject } from 'rxjs';
import { FileService } from 'src/app/services/file.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css'],
})
export class UserDetailsComponent implements OnInit {
  @Input() name = 'Prueba';
  @Input() username = 'Prueba';
  @Input() description = 'Prueba';
  @Input() birthday = 'Prueba';
  @Input() registerDate = 'Prueba';
  private _avatar = new BehaviorSubject<string>('koalaSad.jpg');

  data = '';

  constructor(private fileService: FileService) {}

  ngOnInit(): void {
    this._avatar.subscribe((val) => {
      this.getImage(val);
    });
  }

  @Input() set avatar(value: string) {
    this._avatar.next(value);
  }
  get avatar() {
    return this._avatar.getValue();
  }

  getImage(avatar: string) {
    if (avatar != null) {
      this.fileService.getData(avatar).subscribe((res) => (this.data = res));
    }
  }
}
