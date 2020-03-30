import { Flight } from './flight';

export interface Round {
    roundNum: number;
    eventId: number;
    isCancelled: boolean;
    isFinished:boolean;
    flights: Flight[];
}