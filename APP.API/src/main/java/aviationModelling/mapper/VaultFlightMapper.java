//package aviationModelling.mapper;
//
//import aviationModelling.dto.VaultFlightDTO;
//import aviationModelling.dto.VaultPilotDTO;
//import aviationModelling.entity.Flight;
//import org.mapstruct.*;
//import org.mapstruct.factory.Mappers;
//
//import java.util.List;
//
//@Mapper()
//public interface VaultFlightMapper {
//
//    VaultFlightMapper MAPPER = Mappers.getMapper(VaultFlightMapper.class);
//
//    @Mappings({
//
////            @Mapping(target = "flightId.roundNum", source = "round_number"),
////            @Mapping(target = "flightId.pilotId", source = "pilot_id"),
//            @Mapping(target = "group", source = "group", defaultValue = ""),
//            @Mapping(target = "sub1", expression = "java(!(source.subs== null || source.subs.get(0).sub_val == null) ? Float.parseFloat(source.subs.get(0).sub_val) : null)"),
//            @Mapping(target = "sub2", expression = "java(!(source.subs== null || source.subs.get(1).sub_val == null) ? Float.parseFloat(source.subs.get(1).sub_val) : null)"),
//            @Mapping(target = "sub3", expression = "java(!(source.subs== null || source.subs.get(2).sub_val == null) ? Float.parseFloat(source.subs.get(2).sub_val) : null)"),
//            @Mapping(target = "sub4", expression = "java(!(source.subs== null || source.subs.get(3).sub_val == null) ? Float.parseFloat(source.subs.get(3).sub_val) : null)"),
//            @Mapping(target = "sub5", expression = "java(!(source.subs== null || source.subs.get(4).sub_val == null) ? Float.parseFloat(source.subs.get(4).sub_val) : null)"),
//            @Mapping(target = "sub6", expression = "java(!(source.subs== null || source.subs.get(5).sub_val == null) ? Float.parseFloat(source.subs.get(5).sub_val) : null)"),
//            @Mapping(target = "sub7", expression = "java(!(source.subs== null || source.subs.get(6).sub_val == null) ? Float.parseFloat(source.subs.get(6).sub_val) : null)"),
//            @Mapping(target = "sub8", expression = "java(!(source.subs== null || source.subs.get(7).sub_val == null) ? Float.parseFloat(source.subs.get(7).sub_val) : null)"),
//            @Mapping(target = "sub9", expression = "java(!(source.subs== null || source.subs.get(8).sub_val == null) ? Float.parseFloat(source.subs.get(8).sub_val) : null)"),
//            @Mapping(target = "sub10", expression = "java(!(source.subs== null || source.subs.get(9).sub_val == null) ? Float.parseFloat(source.subs.get(9).sub_val) : null)"),
//            @Mapping(target = "sub11", expression = "java(!(source.subs== null || source.subs.get(10).sub_val == null) ? Float.parseFloat(source.subs.get(10).sub_val) : null)"),
//            @Mapping(target = "score", source = "score", defaultValue = "0F"),
//            @Mapping(target = "penalty", source = "penalty", defaultValue = "0")
//    })
//    Flight toFlight(VaultFlightDTO source);
//
//    @InheritInverseConfiguration
//    VaultFlightDTO toVaultFlightDTO (Flight source);
//
//    List<Flight> toFlightList(List<VaultFlightDTO> source);
//    List<VaultFlightDTO> toVaultFlightDTOList(List<Flight> source);
//
//}
