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

    PlayerState myState = arenaUpdate.arena.state.get(arenaUpdate._links.self.href);

    if(!myState.wasHit)
    {
      String[] com = new String[]{"F","R","L", "T", "T", "T", "T", "T", "T", "T", "T", "T", "T", "T"};
      int j = new Random().nextInt(13);
      return com[j];
    } else {
      String[] commands = new String[]{"F","R","R", "L", "F", "F", "F", "R", "L"};
      int i = new Random().nextInt(8);
      Arena myArena = arenaUpdate.arena;
      Integer x = myArena.dims.get(0);
      Integer y = myArena.dims.get(1);

      List<Integer> arenaDims = myArena.dims;

      Integer myX = myState.x;
      Integer myY = myState.y;
      String myD = myState.direction;

      String myRes = commands[i];

      if (myX == 0 && myD.equals("E") && myRes.equals("T"))
      {
        if (myY == 0)     //top left
        {
          return "L";
        }

        if  (myY.equals(arenaDims.get(1)))
        {     //Bottom left
          return "R";
        }

        return "L";
      }

      if (myY == 0 && myD.equals("N") && myRes.equals("T"))
      {
        if (myX == 0)     //top left
        {
          return "R";
        }

        if  (myX.equals(arenaDims.get(0)))
        {     //top Right
          return "L";
        }

        return "R";
      }

      if (myX.equals(arenaDims.get(0)) && myD.equals("W") && myRes.equals("T"))
      {
        if (myY == 0)     //top Right
        {
          return "R";
        }

        if  (myY.equals(arenaDims.get(1)))
        {     //Bottom Right
          return "L";
        }

        return "L";
      }

      if (myY.equals(arenaDims.get(1)) && myD.equals("S") && myRes.equals("T"))
      {
        if (myX == 0)     //Bottom left
        {
          return "L";
        }

        if  (myX.equals(arenaDims.get(0)))
        {     //Bottom Right
          return "R";
        }

        return "R";
      }
      return myRes;
    }
  }

}

