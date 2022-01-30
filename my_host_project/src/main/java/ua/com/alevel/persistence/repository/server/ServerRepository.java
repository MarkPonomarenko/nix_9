package ua.com.alevel.persistence.repository.server;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.provider.Provider;
import ua.com.alevel.persistence.entity.server.Server;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.List;

@Repository
public interface ServerRepository extends BaseRepository<Server> {

    List<Server> findByProvider(Provider provider);
    List<Server> findByCpuModelContaining(String cpuModel);
}
