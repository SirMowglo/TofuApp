import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  OnInit,
} from '@angular/core';
import { AuthService } from './services/auth.service';
import { LoadingService } from './services/loading.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  isLoggedIn = false;
  loading = false;

  constructor(
    private authService: AuthService,
    private loadingService: LoadingService,
    private changeDetector: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadingService.getLoader().subscribe((res) => {
      this.loading = res;
    });
    this.authService.isLogged.subscribe((isLogged) => {
      this.isLoggedIn = isLogged;
    });
  }

  ngAfterContentChecked(): void {
    this.changeDetector.detectChanges();
  }
  title = 'web_tofu_app';
}
