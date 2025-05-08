package h05;

public class Camera extends AbstractEquipment {

    public static final int DEFAULT_VISIBILITY_RANGE = 1;
    private final int visibilityRange;

    public Camera(int visibilityRange) {
        super("Camera");
        this.visibilityRange = visibilityRange;
    }

    public Camera() {
        this(DEFAULT_VISIBILITY_RANGE);
    }

    public int getVisibilityRange() {
        return visibilityRange;
    }
}
