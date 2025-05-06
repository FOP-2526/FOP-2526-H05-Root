package h05;

public class Camera extends AbstractEquipment {

    private int visibilityRange = 1;

    public Camera(int x, int y) {
        super(x, y, "Camera");
    }


    public int getVisibilityRange() {
        return visibilityRange;
    }

    public void setVisibilityRange(int visibilityRange) {
        this.visibilityRange = visibilityRange;
    }
}
