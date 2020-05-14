package aviationModelling.mapper;

import aviationModelling.dto.PilotDTO;
import aviationModelling.dto.VaultPilotDTO;
import aviationModelling.entity.EventPilot;
import aviationModelling.entity.Pilot;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {FlightMapper.class})
public interface PilotMapper {

    PilotMapper MAPPER = Mappers.getMapper(PilotMapper.class);

    @Mappings({
            @Mapping(source = "pilot.pilotBib", target = "pilotBib"),
            @Mapping(source = "pilot.firstName", target = "firstName"),
            @Mapping(source = "pilot.lastName", target = "lastName"),


    })
    PilotDTO toPilotDTO(EventPilot source);
    List<PilotDTO> toPilotDTOList(List<EventPilot> source);
}
