package net.azagwen.atbyw.main;

import net.azagwen.atbyw.items.ShroomStickEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

import static net.azagwen.atbyw.main.AtbywMain.*;

public class AtbywEntityType {

    public static final EntityType<ShroomStickEntity> SHROOMSTICK = Registry.register(
            Registry.ENTITY_TYPE,
            newID("shroomstick"),
            FabricEntityTypeBuilder.<ShroomStickEntity>create(SpawnGroup.MISC, ShroomStickEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f, 0.75f))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );
}
