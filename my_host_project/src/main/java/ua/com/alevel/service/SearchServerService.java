package ua.com.alevel.service;

import java.util.List;

public interface SearchServerService {

    List<String> fetchSuggestions(String query);
}
