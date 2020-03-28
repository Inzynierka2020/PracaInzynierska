package aviationModelling.mapper;

import aviationModelling.dto.EventDTO;
import aviationModelling.dto.VaultEventDTO;
import aviationModelling.entity.Event;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PilotMapper.class})
public interface EventMapper {

    EventMapper MAPPER = Mappers.getMapper(EventMapper.class);

    @Mappings({
            @Mapping(target = "eventId", source = "event_id"),
            @Mapping(target = "eventName", source = "event_name"),
            @Mapping(target = "eventLocation", source = "location"),
            @Mapping(target = "eventStartDate", source = "start_date"),
            @Mapping(target = "eventEndDate", source = "end_date"),
            @Mapping(target = "eventType", source = "event_type"),
            @Mapping(target = "numberOfRounds", source = "rounds"),
    })
    Event toEvent(VaultEventDTO source);




}
