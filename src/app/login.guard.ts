import {CanActivateFn, Router} from "@angular/router";
import {AuthService} from "./Services/Authentication/auth.service";
import {inject} from "@angular/core";

export const loginGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    router.navigate(['/home']);
    return false;
  } else {
    return true;
  }
};
