package walkgame.controllers;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import walkgame.controllers.parentClasses.MainController;
import walkgame.interfaces.Controllable;
import walkgame.interfaces.Destructible;
import walkgame.interfaces.Moveable;
import walkgame.objects.microObjects.Coordinates;
import walkgame.views.FirstMainView;
import walkgame.views.parentClasses.MainView;

import java.util.LinkedList;

public class FirstViewMainController extends MainController {

    public FirstMainView firstView;
    public FirstViewMainController(FirstMainView firstView) {
        this.firstView = firstView;
    }



    public void pressKeyButton(KeyCode k)
    {
        for(Group group : MainView.getRootArray())
        {
            if (group instanceof Controllable)
            {
                ((Controllable) group).pressButton(k);
            }

            for(Node node : group.getChildren())
            {
                if (node instanceof Controllable)
                {
                    ((Controllable) node).pressButton(k);
                }
            }
        }
    }

    public void releaseKeyButton(KeyCode k)
    {
        for(Group group : MainView.getRootArray())
        {
            if (group instanceof Controllable)
            {
                ((Controllable) group).releaseButton(k);
            }

            for(Node node : group.getChildren())
            {
                if (node instanceof Controllable)
                {
                    ((Controllable) node).releaseButton(k);
                }
            }
        }
    }

    public void mouseClick(Coordinates mouseCoordinates)
    {
        firstView.player.getCurrentGun().shoot(MainView.screenCenter, mouseCoordinates);
    }

    public void mouseRelease()
    {
       firstView.player.getCurrentGun().releaseTrigger();
    }

    @Override
    public void tick()
    {
        for(Group rootItem : MainView.getRootArray())
        {
            if(rootItem instanceof Moveable)
            {
                ((Moveable) rootItem).move();
            }
        }

        for(Node node : MainView.getListOfAllNodes())//todo: Schiet aanpassen zodat het weer werkt
        {
            LinkedList<Destructible> toDestroy = new LinkedList<>();
            if(node instanceof Moveable)
            {
                Moveable moveable = ((Moveable) node);
                moveable.move();
            }
            if(node instanceof Destructible)
            {
                Destructible destructible = (Destructible) node;
                if(destructible.getHealth() <= 0)
                {
                    toDestroy.add(destructible);
                }
            }

            for(Destructible destructible : toDestroy)//after the loop, delete all destructibles
            {
                MainView.getRoot().remove(destructible);
                destructible.destroy();
            }
        }
    }
}
