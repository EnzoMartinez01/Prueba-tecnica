import {CanActivateFn, Router} from "@angular/router";
import {AuthService} from "./Services/Authentication/auth.service";
import {inject} from "@angular/core";
import {MantenimientosService} from "./Services/mantenimientos.service";

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }
};
