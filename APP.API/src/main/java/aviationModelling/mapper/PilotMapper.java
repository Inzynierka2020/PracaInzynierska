package aviationModelling.mapper;

import aviationModelling.dto.PilotDTO;
import aviationModelling.dto.VaultPilotDTO;
import aviationModelling.entity.Pilot;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {FlightMapper.class})
public interface PilotMapper {

    PilotMapper MAPPER = Mappers.getMapper(PilotMapper.class);

    PilotDTO toPilotDTO(Pilot source);
    Pilot toPilot(PilotDTO source);

    List<Pilot> toPilotList(List<PilotDTO> source);
    List<PilotDTO> toPilotListDTO(List<Pilot> source);
}
