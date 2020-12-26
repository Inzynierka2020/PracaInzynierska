import { Injectable } from '@angular/core';

export enum BestFlightType {
  Round = 1,
  Event = 2,
  Group = 3
}

export interface EventRules {
  pilotInGroupCount: number;
  bestFlightType: BestFlightType;
  minWind: number;
  maxWind: number;
  minDir: number;
  maxDir: number;
}

@Injectable({
  providedIn: 'root'
})
export class RulesService {

  constructor() { }

  rules: EventRules = {
    pilotInGroupCount: 10,
    bestFlightType: BestFlightType.Round,
    minWind: 3,
    maxWind: 25,
    minDir: -45,
    maxDir: 45
  }

  getRules(): EventRules {
    var result = window.localStorage.getItem("rules");
    if (result === null)
      return this.rules;  
    return JSON.parse(result);
  }

  setRules(rules: EventRules) {
    window.localStorage.setItem("rules", JSON.stringify(rules));
  }
}
