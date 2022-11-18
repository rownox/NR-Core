package me.rownox.nrcore.utils.sql;

import me.rownox.nrcore.NRCore;
import me.rownox.nrcore.utils.ChatUtils;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class SQLHelper {
    private static final Statement STMT = NRCore.getDatabaseThread().getDB().getStmt();

    /**
     * Create a SQL Table from the given parameters. NOTE: MUST BE VALID SQL
     * @param NAME Name of the new Table to create.
     * @param columns The Colums, {String...}, Will take "ID CHAR(36) PRIMARY KEY NOT NULL", "ALIVE BOOL NOT NULL"
     */
    public static void createTable(@Nonnull final String NAME, @Nonnull final String... columns) {
        StringBuilder sb = new StringBuilder();
        for (String str : columns) {
            sb.append(str).append(",");
        }
        try {
            STMT.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS " + NAME
                    + " (" + ChatUtils.removeLastChar(sb.toString()) + ")"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Intialize the values of a row in a given SQL Table
     * @param NAME Name of the Table
     * @param DEFAULT_PARAM Defualt Variables of the Table. E.g. (ID, ALIVE)
     * @param VALUES Values to insert. E.g: 100, true
     */
    public static void initializeValues(@Nonnull final String NAME, @Nonnull final String DEFAULT_PARAM, @Nonnull final String... VALUES) {
        StringBuilder sb = new StringBuilder();
        for (String str : VALUES) {
            sb.append(str).append(",");
        }

        try {
            STMT.executeUpdate("INSERT INTO " + NAME + " (" + DEFAULT_PARAM + ") VALUES (" + ChatUtils.removeLastChar(sb.toString()) + ");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update a value from a given row and given column
     * @param NAME Name of the SQL Table.
     * @param ID The ID Column, or first Column of the table.
     * @param ID_VALUE The value of the ID Column that we are searching for.
     * @param COLUMN The Column you want to change
     * @param NEW_VALUE The new value for the specified column.
     */
    public static void updateValue(@Nonnull final String NAME, @Nonnull final String ID, @Nonnull final String ID_VALUE, @Nonnull final String COLUMN, @Nonnull final String NEW_VALUE) {
        try {
            STMT.executeUpdate("UPDATE " + NAME + " set " + COLUMN + " = " + NEW_VALUE + " WHERE " + ID + " = " + ID_VALUE + ";");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check if a row exists
     * @param NAME Name of the SQL Table.
     * @param ID The ID Column, or first Column of the table.
     * @param ID_VALUE The value of the ID Column that we are searching for.
     * @return Boolean if the row exists
     */
    public static boolean ifRowExists(@Nonnull final String NAME, @Nonnull final String ID, @Nonnull final String ID_VALUE) {
        try {
            ResultSet rs = STMT.executeQuery("SELECT * FROM " + NAME + " WHERE " + ID + " = " + ID_VALUE);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Get the result set of a given row.
     * @param NAME Name of the SQL Table.
     * @param ID The ID Column, or first Column of the table.
     * @param ID_VALUE The value of the ID Column that we are searching for.
     * @return ResultSet of the queried row
     */
    public static ResultSet getRs(@Nonnull final String NAME, @Nonnull final String ID, @Nonnull final String ID_VALUE) {
        try {
            return STMT.executeQuery("SELECT * FROM " + NAME + " WHERE " + ID + " = " + ID_VALUE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the Value of a certain column.
     * @param NAME Name of the SQL Table.
     * @param ID The ID Column, or first Column of the table.
     * @param ID_VALUE The value of the ID Column that we are searching for.
     * @param INDEX Index of the column.
     * @return Value of the passed parameters
     */
    public static Object getObject(@Nonnull final String NAME, @Nonnull final String ID, @Nonnull final String ID_VALUE, @Nonnull final int INDEX) {
        ResultSet rs = getRs(NAME, ID, ID_VALUE);
        try {
            return rs.getObject(INDEX);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}