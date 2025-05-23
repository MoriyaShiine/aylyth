package moriyashiine.aylyth.datagen.common;

import moriyashiine.aylyth.common.data.AylythJukeboxSongs;
import moriyashiine.aylyth.common.world.AylythSoundEvents;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public final class AylythJukeboxSongsBootstrap {
    private AylythJukeboxSongsBootstrap() {}

    public static void bootstrap(Registerable<JukeboxSong> context) {
        register(context, AylythJukeboxSongs.POMEGRANATE_MUSIC_DISC, AylythSoundEvents.POMEGRANATE_MUSIC_DISC, 118, 14);
    }

    private static void register(Registerable<JukeboxSong> registry,
                                 RegistryKey<JukeboxSong> key,
                                 RegistryEntry<SoundEvent> soundEvent,
                                 int lengthInSeconds,
                                 int comparatorOutput
    ) {
        registry.register(key,
                new JukeboxSong(
                        soundEvent,
                        Text.translatable(Util.createTranslationKey("jukebox_song", key.getValue())),
                        lengthInSeconds,
                        comparatorOutput
                ));
    }
}
