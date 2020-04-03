import { Flight } from './flight';

export interface Pilot {
    id: number
    eventId: number;
    pilotBib: number;
    firstName: string;
    lastName: string;
    pilotClass: "";
    ama: "",
    fai: "",
    faiLicense: "",
    teamName: ""
    discarded1: number;
    discarded2: number;
    score: string;
    penalty: number 
    flights: Flight[];
    flight: Flight;
}
