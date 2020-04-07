package aviationModelling.mapper;

import aviationModelling.dto.VaultEventDTO;
import aviationModelling.entity.Event;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {VaultPilotMapper.class})
public interface VaultEventMapper {

    VaultEventMapper MAPPER = Mappers.getMapper(VaultEventMapper.class);

    @Mappings({
            @Mapping(target = "eventId", source = "event_id"),
            @Mapping(target = "eventName", source = "event_name"),
            @Mapping(target = "eventLocation", source = "location"),
            @Mapping(target = "eventStartDate", source = "start_date"),
            @Mapping(target = "eventEndDate", source = "end_date"),
            @Mapping(target = "eventType", source = "event_type"),
            @Mapping(target = "numberOfRounds", source = "rounds"),
            @Mapping(target = "eventPilots", ignore = true),
            @Mapping(target = "eventRounds", ignore = true),
    })
    Event toEvent(VaultEventDTO source);











}
