import { Flight } from './flight';

export interface Round {
    roundNum: number;
    eventId: number;
    cancelled: boolean;
    finished:boolean;
    flights: Flight[];
}