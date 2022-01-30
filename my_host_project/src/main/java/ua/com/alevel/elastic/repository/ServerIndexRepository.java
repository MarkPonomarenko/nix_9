package ua.com.alevel.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ua.com.alevel.elastic.index.ServerIndex;

import java.util.List;

@Repository
public interface ServerIndexRepository extends ElasticsearchRepository<ServerIndex, String> {

    List<ServerIndex> findByModelContaining(String model);
}
