package aviationModelling.mapper;

import aviationModelling.dto.RoundDTO;
import aviationModelling.entity.EventRound;
import aviationModelling.entity.Round;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {FlightMapper.class})
public interface RoundMapper {

    RoundMapper MAPPER = Mappers.getMapper(RoundMapper.class);

//    RoundDTO toRoundDTO(Round source);
//    Round toRound(RoundDTO source);

    @Mappings({
            @Mapping(source = "id", target = "eventRoundId")
    })
    RoundDTO toRoundDTO(EventRound source);

    List<RoundDTO> toRoundDTOList(List<EventRound> source);

//    List<RoundDTO> toRoundDTOList(List<Round> source);
//    List<Round> toRoundList(List<RoundDTO> source);
}
