package raf.dsw.classycraft.app.gui.swing.controller;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.gui.swing.controller.diagramaction.*;

@Getter
@Setter
public class ActionManager {

    private ExitAction exitAction;
    private NewAction newAction;
    private AboutAction aboutAction;
    private DeleteAction deleteAction;
    private EditAction editAction;
    private AddElementAction addElementAction;
    private DeleteElementAction deleteElement;
    private AddRelationshipAction addRelationship;
    private AddContentAction addContent;
    private SelectAction selectAction;
    private  ZoomInAction zoomInAction;
    private ZoomOutAction zoomOutAction;
    private DuplicateAction duplicateAction;
    private MoveAction moveAction;
    private OpenAction openAction;
    private SaveAsAction saveAsAction;
    private UndoAction undoAction;
    private RedoAction redoAction;

    public ActionManager() {
        initialiseActions();
    }

    private void initialiseActions() {
        exitAction = new ExitAction();
        newAction = new NewAction();
        aboutAction=new AboutAction();
        deleteAction=new DeleteAction();
        editAction=new EditAction();
        addElementAction=new AddElementAction();
        deleteElement=new DeleteElementAction();
        addRelationship=new AddRelationshipAction();
        addContent=new AddContentAction();
        selectAction = new SelectAction();
        zoomInAction = new ZoomInAction();
        zoomOutAction = new ZoomOutAction();
        duplicateAction = new DuplicateAction();
        moveAction = new MoveAction();
        openAction = new OpenAction();
        saveAsAction = new SaveAsAction();
        undoAction = new UndoAction();
        redoAction = new RedoAction();
    }
}
