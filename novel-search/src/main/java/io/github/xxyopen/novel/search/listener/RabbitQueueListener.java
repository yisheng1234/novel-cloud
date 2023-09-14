package io.github.xxyopen.novel.search.listener;


import co.elastic.clients.elasticsearch.core.IndexResponse;
import io.github.xxyopen.novel.book.dao.entity.BookInfo;
import io.github.xxyopen.novel.book.dao.mapper.BookInfoMapper;
import io.github.xxyopen.novel.book.dto.resp.BookEsRespDto;
import io.github.xxyopen.novel.common.constant.AmqpConsts;
import io.github.xxyopen.novel.search.config.EsConfig;
import io.github.xxyopen.novel.search.constant.EsConsts;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Rabbit 队列监听器
 *
 * @author xiongxiaoyang
 * @date 2022/5/25
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitQueueListener {

    private final BookInfoMapper bookInfoMapper;

    private final EsConfig esConfig;

    /**
     * 监听小说信息改变的 ES 更新队列，更新最新小说信息到 ES
     */
    @RabbitListener(queues = AmqpConsts.BookChangeMq.QUEUE_ES_UPDATE)
    @SneakyThrows
    public void updateEsBook(Long bookId) {
        BookInfo bookInfo = bookInfoMapper.selectById(bookId);
        log.error("已更新es");
        IndexResponse response = esConfig.restClient().getElasticsearchClient().index(i -> i
            .index(EsConsts.BookIndex.INDEX_NAME)
            .id(bookInfo.getId().toString())
            .document(BookEsRespDto.builder().authorId(bookInfo.getAuthorId())
                    .authorName(bookInfo.getAuthorName())
                    .bookName(bookInfo.getBookName())
                    .bookDesc(bookInfo.getBookDesc())
                    .bookStatus(bookInfo.getBookStatus())
                    .wordCount(bookInfo.getWordCount())
                    .workDirection(bookInfo.getWorkDirection())
                    .categoryId(bookInfo.getCategoryId())
                    .categoryName(bookInfo.getCategoryName())
                    .commentCount(bookInfo.getCommentCount())
                    .score(bookInfo.getScore())
                    .isVip(bookInfo.getIsVip()).build())
        );
        log.info("Indexed with version " + response.version());
    }

}
