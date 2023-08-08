package net.starelement.picoplay;

public class Profile {

    public int count = 1;

    @Override
    protected Profile clone() {
        Profile profile = new Profile();
        profile.count = this.count;
        return profile;
    }
}
