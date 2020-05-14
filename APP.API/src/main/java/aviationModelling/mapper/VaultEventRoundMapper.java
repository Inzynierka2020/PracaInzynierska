package aviationModelling.mapper;

import aviationModelling.dto.VaultRoundDTO;
import aviationModelling.entity.EventRound;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VaultEventRoundMapper {

    VaultEventRoundMapper MAPPER = Mappers.getMapper(VaultEventRoundMapper.class);

    @Mappings({
            @Mapping(source="round_number", target="roundNum")
    })
    EventRound toEventRound(VaultRoundDTO source);
}
