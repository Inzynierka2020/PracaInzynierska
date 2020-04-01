package aviationModelling.mapper;

import aviationModelling.dto.VaultFlightDTO;
import aviationModelling.entity.Flight;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface VaultFlightMapper {

    VaultFlightMapper MAPPER = Mappers.getMapper(VaultFlightMapper.class);

    @Mappings({
            @Mapping(target = "flightId.roundNum", source = "round_number"),
            @Mapping(target = "flightId.pilotId", source = "pilot_id"),
            @Mapping(target = "group", source = "group", defaultValue = ""),
            @Mapping(target = "sub1", source = "subs.sub1", defaultValue = "0F"),
            @Mapping(target = "sub2", source = "subs.sub2", defaultValue = "0F"),
            @Mapping(target = "sub3", source = "subs.sub3", defaultValue = "0F"),
            @Mapping(target = "sub4", source = "subs.sub4", defaultValue = "0F"),
            @Mapping(target = "sub5", source = "subs.sub5", defaultValue = "0F"),
            @Mapping(target = "sub6", source = "subs.sub6", defaultValue = "0F"),
            @Mapping(target = "sub7", source = "subs.sub7", defaultValue = "0F"),
            @Mapping(target = "sub8", source = "subs.sub8", defaultValue = "0F"),
            @Mapping(target = "sub9", source = "subs.sub9", defaultValue = "0F"),
            @Mapping(target = "sub10", source = "subs.sub10", defaultValue = "0F"),
            @Mapping(target = "sub11", source = "subs.sub11", defaultValue = "0F"),

    })
    Flight toFlight(VaultFlightDTO source);

    @InheritInverseConfiguration
    VaultFlightDTO toVaultFlightDTO (Flight source);

    List<Flight> toFlightList(List<VaultFlightDTO> source);
    List<VaultFlightDTO> toVaultFlightDTOList(List<Flight> source);




}
