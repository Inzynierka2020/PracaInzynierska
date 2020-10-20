package aviationModelling.mapper;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.VaultFlightDTO;
import aviationModelling.entity.Flight;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FlightMapper {

    FlightMapper MAPPER = Mappers.getMapper(FlightMapper.class);

    @Mappings({
//            @Mapping(source = "flightId.eventPilotId", target = "eventPilotId"),
//            @Mapping(source = "flightId.eventRoundId", target = "eventRoundId"),
            @Mapping(source = "eventPilot.pilotId", target = "pilotId"),
            @Mapping(source = "eventRound.roundNum", target = "roundNum"),
            @Mapping(source = "eventPilot.eventId", target = "eventId"),
            @Mapping(source = "sub1", target = "sub1", defaultValue = "0F"),
            @Mapping(source = "sub2", target = "sub2", defaultValue = "0F"),
            @Mapping(source = "sub3", target = "sub3", defaultValue = "0F"),
            @Mapping(source = "sub4", target = "sub4", defaultValue = "0F"),
            @Mapping(source = "sub5", target = "sub5", defaultValue = "0F"),
            @Mapping(source = "sub6", target = "sub6", defaultValue = "0F"),
            @Mapping(source = "sub7", target = "sub7", defaultValue = "0F"),
            @Mapping(source = "sub8", target = "sub8", defaultValue = "0F"),
            @Mapping(source = "sub9", target = "sub9", defaultValue = "0F"),
            @Mapping(source = "sub10", target = "sub10", defaultValue = "0F"),
            @Mapping(source = "sub11", target = "sub11", defaultValue = "0F")
    })
    FlightDTO toFlightDTO(Flight source);

    @Mappings({
//            @Mapping(source = "eventPilotId", target = "flightId.eventPilotId"),
//            @Mapping(source = "eventRoundId", target = "flightId.eventRoundId"),
            @Mapping(source = "group", target = "group", defaultValue = ""),
            @Mapping(source = "penalty", target = "penalty", defaultValue = "0"),
            @Mapping(source = "score", target = "score", defaultValue = "0F"),
            @Mapping(source = "synchronized", target = "isSynchronized")

    })
    Flight toFlight(FlightDTO source);

    List<Flight> toFlightList(List<FlightDTO> source);

    List<FlightDTO> toFlightDTOList(List<Flight> source);

}
