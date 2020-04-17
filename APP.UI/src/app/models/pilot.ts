import { Flight } from './flight';

export interface Pilot {
    eventPilotId: number;
    pilotId: number
    eventId: number;
    pilotBib: number;
    firstName: string;
    lastName: string;
    pilotClass: "";
    ama: "",
    fai: "",
    faiLicense: "",
    teamName: ""
    totalPenalty: number;
    discarded1: number;
    discarded2: number;
    percentage: number;
    score: string;
    flights: Flight[];
    flight: Flight;
}
