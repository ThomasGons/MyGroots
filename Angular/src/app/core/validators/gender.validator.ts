import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";


export function GenderValidator(): ValidatorFn {
  return ((control: AbstractControl): ValidationErrors | null => {
    if (!control.value) {
      return null;
    }

    let isValid: boolean = false;
    const genders: string[] = ["MALE", "FEMALE"];
    const userInput = control.value.toUpperCase();
    
    if (genders.includes(userInput)) {
      isValid = true;
    }
    return (isValid ? null : { invalidGender: true }); 
  });
}