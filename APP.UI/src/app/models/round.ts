import { Flight } from './flight';

export interface Round {
    roundNum: number;
    eventId: number;
    numberOfGroups: number;
    cancelled: boolean;
    finished:boolean;
    flights: Flight[];
    synchronized: boolean;
}