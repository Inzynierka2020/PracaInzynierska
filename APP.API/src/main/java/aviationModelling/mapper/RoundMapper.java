package aviationModelling.mapper;

import aviationModelling.dto.RoundDTO;
import aviationModelling.entity.EventRound;
import aviationModelling.entity.Round;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {FlightMapper.class})
public interface RoundMapper {

    RoundMapper MAPPER = Mappers.getMapper(RoundMapper.class);

    @Mappings({
            @Mapping(source = "cancelled", target = "isCancelled"),
            @Mapping(source = "finished", target = "isFinished"),
            @Mapping(source = "synchronized", target = "isSynchronized")
    })
    EventRound toEventRound(RoundDTO source);

    @Mappings({
            @Mapping(source = "cancelled", target = "cancelled"),
            @Mapping(source = "finished", target = "finished"),
            @Mapping(source = "synchronized", target = "synchronized")
    })
    RoundDTO toRoundDTO(EventRound source);

    List<RoundDTO> toRoundDTOList(List<EventRound> source);

    List<EventRound> toEventRoundList(List<RoundDTO> source);

}
