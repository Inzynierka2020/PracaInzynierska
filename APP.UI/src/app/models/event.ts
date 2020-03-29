export interface Event {
    eventId: number,
    eventName: string,
    eventLocation: string,
    eventStartDate: string,
    eventEndDate: string,
    eventType: string,
    numberOfRounds: number
    started: boolean;
}
