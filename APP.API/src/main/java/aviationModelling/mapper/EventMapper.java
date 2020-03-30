package aviationModelling.mapper;


import aviationModelling.dto.EventDTO;
import aviationModelling.dto.VaultEventDTO;
import aviationModelling.entity.Event;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PilotMapper.class, RoundMapper.class})
public interface EventMapper {

    EventMapper MAPPER = Mappers.getMapper(EventMapper.class);

    EventDTO toEventDTO(Event source);
    Event toEvent(EventDTO source);









}
