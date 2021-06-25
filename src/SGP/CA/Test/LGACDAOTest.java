package SGP.CA.Test;

import SGP.CA.DataAccess.LGACDAO;
import SGP.CA.Domain.LGAC;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class LGACDAOTest {

    LGACDAO lgacdao = new LGACDAO();

    @Test
    public void searchLGACbyLineNameTest() throws SQLException {
        LGAC lgac = new LGAC();
        lgac = lgacdao.searchLGACbyLineName("Redes de conocimiento y aprendizaje");

        Assert.assertEquals(1, lgac.getIdLGAC(),0);
    }

    @Test
    public void searchLGACbyIDTest() throws SQLException {
        LGAC lgac = new LGAC();
        lgac = lgacdao.searchLGACbyID(1);

        Assert.assertEquals(1, lgac.getIdLGAC(),0);
    }

    @Test
    public void getAllLinesTest() throws SQLException {
        ArrayList<LGAC> allLines = new ArrayList<>();
        allLines = lgacdao.getAllLines();

        Assert.assertEquals(1, allLines.get(0).getIdLGAC() ,0);
    }

}
