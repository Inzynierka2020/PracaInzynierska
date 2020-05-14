package aviationModelling.mapper;

import aviationModelling.dto.VaultPilotDTO;
import aviationModelling.entity.Pilot;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper//(uses = {VaultFlightMapper.class})
public interface VaultPilotMapper {

    VaultPilotMapper MAPPER = Mappers.getMapper(VaultPilotMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "pilot_id"),
            @Mapping(target = "pilotBib", source = "pilot_bib"),
            @Mapping(target = "firstName", source = "pilot_first_name"),
            @Mapping(target = "lastName", source = "pilot_last_name"),
            @Mapping(target = "countryCode", source = "country_code")

    })
    Pilot toPilot(VaultPilotDTO source);


    List<Pilot> toPilotList(List<VaultPilotDTO> source);
    List<VaultPilotDTO> toVaultPilotDTOList(List<Pilot> source);

}
