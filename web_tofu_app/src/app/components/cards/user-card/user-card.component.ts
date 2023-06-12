import { Component, EventEmitter, Input, Output } from '@angular/core';
import { UserResponse } from 'src/app/models/user.interface';
import { FileService } from 'src/app/services/file.service';

@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.css']
})
export class UserCardComponent {
  @Input() user: UserResponse = {} as UserResponse;

  @Output() OnClick = new EventEmitter();

  userAvatarData = ''

  constructor(private fileService: FileService) {}

  ngOnInit(): void {
    this.getUserAvatar(this.user.avatar)
  }

  emitEvent() {
    this.OnClick.emit();
  }

  getUserAvatar(avatar: string) {
    if (avatar != null) {
      this.fileService
        .getData(avatar)
        .subscribe((res) => (this.userAvatarData = res));
    }
  }
}
