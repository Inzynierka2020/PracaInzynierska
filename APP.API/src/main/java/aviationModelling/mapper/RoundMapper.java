package aviationModelling.mapper;

import aviationModelling.dto.RoundDTO;
import aviationModelling.entity.Round;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {FlightMapper.class})
public interface RoundMapper {

    RoundMapper MAPPER = Mappers.getMapper(RoundMapper.class);

    RoundDTO toRoundDTO(Round source);
    Round toRound(RoundDTO source);

    List<RoundDTO> toRoundDTOList(List<Round> source);
    List<Round> toRoundList(List<RoundDTO> source);
}
