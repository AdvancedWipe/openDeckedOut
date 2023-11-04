package com.github.advancedwipe.opendeckedout.utils;

import com.github.advancedwipe.opendeckedout.player.DungeonPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.Note.Tone;

public class SoundUtils {

  public static Random random = new Random();

  public static void playCoinSound(List<DungeonPlayer> players, Location location) {
    var coinSounds = getCoinSounds();
    players.forEach(p -> p.getPlayer()
        .playNote(location, Instrument.CHIME, coinSounds.get(random.nextInt(coinSounds.size()))));
  }

  private static List<Note> getCoinSounds() {
    List<Note> coinSounds = new ArrayList<>();

    coinSounds.add(Note.sharp(0, Tone.F));
    coinSounds.add(Note.natural(0, Tone.G));
    coinSounds.add(Note.sharp(0, Tone.G));
    coinSounds.add(Note.natural(0, Tone.A));
    coinSounds.add(Note.sharp(0, Tone.B));

    return coinSounds;
  }

}
