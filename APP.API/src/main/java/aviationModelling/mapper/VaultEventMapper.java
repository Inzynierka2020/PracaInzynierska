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
            @Mapping(target = "eventLocation", source = "location_name"),
            @Mapping(target = "eventStartDate", source = "start_date"),
            @Mapping(target = "eventEndDate", source = "end_date"),
            @Mapping(target = "eventType", source = "event_type_name"),
            @Mapping(target = "numberOfRounds", source = "total_rounds"),
//            @Mapping(target = "eventPilots", ignore = true),
            @Mapping(target = "eventRounds", ignore = true),
    })
    Event toEvent(VaultEventDTO source);

}
