import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { SnackbarService, StorageService } from '../services';

export const authGuard: CanActivateFn = (route, state) => {
  const storageService = inject(StorageService);
  const snackbarService = inject(SnackbarService);
  const router = inject(Router);
  
  if (storageService.isAuthenticated()) {
    return true;
  }
  router.navigate(["auth/login"]);
  snackbarService.openSnackbar("Vous devez d'abord vous connecter !");
  return false;
};
