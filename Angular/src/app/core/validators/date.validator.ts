import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";


export function DateValidator(): ValidatorFn {
  return ((control: AbstractControl): ValidationErrors | null => {
    if (!control.value) {
      return null;
    }

    let isValid: boolean = false;
    const today: Date = new Date();
    const userInput: Date = new Date(control.value);

    if (today >= userInput){
      isValid = true;
    }
    return (isValid ? null : { invalidDate: true }); 
  });
}