package net.sixik.sdmshoprework.common.register;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.sixik.sdmshoprework.SDMShopRework;

public class SoundRegister {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(SDMShopRework.MODID, Registry.SOUND_EVENT_REGISTRY);

    public static RegistrySupplier<SoundEvent> registrySoundEvents(SoundEvent soundEvent) {
        return SOUND_EVENTS.register(soundEvent.getLocation().getPath(), () -> {
            return soundEvent;
        });
    }

    public static SoundEvent registerSound(String soundName) {
        ResourceLocation location = new ResourceLocation(SDMShopRework.MODID, soundName);
        SoundEvent event = new SoundEvent(location);
        registrySoundEvents(event);
        return event;
    }

    public static void init(){
        SOUND_EVENTS.register();
    }
}
