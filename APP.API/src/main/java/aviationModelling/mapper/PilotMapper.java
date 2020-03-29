package aviationModelling.mapper;

import aviationModelling.dto.PilotDTO;
import aviationModelling.dto.VaultPilotDTO;
import aviationModelling.entity.Pilot;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {FlightMapper.class})
public interface PilotMapper {

    PilotMapper MAPPER = Mappers.getMapper(PilotMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "pilot_id"),
            @Mapping(target = "eventId", source = "event_id"),
            @Mapping(target = "pilotBib", source = "pilot_bib"),
            @Mapping(target = "firstName", source = "pilot_first_name"),
            @Mapping(target = "lastName", source = "pilot_last_name"),
            @Mapping(target = "pilotClass", source = "pilot_class"),
            @Mapping(target = "ama", source = "pilot_ama"),
            @Mapping(target = "fai", source = "pilot_fai"),
            @Mapping(target = "faiLicence", source = "pilot_fai_license"),
            @Mapping(target = "teamName", source = "pilot_team"),
            @Mapping(target = "flights", ignore = true)
    })
    Pilot toPilot(VaultPilotDTO source);

    @InheritInverseConfiguration
    VaultPilotDTO toVaultPilotDTO(Pilot source);

    List<Pilot> toPilotList(List<VaultPilotDTO> source);
    List<VaultPilotDTO> toVaultPilotDTOList(List<Pilot> source);
//
//    PilotDTO toPilotDTO(Pilot source);
//    Pilot toPilot(PilotDTO source);
}
