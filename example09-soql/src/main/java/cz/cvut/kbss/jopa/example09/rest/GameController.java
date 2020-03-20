package cz.cvut.kbss.jopa.example09.rest;

import cz.cvut.kbss.jopa.example09.model.Game;
import cz.cvut.kbss.jopa.example09.persistence.GameRepository;
import cz.cvut.kbss.jsonld.JsonLd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameRepository gameRepository;

    @Autowired
    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping(produces = JsonLd.MEDIA_TYPE)
    public List<Game> getGames(@RequestParam(name = "from", required = false)
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                               @RequestParam(name = "to", required = false)
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        if (from != null) {
            if (to == null) {
                to = LocalDate.now();
            }
            return gameRepository.findAll(from, to);
        }
        return gameRepository.findAll();
    }
}
