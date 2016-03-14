package unc.group16.bo.managers.oracle;

import org.junit.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import unc.group16.bo.JDBC;
import unc.group16.data.Drink;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class OracleDrinksManagerTest {
    private OracleDrinksManager manager;
    private Connection spyConnection;

    @Before
    public void setUp() throws Exception {
        manager = new OracleDrinksManager();
        JDBC spyJdbc = spy(new JDBC());
        manager.setJDBC(spyJdbc);

        spyConnection = spy(spyJdbc.getConnection());
        doReturn(spyConnection).when(spyJdbc).getConnection();
    }

    @After
    public void tearDown() throws Exception {
        spyConnection.close();
    }

    @Test
    public void testCreate() throws Exception {
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            PreparedStatement spyPS = spy(new JDBC().getConnection().prepareStatement((String) args[0], (int[])args[1]));
            assertEquals("INSERT INTO DRINKS VALUES (null, ?, ?, ?, ?)", args[0]);

            doReturn(0).when(spyPS).executeUpdate();
            return spyPS;
        }).when(spyConnection).prepareStatement(anyString(), any(int[].class));


        Drink drink = new Drink(null, 2, BigDecimal.TEN, "Drink", "");
        manager.create(drink);
    }

    @Test
    public void testRead() throws Exception {
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            PreparedStatement spyPS = spy(new JDBC().getConnection().prepareStatement((String) args[0]));

            assertEquals("SELECT * FROM DRINKS WHERE DRNK_ID=42", args[0]);

            return spyPS;
        }).when(spyConnection).prepareStatement(anyString());

        manager.read(42L);
    }

    @Test
    public void testUpdate() throws Exception {
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            PreparedStatement spyPS = spy(new JDBC().getConnection().prepareStatement((String) args[0]));

            assertEquals("UPDATE DRINKS SET VOLUME=?, PRICE=?, TITLE=?, DESCRIPTION=? WHERE DRNK_ID=42", args[0]);

            doReturn(1).when(spyPS).executeUpdate();
            return spyPS;
        }).when(spyConnection).prepareStatement(anyString());


        Drink drink = new Drink(42L, 2, BigDecimal.TEN, "Drink", "");
        manager.update(drink);
    }

    @Test
    public void testDelete() throws Exception {
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            PreparedStatement spyPS = spy(new JDBC().getConnection().prepareStatement((String) args[0]));

            assertEquals("DELETE FROM DRINKS WHERE DRNK_ID=42", args[0]);

            doReturn(1).when(spyPS).executeUpdate();
            return spyPS;
        }).when(spyConnection).prepareStatement(anyString());


        Drink drink = new Drink(42L, 2, BigDecimal.TEN, "Drink", "");
        manager.delete(42L);
    }
}