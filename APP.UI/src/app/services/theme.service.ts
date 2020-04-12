import { Injectable } from '@angular/core';
import { Theme, first, second } from '../models/theme';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private active: Theme = first;
  private availableThemes: Theme[] = [first, second];

  getAvailableThemes(): Theme[] {
    return this.availableThemes;
  }

  getActiveTheme(): Theme {
    return this.active;
  }

  isSecondTheme(): boolean {
    return this.active.name === second.name;
  }

  setSecondTheme(): void {
    this.setActiveTheme(second);
  }

  setFirstTheme(): void {
    this.setActiveTheme(first);
  }

  setActiveTheme(theme: Theme): void {
    this.active = theme;

    Object.keys(this.active.properties).forEach(property => {
      document.documentElement.style.setProperty(
        property,
        this.active.properties[property]
      );
    });
  }
  
}

