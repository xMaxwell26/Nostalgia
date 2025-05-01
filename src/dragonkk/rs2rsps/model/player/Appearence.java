package dragonkk.rs2rsps.model.player;

import java.io.Serializable;

public class Appearence implements Serializable {

    private static final long serialVersionUID = 5497272754883262586L;

    private short npcType;
    private byte gender;
    private byte[] look;
    private byte[] colour;
    private boolean male;
    private int skullIcon = -1;
    private transient Player p;


    public Appearence() {
        if (gender < 0 && gender > 1) {
            gender = 1;
        }
        this.setMale(true);
        this.setNpcType((short) -1);
        this.resetAppearence();
    }

    public void resetAppearence() {
        if (gender < 0 && gender > 1) {
            gender = 1;
        }
        this.setLook(new byte[7]);
        this.setColour(new byte[5]);
        this.setMale(true);
        look[0] = 3; // Hair
        look[1] = 10; // Beard
        look[2] = 22; // Torso
        look[3] = 108; // Arms
        look[4] = 33; // Bracelets
        look[5] = 88; // Legs
        look[6] = 42; // Shoes
        colour[2] = 16;
        colour[1] = 16;
        gender = 0;
        for (int i = 0; i < 5; i++) {
            colour[2] = 16;
            colour[1] = 16;
            colour[0] = 3;
        }
    }

    public void OwnerAppearence() {
        this.setLook(new byte[7]);
        this.setColour(new byte[5]);
        look[0] = p.ohair; // Hair
        look[1] = p.obeard; // Beard
        look[2] = p.otorso; // Torso
        look[3] = p.oarms; // Arms
        look[4] = 33; // Bracelets
        look[5] = p.olegs; // Legs
        look[6] = 42; // Shoes
        colour[2] = p.c1;
        colour[1] = p.c2;
        for (int i = 0; i < 5; i++) {
            colour[2] = p.c3;
            colour[1] = p.c4;
            colour[0] = p.c5;
        }
    }


    public void female() {
        if (gender < 0 && gender > 1) {
            gender = 1;
        }
        this.setLook(new byte[7]);
        this.setColour(new byte[5]);
        this.setMale(false);
        look[0] = 48; // Hair
        look[1] = 57; // Beard
        look[2] = 57; // Torso
        look[3] = 65; // Arms
        look[4] = 68; // Bracelets
        look[5] = 77; // Legs
        look[6] = 80; // Shoes
        colour[2] = 16;
        colour[1] = 16;
        for (int i = 0; i < 5; i++) {
            colour[2] = 16;
            colour[1] = 16;
            colour[0] = 3;
            gender = 1;
        }
    }

    public void setNpcType(short npcType) {
        this.npcType = npcType;
    }

    public short getNpcType() {
        return npcType;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public byte getGender() {
        return gender;
    }

    public void setLook(byte[] look) {
        this.look = look;
    }

    public byte[] getLook() {
        return look;
    }

    public void setColour(byte[] colour) {
        this.colour = colour;
    }

    public byte[] getColour() {
        return colour;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public boolean isMale() {
        return male;
    }

    /**
     * @param skullIcon the skullIcon to set
     */
    public void setSkullIcon(int skullIcon) {
        this.skullIcon = skullIcon;
    }

    /**
     * @return the skullIcon
     */
    public int getSkullIcon() {
        return skullIcon;
    }

}
