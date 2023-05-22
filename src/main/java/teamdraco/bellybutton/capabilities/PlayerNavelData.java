package teamdraco.bellybutton.capabilities;

import net.minecraft.nbt.CompoundTag;

public class PlayerNavelData {
    private int posX;
    private int posY;
    private int posZ;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosZ() {
        return posZ;
    }

    public void setPosZ(int posZ) {
        this.posZ = posZ;
    }

    public void copyFrom(PlayerNavelData source) {
        posX = source.posX;
        posY = source.posY;
        posZ = source.posZ;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("navelPosX", posX);
        tag.putInt("navelPosY", posY);
        tag.putInt("navelPosZ", posZ);
    }

    public void loadNBTData(CompoundTag tag) {
        posX = tag.getInt("navelPosX");
        posY = tag.getInt("navelPosY");
        posZ = tag.getInt("navelPosZ");
    }
}

