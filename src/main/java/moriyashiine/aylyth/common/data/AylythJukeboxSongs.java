package moriyashiine.aylyth.common.data;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public interface AylythJukeboxSongs {
    RegistryKey<JukeboxSong> POMEGRANATE_MUSIC_DISC = bind("pomegranate");

    private static RegistryKey<JukeboxSong> bind(String name) {
        return RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Aylyth.id(name));
    }
}
