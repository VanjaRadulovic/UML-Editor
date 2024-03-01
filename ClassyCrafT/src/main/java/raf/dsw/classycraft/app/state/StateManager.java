package raf.dsw.classycraft.app.state;

import raf.dsw.classycraft.app.state.states.*;

public class StateManager {

    private State currentState;
    private AddElement addElement;
    private AddRelationship addRelationship;
    private DeleteElement deleteElement;
    private AddContent addContent;
    private SelectState selectState;
    private ZoomInState zoomInState;
    private ZoomOutState zoomOutState;
    private DuplicateState duplicateState;
    private MoveState moveState;

    public StateManager() {
        initStates();
    }

    private void initStates() {
        addElement = new AddElement();
        addRelationship = new AddRelationship();
        deleteElement = new DeleteElement();
        selectState = new SelectState();
        currentState = addElement;
        zoomInState = new ZoomInState();
        addContent = new AddContent();
        zoomOutState = new ZoomOutState();
        duplicateState = new DuplicateState();
        moveState = new MoveState();
    }

    public State getCurrent(){
        return currentState;
    }

    public void setAddElementState(){
        currentState = addElement;
    }

    public void setDeleteElementState(){
        currentState = deleteElement;
    }

    public void setAddRelationshipState(){
        currentState = addRelationship;
    }

    public void setAddContentState(){
        currentState = addContent;
    }

    public void setSelectState(){currentState = selectState;}

    public void setZoomInState(){
        currentState =zoomInState;
    }
    public void setZoomOutState(){
        currentState = zoomOutState;
    }
    public void setDuplicateState(){currentState = duplicateState;}
    public void setMoveState(){
        currentState = moveState;
    }

}
