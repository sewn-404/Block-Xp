package net.sewn404.blockxp;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

public class BlockxpDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider((output, registriesFuture) -> new FabricDynamicRegistryProvider(
                output, 
                registriesFuture
        ) {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
                entries.addAll(registries.getWrapperOrThrow(RegistryKeys.CONFIGURED_FEATURE));
                entries.addAll(registries.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE));
            }

            @Override
            public String getName() {
                return "BlockXP World Gen";
            }
        });
    }
}