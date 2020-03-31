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
            @Mapping(source = "flightId.pilotId", target = "pilotId"),
            @Mapping(source = "flightId.roundNum", target = "roundNum"),
            @Mapping(source = "pilot.eventId", target = "eventId")
    })
    FlightDTO toFlightDTO(Flight source);

    @Mappings({
            @Mapping(target = "flightId.pilotId", source = "pilotId"),
            @Mapping(target = "flightId.roundNum", source = "roundNum"),
            @Mapping(target = "group", source = "group", defaultValue = "")
    })
    Flight toFlight(FlightDTO source);

    List<Flight> toFlightList (List<FlightDTO> source);
    List<FlightDTO> toFlightDTOList (List<Flight> source);


}
