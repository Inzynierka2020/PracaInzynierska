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
    discard1: number;
    discard2: number;
    score: string;
    penalty: number;
    flights: Flight[];
    flight: Flight;
}
