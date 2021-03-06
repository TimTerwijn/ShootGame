package walkgame.objects.hud;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import walkgame.interfaces.Controllable;
import walkgame.interfaces.Nameable;
import walkgame.interfaces.Shootable;
import walkgame.objects.map.Room;
import walkgame.objects.microObjects.Controlls;
import walkgame.objects.microObjects.Functions;
import walkgame.objects.microObjects.Key;
import walkgame.objects.microObjects.guns.Gun;
import walkgame.objects.parentClasses.Character;
import walkgame.views.parentClasses.MainView;

public class Player extends Character implements Controllable, Nameable, Shootable {

    public static Group group = new Group();

    private static final Image STANDARD_IMAGE = new Image("walkgame/res/player/player.png");
    private static final SimpleIntegerProperty PLAYER_HEALTH = new SimpleIntegerProperty(100);
    private static final double PLAYER_SPEED = 0;

    private String name;
    private Gun currentGun;
    private Room currentRoom;

    private final double SPRITE_SIZE = 32;


    public Player(Image[] image, String name, Gun currentGun)
    {
        super(image, new Point2D(0,0), PLAYER_HEALTH, PLAYER_SPEED);
        this.name = name;
        this.currentGun = currentGun;
        this.currentRoom =  (Room) Room.group.getChildren().get(0);


        Point2D playerSize = super.getSize();
        Point2D playerSpawn = new Point2D(MainView.getScreenCenter().getX() - (playerSize.getX() / 2f) , MainView.getScreenCenter().getY() - (playerSize.getY() / 2f));//todo: moet anders

        super.setX(playerSpawn.getX());
        super.setY(playerSpawn.getY());
    }

    public Player(String name, Gun currentGun)
    {
        this(new Image[]{STANDARD_IMAGE, currentGun.getImage()}, name, currentGun);
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    public void setCurrentGun(Gun currentGun) {
        this.currentGun = currentGun;
    }

    public Gun getCurrentGun() {
        return currentGun;
    }

    public Room getCurrentRoom() {
         return currentRoom;
    }

    @Override
    public Point2D getPoint2D()
    {
        return Hud.hudToMovableGroup(super.getPoint2D());
    }

    @Override
    public Point2D getCenter() {
        return Hud.hudToMovableGroup(super.getCenter());
    }

    @Override
    public Point2D getMaxPoint2D()
    {
        return Hud.hudToMovableGroup(super.getMaxPoint2D());
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    @Override
    public void move()
    {
        return;
    }

    @Override
    public void rotateImage(Point2D mouseCoordinates) {
        double angle = Functions.getAngle(MainView.getScreenCenter(), mouseCoordinates);

        super.setRotate(angle);
    }

    @Override
    public void checkButton(Controlls controlls) {
        for(Key key : controlls.getPressedButtons())
        {
            pressButton(key);
        }
        for(Key key : controlls.getReleasedButtons())
        {
            releaseButton(key);
        }
    }

    private void pressButton(Key k) {
        if(k == Controlls.up) {
            goNorth = true;
            goSouth = false;
        }
        else if(k == Controlls.right) {
            goEast = true;
            goWest = false;
        }
        else if(k == Controlls.down) {
            goSouth = true;
            goNorth = false;
        }
        else if(k == Controlls.left) {
            goWest = true;
            goEast = false;
        }
    }

    private void releaseButton(Key k) {
        if(k == Controlls.up) {
            goNorth = false;
        }
        else if(k == Controlls.right) {
            goEast = false;
        }
        else if(k == Controlls.down) {
            goSouth = false;
        }
        else if(k == Controlls.left) {
            goWest = false;
        }
        else if(k == Controlls.reload) {
            this.currentGun.reload();
        }
    }

    public Rectangle2D getRelativeRectangle2D()
    {
        Point2D center = this.getCenter();

        double newX = center.getX() - (SPRITE_SIZE / 2f);
        double newY = center.getY() - (SPRITE_SIZE / 2f);

        return new Rectangle2D(newX, newY, SPRITE_SIZE, SPRITE_SIZE);
    }

    @Override
    public void destroy() {
        Player.group.getChildren().remove(this);
    }

    @Override
    public void addNodeToList()
    {
        Player.group.getChildren().clear();
        Player.group.getChildren().add(0,this);

        MainView.CONTROLLABLE_LIST.add(this);
        MainView.DESTRUCTIBLE_LIST.add(this);
        MainView.SHOOTABLE_LIST.add(this);
    }
}
