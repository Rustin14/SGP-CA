package SGP.CA.GUI;

import SGP.CA.Domain.Member;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;

public class MemberCell extends ListCell<Member> {

    @Override
    protected void updateItem(Member member, boolean empty) {
        super.updateItem(member, empty);

        if(empty || member == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        else {
            Data data = new Data();
            data.setInfo(member);
            setGraphic(data.getBox());
        }
    }
}