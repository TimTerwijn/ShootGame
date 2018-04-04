package test.objects.parentClasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.TestClasses;
import walkgame.objects.map.Room;
import walkgame.objects.microObjects.Compass;
import walkgame.objects.microObjects.Coordinates;
import walkgame.objects.parentClasses.ImageViewObject;

import static org.junit.jupiter.api.Assertions.*;

class ImageViewObjectTest extends TestClasses {

    ImageViewObject imageViewObject;

    @BeforeEach
    void setUp() {
        super.innit();
        imageViewObject = new Room(new Coordinates(0,0));
    }

    @Test
    void getCoordinate() {
        String expected = "0.00.0";
        String actual = String.format("%S%S", imageViewObject.getCoordinate().getX(), imageViewObject.getCoordinate().getY());
        assertEquals(expected, actual);
    }

    @Test
    void getCollisionDirection() {//todo: update test naar arraylistChar
        Coordinates[] coordinatesList = {new Coordinates(0,-300), new Coordinates(300,0), new Coordinates(0,300), new Coordinates(-300,0)};
        char[] compass = {Compass.NORTH, Compass.EAST, Compass.SOUTH, Compass.WEST};

        for (int i = 0; i < coordinatesList.length; i++) {
            System.out.println("test: " + compass[i]);
            room = new Room(coordinatesList[i]);
            char expected = compass[i];
            char actual = 0;//imageViewObject.getCollisionDirection(room);
            assertEquals(expected, actual);
        }
    }

    @Test
    void containsObject() {
        assertTrue(imageViewObject.contains(player.getPoint2D()));

        assertFalse(player.contains((new Room(new Coordinates(300, 300))).getPoint2D()));
    }

    @Test
    void getCenter() {
        Coordinates a = room.getCenter();
        Coordinates b = new Coordinates(150,150);

        assertTrue(a.equals(b));

        b = new Coordinates(100,100);
        
        assertFalse(a.equals(b));
    }
}