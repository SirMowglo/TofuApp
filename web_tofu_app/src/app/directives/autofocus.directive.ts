import { Directive, ElementRef } from '@angular/core';
import { filter, map, of, take } from 'rxjs';

@Directive({
  selector: '[appAutofocus]'
})
export class AutofocusDirective {

  constructor(private el: ElementRef) { }

  ngOnInit() {
      of(this.el)
      .pipe(
        map(elementRef => elementRef.nativeElement), // getting the el 
        filter(nativeElement => !!nativeElement), // filtering
        take(1) // avoid memory leak, it will unsubscribe automatically
      )
      // our side effect
      .subscribe(input => {
        input.focus();
      })
  }
}
