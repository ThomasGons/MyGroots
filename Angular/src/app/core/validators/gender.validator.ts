import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";


export function GenderValidator(): ValidatorFn {
  return ((control: AbstractControl): ValidationErrors | null => {
    if (!control.value) {
      return null;
    }

    const genders: string[] = ["MALE", "FEMALE"];
    const userInput = control.value.toUpperCase();
    
    if (genders.includes(userInput)) {
      return null;
    }
    return { invalidGender: true }; 
  });
}