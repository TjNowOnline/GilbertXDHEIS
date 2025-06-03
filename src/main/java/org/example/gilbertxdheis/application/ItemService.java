package org.example.gilbertxdheis.application;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.example.gilbertxdheis.domain.Item;
import org.example.gilbertxdheis.infrastructure.JdbcItemRepository;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



import java.util.List;

@Service
public class ItemService {
    private final JdbcItemRepository itemRepository;
    private final LevenshteinDistance levenshtein = new LevenshteinDistance(2);
    //Levenshtein is our fuzzy search algorithm

    public ItemService(JdbcItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getItemsForSaleBySeller(Long userId) {
        return itemRepository.findItemsBySellerIdAndStatus(userId, "FOR_SALE");
    }

    public List<Item> getSoldItemsBySeller(Long userId) {
        return itemRepository.findItemsBySellerIdAndStatus(userId, "SOLD");
    }

    public List<Item> getAllItems(){
        return (List<Item>) itemRepository.findAll(); }

    public List<Item> searchItem (String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllItems();
        }
        String lowerQuery = query.toLowerCase();
        return getAllItems().stream().filter(item -> isItemMatch(item,lowerQuery)).collect(Collectors.toList());
//                isSimilar(item.getDescription(),lowerQuery) ||
//                isSimilar(item.getCategory(),lowerQuery).collect(Collectors.toList());
    }

    private boolean isItemMatch (Item item, String query){
        return  isSimilar(item.getTitle(),query) ||
                isSimilar(item.getDescription(),query) ||
                isSimilar(item.getCategory(),query);
    }

    private boolean isSimilar(String text, String query) {
        if (text == null)
            return false;


        String lowerText = text.toLowerCase();
        return Arrays.stream(lowerText.split(" ")).anyMatch(word -> levenshtein.apply(word, query) <= 2);
    }


    public Item findById(Long id) {
        return (Item) itemRepository.read(id);
    }
}
