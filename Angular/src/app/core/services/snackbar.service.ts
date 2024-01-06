import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';


@Injectable({
  providedIn: 'root'
})
export class SnackbarService {

  private readonly durationInSeconds: number = 5;

  constructor(
    private _snackbar: MatSnackBar,
  ) {}

  public openSnackbar(message: string, action?: string): void {
    this._snackbar.open(message, action, {
      horizontalPosition: "right",
      verticalPosition: "bottom",
      duration: this.durationInSeconds * 1000,
    });
  }
}
