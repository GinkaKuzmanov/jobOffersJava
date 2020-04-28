package entities;

public enum JobDuration {
    PERMANENT, TEMPORARY;

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1);
    }
}
