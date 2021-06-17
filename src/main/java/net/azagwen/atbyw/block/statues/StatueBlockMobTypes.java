package net.azagwen.atbyw.block.statues;

import net.minecraft.util.Identifier;
import net.minecraft.util.shape.VoxelShape;

import static net.azagwen.atbyw.main.AtbywMain.NewAtbywID;

public enum StatueBlockMobTypes implements StatueBlockMobType {
    //Bugs & Shell creatures
    BEE("bee", StatueVoxelShapes.BEE_OUTLINES, StatueVoxelShapes.BEE_COLLISIONS),
    ENDERMITE("endermite", StatueVoxelShapes.ENDERMITE_OUTLINES),
    SHULKER("shulker", NewAtbywID("gameplay/shulker_statue"), StatueVoxelShapes.SHULKER_OUTLINES, StatueVoxelShapes.SHULKER_COLLISIONS),
    SILVERFISH("silverfish", StatueVoxelShapes.SILVERFISH_OUTLINES),

    //Mamals
    AXOLOTL("axolotl", StatueVoxelShapes.AXOLOTL_OUTLINES),
    BAT("axolotl", StatueVoxelShapes.BEE_OUTLINES),
    CAT("cat", StatueVoxelShapes.CAT_OUTLINES),
    CHICKEN("chicken", NewAtbywID("gameplay/chicken_statue"), StatueVoxelShapes.CHICKEN_OUTLINES),
    FOX("fox", StatueVoxelShapes.FOX_OUTLINES),
    RABBIT("rabbit", NewAtbywID("gameplay/rabbit_statue"), StatueVoxelShapes.RABBIT_OUTLINES, StatueVoxelShapes.RABBIT_COLLISIONS),
    WOLF("wolf", StatueVoxelShapes.WOLF_OUTLINES),

    //Fishes
    COD("cod", NewAtbywID("gameplay/cod_statue"), StatueVoxelShapes.COD_OUTLINES),
    PUFFER_FISH("puffer_fish", NewAtbywID("gameplay/puffer_fish_statue"), StatueVoxelShapes.PUFFER_FISH_OUTLINES),
    SALMON("salmon", NewAtbywID("gameplay/salmon_statue"), StatueVoxelShapes.SALMON_OUTLINES),

    //Slimes & others
    MAGMA_CUBE("magma_cube", NewAtbywID("gameplay/magma_cube_statue"), StatueVoxelShapes.SLIME_SMALL_OUTLINES),
    SLIME("slime", NewAtbywID("gameplay/slime_statue"), StatueVoxelShapes.SLIME_SMALL_OUTLINES);

    //Negative Z = NORTH
    //Positive Z = SOUTH
    //Negative X = EAST
    //Positive X = WEST

    private final String name;
    private final Identifier lootTableID;
    private final VoxelShape[] outlineShapes;
    private VoxelShape[] collisionShapes;

    private String makeName(String name) {
        return name + "_statue";
    }

    StatueBlockMobTypes(String name, Identifier lootTableID, VoxelShape[] outlineShapes, VoxelShape[] collisionShapes) {
        this.name = makeName(name);
        this.lootTableID = lootTableID;
        this.outlineShapes = outlineShapes;
        this.collisionShapes = collisionShapes;
    }

    StatueBlockMobTypes(String name, Identifier lootTableID, VoxelShape[] outlineShapes) {
        this.name = makeName(name);
        this.lootTableID = lootTableID;
        this.outlineShapes = outlineShapes;

    }

    StatueBlockMobTypes(String name, VoxelShape[] outlineShapes, VoxelShape[] collisionShapes) {
        this.name = makeName(name);
        this.lootTableID = null;
        this.outlineShapes = outlineShapes;
        this.collisionShapes = collisionShapes;
    }

    StatueBlockMobTypes(String name, VoxelShape[] outlineShapes) {
        this.name = makeName(name);
        this.lootTableID = null;
        this.outlineShapes = outlineShapes;

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Identifier getLootTable() {
        return lootTableID;
    }

    @Override
    public VoxelShape getOutlineShape(int direction) {
        return this.outlineShapes[direction];
    }

    @Override
    public VoxelShape getCollisionShape(int direction) {
        return this.collisionShapes[direction];
    }

    @Override
    public VoxelShape[] getOutlineShapes() {
        return this.outlineShapes;
    }

    @Override
    public VoxelShape[] getCollisionShapes() {
        return this.collisionShapes;
    }

}
