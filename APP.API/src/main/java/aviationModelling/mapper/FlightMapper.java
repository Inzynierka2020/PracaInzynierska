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
            @Mapping(source = "flightId.eventPilotId", target = "pilotId"),
            @Mapping(source = "flightId.eventRoundId", target = "roundNum"),
            @Mapping(source = "eventPilot.eventId", target = "eventId")
    })
    FlightDTO toFlightDTO(Flight source);

    @Mappings({
            @Mapping(target = "flightId.eventPilotId", source = "pilotId"),
            @Mapping(target = "flightId.eventRoundId", source = "roundNum"),
            @Mapping(target = "group", source = "group", defaultValue = "")
    })
    Flight toFlight(FlightDTO source);

    List<Flight> toFlightList (List<FlightDTO> source);
    List<FlightDTO> toFlightDTOList (List<Flight> source);


}
