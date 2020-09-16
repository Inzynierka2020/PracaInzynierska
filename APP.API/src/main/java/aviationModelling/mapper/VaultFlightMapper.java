package aviationModelling.mapper;

import aviationModelling.dto.VaultFlightDTO;
import aviationModelling.entity.Flight;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper()
public interface VaultFlightMapper {

    VaultFlightMapper MAPPER = Mappers.getMapper(VaultFlightMapper.class);

    @Mappings({

//            @Mapping(target = "flightId.roundNum", source = "round_number"),
//            @Mapping(target = "flightId.pilotId", source = "pilot_id"),
            @Mapping(target = "group", source = "flight_group", defaultValue = ""),
            @Mapping(target = "sub1", expression = "java(!(source.flight_subs== null || source.flight_subs.size()==0 || source.flight_subs.get(0).sub_val == null) ? Float.parseFloat(source.flight_subs.get(0).sub_val) : null)"),
            @Mapping(target = "sub2", expression = "java(!(source.flight_subs== null || source.flight_subs.size()==0 || source.flight_subs.get(1).sub_val == null) ? Float.parseFloat(source.flight_subs.get(1).sub_val) : null)"),
            @Mapping(target = "sub3", expression = "java(!(source.flight_subs== null || source.flight_subs.size()==0 || source.flight_subs.get(2).sub_val == null) ? Float.parseFloat(source.flight_subs.get(2).sub_val) : null)"),
            @Mapping(target = "sub4", expression = "java(!(source.flight_subs== null || source.flight_subs.size()==0 || source.flight_subs.get(3).sub_val == null) ? Float.parseFloat(source.flight_subs.get(3).sub_val) : null)"),
            @Mapping(target = "sub5", expression = "java(!(source.flight_subs== null || source.flight_subs.size()==0 || source.flight_subs.get(4).sub_val == null) ? Float.parseFloat(source.flight_subs.get(4).sub_val) : null)"),
            @Mapping(target = "sub6", expression = "java(!(source.flight_subs== null || source.flight_subs.size()==0 || source.flight_subs.get(5).sub_val == null) ? Float.parseFloat(source.flight_subs.get(5).sub_val) : null)"),
            @Mapping(target = "sub7", expression = "java(!(source.flight_subs== null || source.flight_subs.size()==0 || source.flight_subs.get(6).sub_val == null) ? Float.parseFloat(source.flight_subs.get(6).sub_val) : null)"),
            @Mapping(target = "sub8", expression = "java(!(source.flight_subs== null || source.flight_subs.size()==0 || source.flight_subs.get(7).sub_val == null) ? Float.parseFloat(source.flight_subs.get(7).sub_val) : null)"),
            @Mapping(target = "sub9", expression = "java(!(source.flight_subs== null || source.flight_subs.size()==0 || source.flight_subs.get(8).sub_val == null) ? Float.parseFloat(source.flight_subs.get(8).sub_val) : null)"),
            @Mapping(target = "sub10", expression = "java(!(source.flight_subs== null || source.flight_subs.size()==0 || source.flight_subs.get(9).sub_val == null) ? Float.parseFloat(source.flight_subs.get(9).sub_val) : null)"),
            @Mapping(target = "sub11", expression = "java(!(source.flight_subs== null || source.flight_subs.size()==0 || source.flight_subs.get(10).sub_val == null) ? Float.parseFloat(source.flight_subs.get(10).sub_val) : null)"),
            @Mapping(target = "score", source = "flight_score", defaultValue = "0F"),
            @Mapping(target = "penalty", source = "flight_penalty", defaultValue = "0"),
            @Mapping(target = "order", source = "flight_order", defaultValue = "0"),
            @Mapping(target = "seconds", source = "flight_seconds", defaultValue = "0F")
    })
    Flight toFlight(VaultFlightDTO source);

    @InheritInverseConfiguration
    VaultFlightDTO toVaultFlightDTO (Flight source);

    List<Flight> toFlightList(List<VaultFlightDTO> source);
    List<VaultFlightDTO> toVaultFlightDTOList(List<Flight> source);

}
