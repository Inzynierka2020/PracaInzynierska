package aviationModelling.mapper;

import aviationModelling.dto.EventRoundDTO;
import aviationModelling.dto.RoundDTO;
import aviationModelling.entity.EventRound;
import aviationModelling.entity.Round;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EventRoundMapper {

    EventRoundMapper MAPPER = Mappers.getMapper(EventRoundMapper.class);

    EventRoundDTO toEventRoundDTO(EventRound source);
    EventRound toEventRound(EventRoundDTO source);

    List<EventRoundDTO> toEventRoundDTOList(List<EventRound> source);
    List<EventRound> toEventRoundList(List<EventRoundDTO> source);
}
