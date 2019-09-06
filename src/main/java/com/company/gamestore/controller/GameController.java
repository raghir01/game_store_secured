package com.company.gamestore.controller;

import com.company.gamestore.service.InvoiceService;
import com.company.gamestore.viewmodel.GameViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/store")
public class GameController {


    @Autowired
    private InvoiceService service;

    @PostMapping("/games")
    @ResponseStatus(value = HttpStatus.CREATED)
    public GameViewModel addGame(@RequestBody @Valid GameViewModel gameViewModel){
        return service.addGame(gameViewModel);
    }

    @GetMapping("/games/{gameId}")
    @ResponseStatus(value = HttpStatus.OK)
    public GameViewModel getGame(@PathVariable int gameId){
        return service.getGame(gameId);
    }

    @GetMapping("/games")
    @ResponseStatus(value = HttpStatus.OK)
    public List<GameViewModel> getAllGames() {
        return service.getAllGame();
    }


    @PutMapping("/games")
    @ResponseStatus(value = HttpStatus.OK)
    public GameViewModel updateGame(@RequestBody @Valid GameViewModel gameViewModel){
        return service.updateGame(gameViewModel);
    }

    @DeleteMapping("/games/{gameId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteGame(@PathVariable int gameId){
        service.deleteGame(gameId);
    }

    @GetMapping("/games/studio/{studio}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<GameViewModel> findGamesByStudio(@PathVariable String studio) {
        return service.findGamesByStudio(studio);
    }

    @GetMapping("/games/ersbRating/{ersbRating}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<GameViewModel> findGamesByERSBRating(@PathVariable String ersbRating) {
        return service.findGamesByERSBRating(ersbRating);
    }

    @GetMapping("/games/title/{title}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<GameViewModel> findGamesByTitle(@PathVariable String title) {
        return service.findGamesByTitle(title);
    }

}
