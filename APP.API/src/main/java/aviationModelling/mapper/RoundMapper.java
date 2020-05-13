//package aviationModelling.mapper;
//
//import aviationModelling.dto.RoundDTO;
//import aviationModelling.entity.EventRound;
//import aviationModelling.entity.Round;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Mappings;
//import org.mapstruct.factory.Mappers;
//
//import java.util.List;
//
//@Mapper(uses = {FlightMapper.class})
//public interface RoundMapper {
//
//    RoundMapper MAPPER = Mappers.getMapper(RoundMapper.class);
//
//    RoundDTO toRoundDTO(EventRound source);
//
//    List<RoundDTO> toRoundDTOList(List<EventRound> source);
//
//    EventRound toEventRound(RoundDTO source);
//    List<EventRound> toEventRoundList(List<RoundDTO> source);
//
//}
