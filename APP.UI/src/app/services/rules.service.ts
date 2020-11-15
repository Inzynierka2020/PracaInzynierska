import { Injectable } from '@angular/core';

export enum BestFlightType {
  Round = 1,
  Event = 2,
  Group = 3
}

export interface EventRules {
  pilotInGroupCount: number;
  bestFlightType: BestFlightType;
}

@Injectable({
  providedIn: 'root'
})
export class RulesService {

  constructor() { }

  rules: EventRules = {
    pilotInGroupCount: 10,
    bestFlightType: BestFlightType.Round
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
