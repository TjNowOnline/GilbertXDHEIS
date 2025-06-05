package org.example.gilbertxdheis.presentation;
import org.springframework.web.bind.annotation.*;
import org.example.gilbertxdheis.domain.Item;
import org.example.gilbertxdheis.application.ItemService;
import java.util.List;


@RestController
@RequestMapping("/api/items")
public class ItemRestController {

    private final ItemService itemService;

    public ItemRestController (ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping ("/search")
    public List<Item> SearchItems (@RequestParam(required = false) String query){
        return itemService.searchItem(query);
    }
}
