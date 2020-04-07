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

//    PilotDTO toPilotDTO(Pilot source);
//    Pilot toPilot(PilotDTO source);

    @Mappings({
            @Mapping(source = "id", target = "eventPilotId"),
            @Mapping(source = "pilot.pilotBib", target = "pilotBib"),
            @Mapping(source = "pilot.firstName", target = "firstName"),
            @Mapping(source = "pilot.lastName", target = "lastName"),
            @Mapping(source = "pilot.pilotClass", target = "pilotClass"),
            @Mapping(source = "pilot.ama", target = "ama"),
            @Mapping(source = "pilot.fai", target = "fai"),
            @Mapping(source = "pilot.faiLicence", target = "faiLicence"),
            @Mapping(source = "pilot.teamName", target = "teamName"),

    })
    PilotDTO toPilotDTO(EventPilot source);

    List<Pilot> toPilotList(List<PilotDTO> source);
    List<PilotDTO> toPilotListDTO(List<Pilot> source);
}
