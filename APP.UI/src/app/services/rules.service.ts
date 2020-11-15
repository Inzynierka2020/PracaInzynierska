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

  getRules(): EventRules {
    var result = window.localStorage.getItem("rules");
    if (result)
      return JSON.parse(result);
    return null;
  }

  setRules(rules: EventRules) {
    window.localStorage.setItem("rules", JSON.stringify(rules));
  }
}
