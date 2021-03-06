/*
 * MIT License
 *
 * Copyright (c) 2020 Ursin Filli
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package dev.ursinn.java.databaselib.sql;

import dev.ursinn.java.databaselib.Database;

import java.sql.*;

/**
 * SQL - SQL Class
 *
 * @author Ursin Filli
 * @version 1.0
 * @since 1.0
 */
public class SQL implements Database {

    protected static Connection connection;

    /**
     * Connect to Database
     *
     * @throws SQLException In Case of Error
     * @since 1.0
     */
    public void connect() throws SQLException {
        if (isConnected())
            close();
        connection = DriverManager.getConnection("");
    }

    /**
     * Close Database Connection
     *
     * @throws SQLException In Case of Error
     * @since 1.0
     */
    public final void close() throws SQLException {
        if (isConnected())
            connection.close();
    }

    /**
     * Check if a Connection exists to Database
     *
     * @return status
     * @throws SQLException In Case of Error
     * @since 1.0
     */
    public final boolean isConnected() throws SQLException {
        if (connection != null)
            return !connection.isClosed();
        return false;
    }

    /**
     * Update Data
     *
     * @param sql Sql String
     * @return Amount of Updated Data
     * @throws SQLException In Case of Error
     * @since 1.0
     */
    public final int update(String sql) throws SQLException {
        if (!isConnected())
            connect();
        return connection.prepareStatement(sql).executeUpdate();
    }

    /**
     * Update Data
     * And Give Back ID (Inserts)
     *
     * @param sql Sql String
     * @return Id of Updated Data
     * @throws SQLException In Case of Error
     * @since 1.0
     */
    public final long updateId(String sql) throws SQLException {
        if (!isConnected())
            connect();
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next())
            return rs.getLong(1);
        return 0;
    }

    /**
     * Get Data
     *
     * @param sql Sql String
     * @return Data
     * @throws SQLException In Case of Error
     * @since 1.0
     */
    public final ResultSet getResult(String sql) throws SQLException {
        if (!isConnected())
            connect();
        return connection.prepareStatement(sql).executeQuery();
    }
}
