package net.sewn404.blockxp.block;

import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class XPOreBlock extends ExperienceDroppingBlock {
    public XPOreBlock(Settings settings) {
        super(UniformIntProvider.create(1, 4), 
            settings
                .luminance(state -> 5)
                .strength(1.5f, 6.0f)
        );
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockState result = super.onBreak(world, pos, state, player);
        
        if (!world.isClient()) {
            int xpAmount = world.random.nextBetween(3, 7); // Drop 3-7 XP orbs
            ExperienceOrbEntity.spawn((ServerWorld) world, pos.toCenterPos(), xpAmount);
        }
        
        return result;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(5) == 0) { // 20% chance to spawn particles each tick
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + random.nextDouble();
            double z = pos.getZ() + random.nextDouble();

            world.addParticle(
                ParticleTypes.ENCHANTED_HIT,
                x, y, z,
                random.nextGaussian() * 0.02D,
                random.nextGaussian() * 0.02D,
                random.nextGaussian() * 0.02D
            );
        }
    }
}