package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
@RestController
public class Application {

  static class Self {
    public String href;
  }

  static class Links {
    public Self self;
  }

  static class PlayerState {
    public Integer x;
    public Integer y;
    public String direction;
    public Boolean wasHit;
    public Integer score;
  }

  static class Arena {
    public List<Integer> dims;
    public Map<String, PlayerState> state;
  }

  static class ArenaUpdate {
    public Links _links;
    public Arena arena;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.initDirectFieldAccess();
  }

  @GetMapping("/")
  public String index() {
    return "Let the battle begin!";
  }

  @PostMapping("/**")
  public String index(@RequestBody ArenaUpdate arenaUpdate) {
    System.out.println(arenaUpdate.toString());
    //String[] commands = new String[]{"F", "R", "L", "T"};

    Arena myArena = arenaUpdate.arena;
    Integer x = myArena.dims.get(0);
    Integer y = myArena.dims.get(1);

    List<Integer> arenaDims = myArena.dims;


    PlayerState myState = arenaUpdate.arena.state.get(arenaUpdate._links.self.href);
    Integer myX = myState.x;
    Integer myY = myState.y;
    String myD = myState.direction;
    String[] commands = new String[]{"F", "R", "L", "T", "T", "T", "T", "T", "T", "T", "T","T","T"};

    int i = new Random().nextInt(12);

//    if (myX == 0 && myD.equals())
//
//    if (myX == 0 && myState.direction == "")

    return commands[i];

  }

}

