import { Flight } from "./flight"

export interface BestFlights {
    bestFromEvent: Flight,
    bestFromGroups: Flight[],
    bestFromRound: Flight
}
