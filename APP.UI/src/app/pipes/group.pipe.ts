import { Pipe, PipeTransform } from '@angular/core';
import { Pilot } from '../models/pilot';

@Pipe({ 
  name: 'groupPipe' 
})
export class GroupPipe implements PipeTransform {
  transform(pilots: Pilot[], group: string) {
    return pilots.filter(pilot => pilot.flight.group == group);
  }
}