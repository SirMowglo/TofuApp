import { catchError, of } from 'rxjs';
import { AuthService } from '../services/auth.service';

export function appInitializer(authenticationService: AuthService) {
    return () => authenticationService.refreshToken()
        .pipe(
            catchError(() => of())
        );
}