package ua.com.alevel.persistence.repository.server;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.provider.Provider;
import ua.com.alevel.persistence.repository.BaseRepository;

@Repository
public interface ProviderRepository extends BaseRepository<Provider> { }
