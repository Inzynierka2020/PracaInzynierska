import { HostListener, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PwaService {

  constructor() { }

  deferredPrompt: any;
  showButton = false;
  number: number;

  addToHomeScreen() {
    // hide our user interface that shows our A2HS button
    this.showButton = false;
    // Show the prompt
    this.deferredPrompt.prompt();
    // Wait for the user to respond to the prompt
    this.deferredPrompt.userChoice
      .then((choiceResult) => {
        if (choiceResult.outcome === 'accepted') {
          // console.log('User accepted the A2HS prompt');
        } else {
          // console.log('User dismissed the A2HS prompt');
        }
        this.deferredPrompt = null;
      })
  }

  requestNewVersion() {
    window.location.reload();
  }
}
