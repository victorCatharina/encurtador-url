package br.com.victorCatharina.encurtador_url.service.mapper;

import br.com.victorCatharina.encurtador_url.dto.response.ClickEventDto;
import br.com.victorCatharina.encurtador_url.entities.ClickEventEntity;

public class ClickEventMapper {

    public static ClickEventDto clickEventEntityToDto(ClickEventEntity clickEventEntity) {
        if (clickEventEntity == null) return null;
        return new ClickEventDto(
                clickEventEntity.getTimestamp(),
                clickEventEntity.getIp(),
                clickEventEntity.getUserAgent(),
                clickEventEntity.getReferrer()
        );
    }

}
