//package aviationModelling.mapper;
//
//import aviationModelling.dto.FlightDTO;
//import aviationModelling.dto.VaultFlightDTO;
//import aviationModelling.entity.Flight;
//import org.mapstruct.*;
//import org.mapstruct.factory.Mappers;
//
//import java.util.List;
//
//@Mapper
//public interface FlightMapper {
//
//    FlightMapper MAPPER = Mappers.getMapper(FlightMapper.class);
//
//    @Mappings({
////            @Mapping(source = "flightId.eventPilotId", target = "eventPilotId"),
////            @Mapping(source = "flightId.eventRoundId", target = "eventRoundId"),
//            @Mapping(source = "eventPilot.pilotId", target = "pilotId"),
//            @Mapping(source = "eventRound.roundNum", target = "roundNum"),
//            @Mapping(source = "eventPilot.eventId", target = "eventId")
//    })
//    FlightDTO toFlightDTO(Flight source);
//
//    @Mappings({
////            @Mapping(source = "eventPilotId", target = "flightId.eventPilotId"),
////            @Mapping(source = "eventRoundId", target = "flightId.eventRoundId"),
//            @Mapping(source = "group", target = "group", defaultValue = ""),
//            @Mapping(source = "score", target = "score", defaultValue = "0F")
//    })
//    Flight toFlight(FlightDTO source);
//
//    List<Flight> toFlightList(List<FlightDTO> source);
//
//    List<FlightDTO> toFlightDTOList(List<Flight> source);
//
//}
