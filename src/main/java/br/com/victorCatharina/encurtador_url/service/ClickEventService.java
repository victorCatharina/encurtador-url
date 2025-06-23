package br.com.victorCatharina.encurtador_url.service;

import br.com.victorCatharina.encurtador_url.dto.response.LinkMetricsResponseDto;
import br.com.victorCatharina.encurtador_url.entities.ClickEventEntity;
import br.com.victorCatharina.encurtador_url.entities.ShortUrlEntity;
import br.com.victorCatharina.encurtador_url.repository.ClickEventRepository;
import br.com.victorCatharina.encurtador_url.service.mapper.ClickEventMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClickEventService {

    private final MongoTemplate mongoTemplate;
    private final ClickEventRepository clickEventRepository;

    @Autowired
    public ClickEventService(MongoTemplate mongoTemplate,
                             ClickEventRepository clickEventRepository) {
        this.mongoTemplate = mongoTemplate;
        this.clickEventRepository = clickEventRepository;
    }

    public void saveClickEvent(String shorUrlId, HttpServletRequest request) {

        ClickEventEntity clickEvent = new ClickEventEntity();
        clickEvent.setShortUrlId(shorUrlId);
        clickEvent.setTimestamp(LocalDateTime.now());
        clickEvent.setIp(request.getRemoteAddr());
        clickEvent.setUserAgent(request.getHeader("User-Agent"));
        clickEvent.setReferrer(request.getHeader("Referrer"));

        clickEventRepository.save(clickEvent);

        Query query = new Query(Criteria.where("_id").is(shorUrlId));
        Update update = new Update().inc("click_count", 1);
        mongoTemplate.updateFirst(query, update, ShortUrlEntity.class);
    }

    public LinkMetricsResponseDto getMetrics(String shortUrlId) {

        List<ClickEventEntity> clickEventList = clickEventRepository.findByShortUrlId(shortUrlId);
        long totalClicks = clickEventRepository.countByShortUrlId(shortUrlId);

        return new LinkMetricsResponseDto(totalClicks,
                clickEventList == null ? null : clickEventList.stream()
                        .map(ClickEventMapper::clickEventEntityToDto)
                        .toList());
    }

}
